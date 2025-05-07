package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.dtos.EquipmentWithDetailsDTO;
import com.NBP.NBP.models.dtos.PaginatedEquipmentResponseDTO;
import com.NBP.NBP.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PaginatedEquipmentResponseDTO> getAllEquipment(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortKey", defaultValue = "name") String sortKey,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection) {

        PaginatedEquipmentResponseDTO response = equipmentService.getPaginatedEquipment(page, size, sortKey,
                sortDirection);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable int id) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        return equipment != null ? ResponseEntity.ok(equipment) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createEquipment(@RequestBody Equipment equipment) {
        int result = equipmentService.saveEquipment(equipment);
        return result > 0
                ? ResponseEntity.ok(Map.of("message", "Equipment created successfully"))
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable int id, @RequestBody EquipmentWithDetailsDTO equipment) {
        equipment.setId(id);
        int result = equipmentService.updateEquipment(equipment);
        return result > 0
                ? ResponseEntity.ok(Map.of("message", "Equipment updated successfully"))
                : ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable int id) {
        int result = equipmentService.deleteEquipment(id);
        return result > 0
                ? ResponseEntity.ok(Map.of("message", "Equipment deleted successfully"))
                : ResponseEntity.badRequest().build();
    }
}
