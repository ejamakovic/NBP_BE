package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable int id) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        return equipment != null ? ResponseEntity.ok(equipment) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createEquipment(@RequestBody Equipment equipment) {
        int result = equipmentService.saveEquipment(equipment);
        return result > 0 ? ResponseEntity.ok("Equipment created successfully") : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEquipment(@PathVariable int id, @RequestBody Equipment equipment) {
        equipment.setId(id);
        int result = equipmentService.updateEquipment(equipment);
        return result > 0 ? ResponseEntity.ok("Equipment updated successfully") : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipment(@PathVariable int id) {
        int result = equipmentService.deleteEquipment(id);
        return result > 0 ? ResponseEntity.ok("Equipment deleted successfully") : ResponseEntity.badRequest().build();
    }
}