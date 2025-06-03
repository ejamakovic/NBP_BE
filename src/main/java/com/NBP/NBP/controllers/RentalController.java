package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Rental;
import com.NBP.NBP.models.dtos.PaginatedRentalResponseDTO;
import com.NBP.NBP.models.enums.RentalStatus;
import com.NBP.NBP.security.CustomUserDetails;
import com.NBP.NBP.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/pending")
    public PaginatedRentalResponseDTO getAllPending(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return rentalService.findAllPending(page, size);
        } else {
            return rentalService.findPendingByUserId(user.getUserId(), page, size);
        }
    }

    @GetMapping("/user/{userId}")
    public PaginatedRentalResponseDTO getByUserId(
            @PathVariable Integer userId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !user.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only view your own rentals.");
        }

        return rentalService.findByUserId(userId, page, size);
    }

    @GetMapping("/pending/user/{userId}")
    public PaginatedRentalResponseDTO getPendingByUserId(
            @PathVariable Integer userId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin || user.getUserId().equals(userId)) {
            return rentalService.findPendingByUserId(userId, page, size);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to view these rentals.");
        }
    }

    @GetMapping
    public PaginatedRentalResponseDTO getAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return rentalService.findAll(page, size);
        } else {
            return rentalService.findByUserId(user.getUserId(), page, size);
        }
    }

    @GetMapping("/{id}")
    public Rental getById(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails user) {
        Rental rental = rentalService.findById(id);

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !rental.getUserId().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to view this rental.");
        }

        return rental;
    }

    @GetMapping("/equipment/{equipmentId}")
    public PaginatedRentalResponseDTO getByEquipmentId(
            @PathVariable int equipmentId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return rentalService.findByEquipmentId(equipmentId, page, size);
        } else {
            PaginatedRentalResponseDTO rentals = rentalService.findByEquipmentId(equipmentId, page, size);

            boolean ownsRental = rentals.getContent().stream()
                    .anyMatch(rental -> rental.getUserId().equals(user.getUserId()));

            if (!ownsRental) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to view these rentals.");
            }

            return rentals;
        }
    }

    @PutMapping("/{rentalId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Integer rentalId,
            @RequestParam(name = "status") RentalStatus status,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can update rental status.");
        }

        int updated = rentalService.updateStatus(rentalId, status);
        if (updated > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createRental(@RequestBody Rental rental) {
        rental.setStatus(RentalStatus.PENDING);
        int saved = rentalService.save(rental);
        if (saved > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRental(
            @PathVariable Integer id,
            @RequestBody Rental rental,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can update rentals.");
        }

        rental.setId(id);
        int updated = rentalService.update(rental);
        if (updated > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id,
            @AuthenticationPrincipal CustomUserDetails user) {
        Rental rental = rentalService.findById(id);
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !rental.getUserId().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete this rental.");
        }

        int deleted = rentalService.delete(id);
        if (deleted > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
