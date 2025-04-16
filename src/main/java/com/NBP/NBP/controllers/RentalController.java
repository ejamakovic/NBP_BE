package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Rental;
import com.NBP.NBP.services.RentalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public Rental getRentalById(@PathVariable int id) {
        return rentalService.getRentalById(id);
    }

    @PostMapping
    public void createRental(@RequestBody Rental rental) {
        rentalService.saveRental(rental);
    }

    @PutMapping("/{id}")
    public void updateRental(@PathVariable int id, @RequestBody Rental rental) {
        rental.setId(id);
        rentalService.updateRental(rental);
    }

    @DeleteMapping("/{id}")
    public void deleteRental(@PathVariable int id) {
        rentalService.deleteRental(id);
    }
}
