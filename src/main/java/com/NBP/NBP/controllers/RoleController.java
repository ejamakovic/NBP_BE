package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Role;
import com.NBP.NBP.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {
        Role role = roleService.getRoleById(id);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<String> createRole(@RequestBody Role role) {
        System.out.println("POST request received with body: " + role); // Dodatno logovanje
        System.out.println("Received Role: " + role.getName());  // Debug log
        int result = roleService.saveRole(role);
        return result > 0 ? ResponseEntity.ok("Role created successfully") : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<String> updateRole(@PathVariable int id, @RequestBody Role role) {
        role.setId(id);
        int result = roleService.updateRole(role);
        return result > 0 ? ResponseEntity.ok("Role updated successfully") : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        int result = roleService.deleteRole(id);
        return result > 0 ? ResponseEntity.ok("Role deleted successfully") : ResponseEntity.badRequest().build();
    }
}
