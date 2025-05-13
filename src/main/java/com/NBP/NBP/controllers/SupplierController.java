package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Supplier;
import com.NBP.NBP.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('NBP08_ADMIN') or hasAuthority('NBP08_USER')")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN') or hasAuthority('NBP08_USER')")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable int id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return supplier != null ? ResponseEntity.ok(supplier) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<String> createSupplier(@RequestBody Supplier supplier) {
        int result = supplierService.saveSupplier(supplier);
        return result > 0 ? ResponseEntity.ok("Supplier created successfully") : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<String> updateSupplier(@PathVariable int id, @RequestBody Supplier supplier) {
        supplier.setId(id);
        int result = supplierService.updateSupplier(supplier);
        return result > 0 ? ResponseEntity.ok("Supplier updated successfully") : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public ResponseEntity<String> deleteSupplier(@PathVariable int id) {
        int result = supplierService.deleteSupplier(id);
        return result > 0 ? ResponseEntity.ok("Supplier deleted successfully") : ResponseEntity.badRequest().build();
    }
}
