package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Rental;
import com.NBP.NBP.models.dtos.PaginatedRentalDetailResponseDTO;
import com.NBP.NBP.models.dtos.PaginatedRentalResponseDTO;
import com.NBP.NBP.models.dtos.RentalDetailsDTO;
import com.NBP.NBP.models.enums.RentalStatus;
import com.NBP.NBP.security.CustomUserDetails;
import com.NBP.NBP.services.CustomUserService;
import com.NBP.NBP.services.RentalService;
import com.NBP.NBP.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.util.Optional;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final CustomUserService customUserService;

    public RentalController(RentalService rentalService, CustomUserService customUserService) {
        this.rentalService = rentalService;
        this.customUserService = customUserService;
    }

    @GetMapping("/pending")
    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> getAllPending(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));

        if (isAdmin) {
            return rentalService.findAllPending(page, size);
        } else {
            return rentalService.findPendingRentalDetailsByUserId(user.getUserId(), page, size);
        }
    }

    @GetMapping("/user/{customUserId}")
    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> getByUserId(
            @PathVariable Integer customUserId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));

        Optional<Integer> userId = customUserService.findUserIdByCustomUserId(customUserId);

        if (!isAdmin || userId.isEmpty() || !userId.get().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only view your own rentals.");
        }

        return rentalService.findByUserId(userId.get(), page, size);
    }

    @GetMapping("/pending/user/{customUserId}")
    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> getPendingByUserId(
            @PathVariable Integer customUserId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));

        Optional<Integer> actualUserIdOpt = customUserService.findUserIdByCustomUserId(customUserId);

        if (isAdmin || (actualUserIdOpt.isPresent() && actualUserIdOpt.get().equals(user.getUserId()))) {
            return rentalService.findPendingRentalDetailsByUserId(customUserId, page, size);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to view these rentals.");
        }
    }

    @GetMapping
    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> getAll(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));


        System.out.println(user.getUserId());
        if (isAdmin) {
            System.out.println(isAdmin);
            return rentalService.findAll(page, size);
        } else {
            return rentalService.findByUserId(user.getUserId(), page, size);
        }
    }

    @GetMapping("/{id}")
    public Rental getById(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails user) {
        Rental rental = rentalService.findById(id);

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));

        Optional<Integer> actualUserIdOpt = customUserService.findUserIdByCustomUserId(rental.getCustomUserId());

        if (!isAdmin && (!actualUserIdOpt.isPresent() || !actualUserIdOpt.get().equals(user.getUserId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to view this rental.");
        }

        return rental;
    }

    @GetMapping("/equipment/{equipmentId}")
    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> getByEquipmentId(
            @PathVariable int equipmentId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));

        PaginatedRentalDetailResponseDTO<RentalDetailsDTO> rentals = rentalService
                .findRentalDetailsByEquipmentId(equipmentId, page, size);

        if (isAdmin) {
            return rentals;
        }

        // Check if any of the rentals belongs to the logged-in user (by comparing real
        // userId via custom_user_id)
        boolean ownsRental = rentals.getContent().stream()
                .map(RentalDetailsDTO::getCustomUserId)
                .map(customUserService::findUserIdByCustomUserId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .anyMatch(id -> id.equals(user.getUserId()));

        if (!ownsRental) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to view these rentals.");
        }

        return rentals;
    }

    @PutMapping("/{rentalId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Integer rentalId,
            @RequestParam(name = "status") RentalStatus status,
            @AuthenticationPrincipal CustomUserDetails user) {

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));

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
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));

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
                .anyMatch(auth -> auth.getAuthority().equals("NBP08_ADMIN"));

        Optional<Integer> userId = customUserService.findUserIdByCustomUserId(rental.getCustomUserId());

        if (!isAdmin || userId.isEmpty() || !userId.get().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete this rental.");
        }

        int deleted = rentalService.delete(id);
        if (deleted > 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
