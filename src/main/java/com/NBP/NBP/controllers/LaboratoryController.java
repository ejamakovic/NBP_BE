package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.models.dtos.PaginatedLaboratoryResponseDTO;
import com.NBP.NBP.services.LaboratoryService;
import com.NBP.NBP.utils.SecurityUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/laboratories")
public class LaboratoryController {
    private final LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping
    public ResponseEntity<PaginatedLaboratoryResponseDTO> getAllLaboratories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortKey", defaultValue = "name") String sortKey,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            Authentication authentication) {

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        PaginatedLaboratoryResponseDTO response;

        if (roles.contains("NBP08_ADMIN")) {
            response = laboratoryService.getPaginatedLaboratories(page, size, sortKey, sortDirection);
        } else {
            Integer userId = SecurityUtils.getCurrentUserId();
            response = laboratoryService.getPaginatedLaboratoriesForUser(userId, page, size, sortKey, sortDirection);
        }
        return ResponseEntity.ok(response);
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
