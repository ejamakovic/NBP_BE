package com.NBP.NBP.controllers;

import com.NBP.NBP.services.AuthenticationService;
import com.NBP.NBP.models.LoginResponse;
import com.NBP.NBP.models.dtos.LoginUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
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
            LoginResponse loginResponse = authenticationService.authenticate(loginUserDto);
            String token = loginResponse.getToken();
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred during authentication");
        }
    }

}
