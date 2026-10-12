package com.trustrent.backend.service;

import com.trustrent.backend.entity.Rental;
import com.trustrent.backend.entity.User;
import com.trustrent.backend.repository.RentalRepository;
import com.trustrent.backend.repository.UserRepository;
import com.trustrent.backend.enums.RentalStatus;
import com.trustrent.backend.enums.TrustEventType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    private final TrustEngineService trustEngineService;

    public RentalService(
            RentalRepository rentalRepository,
            UserRepository userRepository,
            TrustEngineService trustEngineService) {

        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.trustEngineService = trustEngineService;
    }

    @Transactional(readOnly = true)
    public List<Rental> getTenantRentals(String email) {

        User tenant = findUserByEmail(email);

        return rentalRepository
                .findByTenantIdOrderByCreatedAtDesc(tenant.getId());
    }

    @Transactional(readOnly = true)
    public List<Rental> getLandlordRentals(String email) {

        User landlord = findUserByEmail(email);

        return rentalRepository
                .findByLandlordIdOrderByCreatedAtDesc(landlord.getId());
    }

    @Transactional
    public Rental completeRental(String email, UUID rentalId) {
        User landlord = findUserByEmail(email);

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        if (!rental.getLandlord().getId().equals(landlord.getId())) {
            throw new RuntimeException("You are not authorized to manage this rental");
        }

        if (rental.getStatus() == RentalStatus.COMPLETED) {
            throw new RuntimeException("Rental is already completed");
        }

        rental.setStatus(RentalStatus.COMPLETED);
        rental.setEndDate(LocalDate.now());

        Rental savedRental = rentalRepository.save(rental);

        // Emit trust events for rental completion for both landlord and tenant
        trustEngineService.recordEvent(
                rental.getTenant(),
                TrustEventType.RENTAL_COMPLETED,
                "RENTAL",
                rental.getId(),
                20,
                "Completed rental agreement as a tenant"
        );

        trustEngineService.recordEvent(
                rental.getLandlord(),
                TrustEventType.RENTAL_COMPLETED,
                "RENTAL",
                rental.getId(),
                20,
                "Completed rental agreement as a landlord"
        );

        return savedRental;
    }

    private User findUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}