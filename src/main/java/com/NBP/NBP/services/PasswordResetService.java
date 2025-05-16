package com.NBP.NBP.services;

import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.PasswordChangeDTO;
import com.NBP.NBP.repositories.UserRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(UserRepository userRepository, JavaMailSender mailSender,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean resetPasswordAndSendEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        String newPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.update(user);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("🔐 Password Reset - NBP App");
            helper.setFrom("your-email@gmail.com"); // should match spring.mail.username

            String htmlContent = """
                        <html>
                         <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                           <div style="max-width: 600px; margin: auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
                            \s

                             <h2 style="color: #2c3e50;">Hello %s,</h2>
                             <p style="font-size: 16px; color: #555;">You requested a password reset for your NBP account.</p>
                             <p style="font-size: 18px; font-weight: bold; color: #2c3e50;">Your new password is:</p>
                             <div style="padding: 10px 15px; background-color: #ecf0f1; border-left: 5px solid #3498db; margin: 10px 0; font-size: 20px; font-weight: bold; color: #2980b9;">
                               %s
                             </div>
                             <p style="font-size: 14px; color: #777;">We recommend you log in and change this password as soon as possible.</p>
                             <p style="font-size: 14px; color: #aaa;">If you did not request this, please ignore this message.</p>
                             <hr style="margin-top: 30px;">
                             <p style="font-size: 12px; color: #bbb;">ETF Inventory Team</p>
                           </div>
                         </body>
                       </html>
                    """
                    .formatted(user.getFirstName(), newPassword);

            helper.setText(htmlContent, true); // true = isHtml

            mailSender.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(int userId, PasswordChangeDTO request) {
        User user = userRepository.findById(userId);

        if (user == null) {
            return false; // User not found
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return false; // Old password does not match
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.update(user);

        return true;
    }

    private String generateRandomPassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .build();
        return generator.generate(12);
    }
}
