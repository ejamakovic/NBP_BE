package com.NBP.NBP.services;

import com.NBP.NBP.models.RentalRequest;
import com.NBP.NBP.repositories.RentalRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalRequestService {
    private final RentalRequestRepository repository;

    @Autowired
    public RentalRequestService(RentalRequestRepository repository) {
        this.repository = repository;
    }

    public List<RentalRequest> getAll() {
        return repository.findAll();
    }

    public RentalRequest getById(int id) {
        return repository.findById(id);
    }

    public int save(RentalRequest request) {
        return repository.save(request);
    }

    public int update(RentalRequest request) {
        return repository.update(request);
    }

    public int delete(int id) {
        return repository.delete(id);
    }
}
