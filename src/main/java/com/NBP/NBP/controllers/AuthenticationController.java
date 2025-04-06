package com.NBP.NBP.controllers;

import com.NBP.NBP.services.AuthenticationService;
import com.NBP.NBP.models.dtos.LoginUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            String loginResponse = String.valueOf(authenticationService.authenticate(loginUserDto));
            String[] parts = loginResponse.split(",");
            String tokenPart = parts[0];
            String token = tokenPart.substring(tokenPart.indexOf("token=") + 6);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            // Handle authentication exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
