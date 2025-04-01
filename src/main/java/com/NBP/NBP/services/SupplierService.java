package com.NBP.NBP.services;

import com.NBP.NBP.models.Supplier;
import com.NBP.NBP.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(int id) {
        return supplierRepository.findById(id);
    }

    public int saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public int updateSupplier(Supplier supplier) {
        return supplierRepository.update(supplier);
    }

    public int deleteSupplier(int id) {
        return supplierRepository.delete(id);
    }
}
