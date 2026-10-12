package com.trustrent.backend.backend;

import com.trustrent.backend.repository.PropertyRepository;
import com.trustrent.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TrustRentIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @BeforeEach
    public void cleanup() {
        userRepository.deleteAll(userRepository.findAll().stream().filter(u -> u.getEmail().contains("integration.test")).toList());
    }

    @Test
    public void contextLoadsAndSeedDataSucceeds() {
        // Just verifying context startup
    }
}
