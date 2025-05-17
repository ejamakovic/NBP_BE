package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.dtos.EquipmentWithDetailsDTO;
import com.NBP.NBP.models.dtos.PaginatedEquipmentResponseDTO;
import com.NBP.NBP.security.CustomUserDetails;
import com.NBP.NBP.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<PaginatedEquipmentResponseDTO> getAllEquipment(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortKey", defaultValue = "name") String sortKey,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            Authentication authentication) {

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        PaginatedEquipmentResponseDTO response;

        if (roles.contains("NBP08_ADMIN")) {
            response = equipmentService.getPaginatedEquipment(page, size, sortKey, sortDirection);
        } else {
            Integer userId = getUserIdFromAuthentication(authentication);
            response = equipmentService.getPaginatedEquipmentForUser(userId, page, size, sortKey, sortDirection);
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable int id) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        return equipment != null ? ResponseEntity.ok(equipment) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createEquipment(@RequestBody EquipmentWithDetailsDTO equipment) {
        int result = equipmentService.saveEquipment(equipment);
        return result > 0
                ? ResponseEntity.ok(Map.of("message", "Equipment created successfully"))
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable int id, @RequestBody EquipmentWithDetailsDTO equipment) {
        equipment.setId(id);
        int result = equipmentService.updateEquipment(equipment);
        return result > 0
                ? ResponseEntity.ok(Map.of("message", "Equipment updated successfully"))
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable int id) {
        int result = equipmentService.deleteEquipment(id);
        return result > 0
                ? ResponseEntity.ok(Map.of("message", "Equipment deleted successfully"))
                : ResponseEntity.badRequest().build();
    }

    private Integer getUserIdFromAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUserId();
        }
        throw new IllegalStateException("Cannot extract user ID from Authentication principal");
    }

}
