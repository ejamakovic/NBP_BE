package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Rental;
import com.NBP.NBP.models.enums.RentalStatus;
import com.NBP.NBP.services.RentalService;
import com.NBP.NBP.utils.SecurityUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping
    public List<Rental> getAllRentals() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("NBP08_USER"));

        if (isUser) {
            Integer userId = SecurityUtils.getCurrentUserId();
            return rentalService.getRentalsByUserId(userId);
        } else {
            return rentalService.getAllRentals();
        }
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping("/{id}")
    public Rental getRentalById(@PathVariable int id) {
        return rentalService.getRentalById(id);
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @PostMapping
    public void createRental(@RequestBody Rental rental) {
        rental.setStatus(RentalStatus.PENDING);
        rentalService.saveRental(rental);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PutMapping("/{id}")
    public void updateRental(@PathVariable int id, @RequestBody Rental rental) {
        rental.setId(id);
        rentalService.updateRental(rental);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRental(@PathVariable int id) {
        rentalService.deleteRental(id);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @GetMapping("/pending")
    public List<Rental> getAllPendingRentals() {
        return rentalService.getAllPendingRentals();
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping("/pending/user/{userId}")
    public ResponseEntity<?> getPendingRentalsByUserId(@PathVariable Integer userId) {
        Integer currentUserId = SecurityUtils.getCurrentUserId();
        boolean currentUserIsAdmin = SecurityUtils.hasAuthority("NBP08_ADMIN");

        if (!currentUserIsAdmin && !userId.equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You can only access your own pending rental requests.");
        }

        List<Rental> rentals = rentalService.getPendingRentalsByUserId(userId);
        return ResponseEntity.ok(rentals);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PatchMapping("/{id}/status")
    public void updateRentalStatus(@PathVariable Integer id, @RequestParam RentalStatus status) {
        rentalService.updateRentalStatus(id, status);
    }

}
