package com.trustrent.backend.controller;

import com.trustrent.backend.dto.RentalResponse;
import com.trustrent.backend.entity.Rental;
import com.trustrent.backend.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<RentalResponse>> getMyRentals(
            Authentication authentication) {

        List<RentalResponse> response =
                rentalService.getTenantRentals(authentication.getName())
                        .stream()
                        .map(RentalResponse::new)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/landlord")
    @PreAuthorize("hasRole('LANDLORD')")
    public ResponseEntity<List<RentalResponse>> getLandlordRentals(
            Authentication authentication) {

        List<RentalResponse> response =
                rentalService.getLandlordRentals(authentication.getName())
                        .stream()
                        .map(RentalResponse::new)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}