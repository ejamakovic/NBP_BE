package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.services.LaboratoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratories")
public class LaboratoryController {
    private final LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @GetMapping
    public List<Laboratory> getAllLaboratories() {
        return laboratoryService.getAllLaboratories();
    }

    @GetMapping("/{id}")
    public Laboratory getLaboratoryById(@PathVariable int id) {
        return laboratoryService.getLaboratoryById(id);
    }

    @PostMapping
    public void createLaboratory(@RequestBody Laboratory laboratory) {
        laboratoryService.createLaboratory(laboratory);
    }

    @PutMapping("/{id}")
    public void updateLaboratory(@PathVariable int id, @RequestBody Laboratory laboratory) {
        laboratory.setId(id);
        laboratoryService.updateLaboratory(laboratory);
    }

    @DeleteMapping("/{id}")
    public void deleteLaboratory(@PathVariable int id) {
        laboratoryService.deleteLaboratory(id);
    }
}
