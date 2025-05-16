package com.NBP.NBP.services;

import com.NBP.NBP.models.LoginResponse;
import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.LoginUserDto;
import com.NBP.NBP.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse authenticate(LoginUserDto loginDto) {
        logger.info("Starting authentication for email: {}", loginDto.getEmail());

        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            logger.warn("Invalid credentials for email: {}", loginDto.getEmail());
            throw new BadCredentialsException("Invalid credentials");
        }

        logger.info("Password verification succeeded for email: {}", loginDto.getEmail());

        String token = jwtService.generateToken((UserDetails) user);
        logger.debug("Generated token: {}", token);

        LoginResponse response = new LoginResponse(token, jwtService.getExpirationTime(), user.getEmail());
        logger.debug("Login response: {}", response);

        return response;
    }

}
