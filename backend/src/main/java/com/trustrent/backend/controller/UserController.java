package com.trustrent.backend.controller;

import com.trustrent.backend.dto.UserResponse;
import com.trustrent.backend.entity.User;
import com.trustrent.backend.entity.Rental;
import com.trustrent.backend.enums.RentalStatus;
import com.trustrent.backend.repository.UserRepository;
import com.trustrent.backend.repository.RentalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public UserController(UserRepository userRepository, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getRole().name()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getRole().name()));
    }

    @GetMapping("/{id}/rental-history")
    public ResponseEntity<List<Map<String, Object>>> getRentalHistory(@PathVariable UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Rental> rentals;
        if ("TENANT".equals(user.getRole().name())) {
            rentals = rentalRepository.findByTenantIdOrderByCreatedAtDesc(id);
        } else {
            rentals = rentalRepository.findByLandlordIdOrderByCreatedAtDesc(id);
        }

        List<Map<String, Object>> history = rentals.stream()
                .filter(r -> r.getStatus() == RentalStatus.COMPLETED)
                .map(r -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", r.getId());
                    map.put("propertyTitle", r.getProperty().getTitle());
                    map.put("startDate", r.getStartDate());
                    map.put("endDate", r.getEndDate());
                    map.put("status", r.getStatus());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(history);
    }
}
