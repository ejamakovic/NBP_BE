package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.services.LaboratoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratories")
public class LaboratoryController {
    private final LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping
    public List<Laboratory> getAllLaboratories() {
        return laboratoryService.getAllLaboratories();
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping("/{id}")
    public Laboratory getLaboratoryById(@PathVariable int id) {
        return laboratoryService.getLaboratoryById(id);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PostMapping
    public void createLaboratory(@RequestBody Laboratory laboratory) {
        laboratoryService.saveLaboratory(laboratory);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PutMapping("/{id}")
    public void updateLaboratory(@PathVariable int id, @RequestBody Laboratory laboratory) {
        laboratory.setId(id);
        laboratoryService.updateLaboratory(laboratory);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteLaboratory(@PathVariable int id) {
        laboratoryService.deleteLaboratory(id);
    }
}
