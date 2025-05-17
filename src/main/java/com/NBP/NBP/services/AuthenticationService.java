package com.NBP.NBP.services;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.LoginResponse;
import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.LoginUserDto;
import com.NBP.NBP.models.dtos.UserRegistrationDTO;
import com.NBP.NBP.repositories.CustomUserRepository;
import com.NBP.NBP.repositories.DepartmentRepository;
import com.NBP.NBP.repositories.UserRepository;

import java.time.Year;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;

    public AuthenticationService(UserRepository userRepository,
            CustomUserRepository customUserRepository,
            DepartmentRepository departmentRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.customUserRepository = customUserRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mailSender = mailSender;
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

    @Transactional
    public void registerUserAndSendCredentials(UserRegistrationDTO dto) throws MessagingException {
        User user = dto.getUser();
        Integer departmentId = dto.getDepartmentId();

        if (departmentId == null || departmentId == 0) {
        throw new IllegalArgumentException("Department ID must be specified");
        }

        if (!departmentRepository.existsById(departmentId)) {
        throw new IllegalArgumentException("Department with ID " + departmentId + " does not exist");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        String randomPassword = generateRandomPassword();

        try {
            user.setPassword(passwordEncoder.encode(randomPassword));
            user = userRepository.save(user);

            CustomUser customUser = new CustomUser();
            customUser.setUserId(user.getId());
            customUser.setDepartmentId(departmentId);
            customUser.setYear(Year.now().getValue());

            System.out.println(user.getId());
            System.out.println(customUser.getId());

            System.out.println(customUser.getUserId());
            System.out.println(customUser.getYear());
            System.out.println(customUser.getDepartmentId());
            customUserRepository.save(customUser);

            sendRegistrationEmail(user, randomPassword);
        } catch (Exception e) {
            if ((user.getId() != null)) {
                userRepository.delete(user.getId());
            }
            throw new RuntimeException("Failed to register user and send credentials: " + e.getMessage(), e);
        }
    }

    private void sendRegistrationEmail(User user, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(user.getEmail());
        helper.setSubject("👤 Welcome to NBP App - Your Login Info");
        helper.setFrom("your-email@gmail.com");

        String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                  <div style="max-width: 600px; margin: auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
                    <h2 style="color: #2c3e50;">Hello %s,</h2>
                    <p style="font-size: 16px; color: #555;">Your account for the NBP app has been created successfully.</p>
                    <p style="font-size: 18px; font-weight: bold; color: #2c3e50;">Your login credentials are:</p>
                    <p><strong>Username:</strong> %s</p>
                    <div style="padding: 10px 15px; background-color: #ecf0f1; border-left: 5px solid #3498db; margin: 10px 0; font-size: 20px; font-weight: bold; color: #2980b9;">
                      %s
                    </div>
                    <p style="font-size: 14px; color: #777;">Please log in and change this password as soon as possible.</p>
                    <hr style="margin-top: 30px;">
                    <p style="font-size: 12px; color: #bbb;">ETF Inventory Team</p>
                  </div>
                </body>
                </html>
                """
                .formatted(user.getFirstName(), user.getUsername(), password);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    private String generateRandomPassword() {
        int length = 10;
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(charSet.charAt(random.nextInt(charSet.length())));
        }
        return sb.toString();
    }

}
