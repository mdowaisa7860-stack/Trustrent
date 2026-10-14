package com.trustrent.backend.service;

import com.trustrent.backend.dto.AuthResponse;
import com.trustrent.backend.dto.LoginRequest;
import com.trustrent.backend.dto.OtpVerificationRequest;
import com.trustrent.backend.dto.RegisterRequest;
import com.trustrent.backend.dto.RegisterResponse;
import com.trustrent.backend.entity.TrustScore;
import com.trustrent.backend.entity.User;
import com.trustrent.backend.entity.VerificationToken;
import com.trustrent.backend.repository.TrustScoreRepository;
import com.trustrent.backend.repository.UserRepository;
import com.trustrent.backend.repository.VerificationTokenRepository;
import com.trustrent.backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TrustScoreRepository trustScoreRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, 
                       TrustScoreRepository trustScoreRepository,
                       VerificationTokenRepository verificationTokenRepository,
                       PasswordEncoder passwordEncoder, 
                       JwtService jwtService, 
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.trustScoreRepository = trustScoreRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(request.email))) {
            throw new RuntimeException("Email is already registered.");
        }

        User user = new User();
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPasswordHash(passwordEncoder.encode(request.password));
        user.setPhone(request.phone);
        user.setRole(request.role);

        User savedUser = userRepository.save(user);

        // Initialize default Trust Score = 500 (per PRD/Backend Schema)
        TrustScore trustScore = new TrustScore();
        trustScore.setUser(savedUser);
        trustScore.setScore(500);
        trustScore.setPaymentScore(500);
        trustScore.setRentalHistoryScore(500);
        trustScore.setReviewScore(500);
        trustScore.setCareScore(500);
        trustScore.setComplianceScore(500);
        trustScore.setComplaintScore(500);
        trustScore.setLastCalculatedAt(OffsetDateTime.now());
        trustScoreRepository.save(trustScore);

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        VerificationToken token = new VerificationToken();
        token.setEmail(savedUser.getEmail());
        token.setToken(otp);
        token.setExpiryDate(OffsetDateTime.now().plusMinutes(15));
        verificationTokenRepository.save(token);

        return new RegisterResponse("Registration successful. Please verify your email.", savedUser.getEmail(), otp);
    }

    @Transactional
    public AuthResponse verifyOtp(OtpVerificationRequest request) {
        VerificationToken token = verificationTokenRepository.findByEmailAndToken(request.email, request.otp)
                .orElseThrow(() -> new RuntimeException("Invalid or expired OTP."));

        if (token.getExpiryDate().isBefore(OffsetDateTime.now())) {
            throw new RuntimeException("OTP has expired.");
        }

        User user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setEmailVerified(true);
        userRepository.save(user);

        verificationTokenRepository.deleteByEmail(request.email);

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(user.getId(), user.getName(), user.getRole(), jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email, request.password)
        );

        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!user.isEmailVerified()) {
            throw new RuntimeException("Email is not verified. Please verify your email to log in.");
        }

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(user.getId(), user.getName(), user.getRole(), jwtToken);
    }
}
