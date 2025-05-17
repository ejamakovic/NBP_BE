package com.NBP.NBP.controllers;

import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.UserRegistrationDTO;
import com.NBP.NBP.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN') or #id == authentication.principal.id")
    public User getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationDTO userDTO) {
        try {
            userService.saveUserAndCustomUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN') or #id == authentication.principal.id")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
