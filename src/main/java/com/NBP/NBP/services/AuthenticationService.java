package com.NBP.NBP.services;

import com.NBP.NBP.models.LoginResponse;
import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.LoginUserDto;
import com.NBP.NBP.repositories.CustomUserRepository;
import com.NBP.NBP.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        logger.info("Starting authentication for username: {}", loginDto.getUsername());

        // Retrieve user and verify password (as you already did)
        User user = userRepository.findByUsername(loginDto.getUsername());
        if (user != null && loginDto.getPassword().equals(user.getPassword())) {
            logger.info("Password verification succeeded for username: {}", loginDto.getUsername());

            // Generate token
            String token = jwtService.generateToken((UserDetails) user);
            logger.debug("Generated token: {}", token); // Avoid logging token in production

            // Ensure response is correct
            LoginResponse response = new LoginResponse(token, jwtService.getExpirationTime(), user.getUsername());
            logger.debug("Login response: {}", response);

            // Return the response
            return response;
        } else {
            logger.warn("Authentication failed for username: {}", loginDto.getUsername());
            throw new RuntimeException("Invalid credentials");
        }
    }

}
