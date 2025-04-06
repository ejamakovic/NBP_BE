package com.NBP.NBP.services;

import com.NBP.NBP.models.LoginResponse;
import com.NBP.NBP.models.CustomUser;
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

    private final CustomUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(CustomUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse authenticate(LoginUserDto loginDto) {
        logger.info("Attempting authentication for username: {}", loginDto.getUsername());

        // Retrieve the user from the database
        Optional<CustomUser> optionalUser = Optional.ofNullable(userRepository.findByUsername(loginDto.getUsername()));
        if (optionalUser.isPresent()) {
            CustomUser user = optionalUser.get();
            // Verify the password
            if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                logger.info("Password verification succeeded for username: {}", loginDto.getUsername());
                // Generate JWT token
                String token = jwtService.generateToken((UserDetails) user);
                // Extract user roles
                // Return LoginResponse with token and user information
                return new LoginResponse(token, jwtService.getExpirationTime(), user.getUsername());
            } else {
                logger.warn("Password verification failed for username: {}", loginDto.getUsername());
            }
        } else {
            logger.warn("User not found for username: {}", loginDto.getUsername());
        }
        // Throw exception if authentication fails
        throw new RuntimeException("Invalid credentials");
    }
}
