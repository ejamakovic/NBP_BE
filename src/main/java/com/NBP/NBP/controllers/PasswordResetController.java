package com.NBP.NBP.controllers;

import com.NBP.NBP.models.dtos.PasswordResetDTO;
import com.NBP.NBP.services.PasswordResetService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final JavaMailSender mailSender;

    public PasswordResetController(JavaMailSender mailSender, PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
        this.mailSender = mailSender;
    }

    @PostMapping("/reset-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDTO request) {
        boolean success = passwordResetService.resetPasswordAndSendEmail(request.getEmail());

        if (success) {
            return ResponseEntity.ok("New password has been sent to your email.");
        } else {
            return ResponseEntity.badRequest().body("Email not found or failed to send.");
        }
    }

    @PostMapping("/send")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public boolean sendTestEmail(@RequestParam String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("🔐 Password Reset - NBP App");
            helper.setFrom("your-email@gmail.com"); // should match spring.mail.username

            String htmlContent = """
            <html>
             <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
               <div style="max-width: 600px; margin: auto; background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
                \s
                 <h2 style="color: #2c3e50;">Hello User,</h2>
                 <p style="font-size: 16px; color: #555;">You requested a password reset for your NBP account.</p>
                 <p style="font-size: 18px; font-weight: bold; color: #2c3e50;">Your new password is:</p>
                 <div style="padding: 10px 15px; background-color: #ecf0f1; border-left: 5px solid #3498db; margin: 10px 0; font-size: 20px; font-weight: bold; color: #2980b9;">
                   Password2025
                 </div>
                 <p style="font-size: 14px; color: #777;">We recommend you log in and change this password as soon as possible.</p>
                 <p style="font-size: 14px; color: #aaa;">If you did not request this, please ignore this message.</p>
                 <hr style="margin-top: 30px;">
                 <p style="font-size: 12px; color: #bbb;">ETF Inventory Team</p>
               </div>
             </body>
           </html>
        """;

            helper.setText(htmlContent, true); // true = isHtml

            mailSender.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
