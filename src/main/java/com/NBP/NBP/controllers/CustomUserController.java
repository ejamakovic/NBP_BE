package com.NBP.NBP.controllers;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.dtos.CustomUserWithDepartments;
import com.NBP.NBP.models.dtos.PaginatedCustomUserResponseDTO;
import com.NBP.NBP.services.CustomUserService;
import com.NBP.NBP.utils.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;

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

    // @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    // @GetMapping
    // public List<CustomUserWithDepartments> getAllUsers() {
    //     return customUserService.getAllUsers();
    // }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @GetMapping
    public ResponseEntity<PaginatedCustomUserResponseDTO> getAllUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortKey", defaultValue = "id") String sortKey,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {

        PaginatedCustomUserResponseDTO response = customUserService.getAllUsersPaginated(page, size, sortKey, sortDirection);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<CustomUserWithDepartments> getByUserId(@PathVariable Integer id) {
        Integer userId = SecurityUtils.getCurrentUserId();
        boolean isAdmin = SecurityUtils.hasAuthority("NBP08_ADMIN");
        if (userId.equals(id) || isAdmin) {
            Optional<CustomUserWithDepartments> user = customUserService.getByUserIdWithDepartments(id);
            return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
        } else {
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }
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
            user.setId(id);
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
