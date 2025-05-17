package com.NBP.NBP.controllers;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.services.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customUsers")
public class CustomUserController {

    private final CustomUserService customUserService;

    @Autowired
    public CustomUserController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping
    public List<CustomUser> getAllUsers() {
        return customUserService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomUser> getById(@PathVariable int id) {
        Optional<CustomUser> user = customUserService.getById(id);
        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CustomUser user) {
        try {
            customUserService.saveUser(user);
            return ResponseEntity.ok("User created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody CustomUser user) {
        try {
            user.setId(id);  // Ensure the ID in the path matches the ID in the body
            customUserService.updateUser(user);
            return ResponseEntity.ok("User updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        customUserService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
