package com.NBP.NBP.services;

import com.NBP.NBP.models.LoginResponse;
import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.LoginUserDto;
import com.NBP.NBP.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse authenticate(LoginUserDto loginDto) {
        logger.info("Starting authentication for email: {}", loginDto.getEmail());

        // Retrieve user and verify password (as you already did)
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user == null) {
            logger.warn("No user found for email: {}", loginDto.getEmail());
            throw new RuntimeException("Invalid credentials");
        }
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            logger.info("Password verification succeeded for email: {}", loginDto.getEmail());

            // Generate token
            String token = jwtService.generateToken((UserDetails) user);
            logger.debug("Generated token: {}", token); // Avoid logging token in production

            // Ensure response is correct
            LoginResponse response = new LoginResponse(token, jwtService.getExpirationTime(), user.getEmail());
            logger.debug("Login response: {}", response);

            // Return the response
            return response;
        } else {
            logger.warn("Authentication failed for email: {}", loginDto.getEmail());
            throw new RuntimeException("Invalid credentials");
        }
    }

}
