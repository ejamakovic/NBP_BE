package com.NBP.NBP.services;

import com.NBP.NBP.models.Rental;
import com.NBP.NBP.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(int id) {
        return rentalRepository.findById(id);
    }

    public int saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public int updateRental(Rental rental) {
        return rentalRepository.update(rental);
    }

    public int deleteRental(int id) {
        return rentalRepository.delete(id);
    }
}
