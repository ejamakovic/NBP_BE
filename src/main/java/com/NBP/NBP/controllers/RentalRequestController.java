package com.NBP.NBP.controllers;

import com.NBP.NBP.models.RentalRequest;
import com.NBP.NBP.services.RentalRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentalRequests")
public class RentalRequestController {

    private final RentalRequestService service;

    public RentalRequestController(RentalRequestService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<RentalRequest> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public RentalRequest getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody RentalRequest request) {
        service.save(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@PathVariable int id, @RequestBody RentalRequest request) {
        request.setId(id);
        service.update(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}