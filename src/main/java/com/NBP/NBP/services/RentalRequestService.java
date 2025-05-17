package com.NBP.NBP.services;

import com.NBP.NBP.models.RentalRequest;
import com.NBP.NBP.models.enums.RequestStatus;
import com.NBP.NBP.repositories.RentalRequestRepository;
import com.NBP.NBP.services.UserService;
import com.NBP.NBP.services.CustomUserService;
import com.NBP.NBP.models.User;
import com.NBP.NBP.exceptions.EquipmentAlreadyRentedException;
import com.NBP.NBP.models.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalRequestService {
    private final RentalRequestRepository repository;
    private final UserService userService;
    private final CustomUserService customUserService;
    private final EquipmentService equipmentService;

    @Autowired
    public RentalRequestService(RentalRequestRepository repository,
            UserService userService,
            CustomUserService customUserService,
            EquipmentService equipmentService) {
        this.repository = repository;
        this.userService = userService;
        this.customUserService = customUserService;
        this.equipmentService = equipmentService;
    }

    public List<RentalRequest> getAll() {
        return repository.findAll();
    }

    public RentalRequest getById(int id) {
        return repository.findById(id);
    }

    public int createRequest(RentalRequest request) {
        if (equipmentService.isEquipmentRented(request.getEquipmentId())) {
            throw new EquipmentAlreadyRentedException("Equipment is already rented");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found for username: " + username);
        }

        Optional<CustomUser> customUserOpt = customUserService.getByUserId(userOpt.get().getId());
        if (customUserOpt.isEmpty()) {
            throw new RuntimeException("CustomUser not found for user id: " + userOpt.get().getId());
        }

        request.setCustomUserId(customUserOpt.get().getId());

        request.setRequestDate(LocalDate.now());
        request.setStatus(RequestStatus.PENDING);

        return save(request);
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
