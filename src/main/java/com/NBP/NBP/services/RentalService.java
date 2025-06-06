package com.NBP.NBP.services;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.Rental;
import com.NBP.NBP.models.dtos.PaginatedRentalDetailResponseDTO;
import com.NBP.NBP.models.dtos.PaginatedRentalResponseDTO;
import com.NBP.NBP.models.dtos.RentalDetailsDTO;
import com.NBP.NBP.models.enums.RentalStatus;
import com.NBP.NBP.repositories.CustomUserRepository;
import com.NBP.NBP.repositories.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final CustomUserRepository customUserRepository;

    public RentalService(RentalRepository rentalRepository, CustomUserRepository customUserRepository) {
        this.rentalRepository = rentalRepository;
        this.customUserRepository = customUserRepository;
    }

    private Integer calculateOffset(Integer page, Integer size) {
        if (page == null || size == null || page < 0 || size <= 0)
            return 0;
        return page * size;
    }

    private Integer getLimit(Integer size) {
        return (size == null || size <= 0) ? Integer.MAX_VALUE : size;
    }

    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> findAllPending(Integer page, Integer size) {
        int limit = getLimit(size);
        int offset = calculateOffset(page, limit);
        List<RentalDetailsDTO> content = rentalRepository.findAllPendingDetails(offset, limit);
        int totalItems = rentalRepository.countAllPending();
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        int currentPage = (page == null) ? 0 : page;
        return new PaginatedRentalDetailResponseDTO<RentalDetailsDTO>(content, totalPages, totalItems, currentPage);
    }

    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> findByUserId(Integer userId, Integer page, Integer size) {
        CustomUser customUser = customUserRepository.findByUserId(userId);
        if (customUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Custom user not found for user ID: " + userId);
        }

        Integer customUserId = customUser.getId();

        int limit = getLimit(size);
        int offset = calculateOffset(page, limit);

        List<RentalDetailsDTO> content = rentalRepository.findRentalDetailsByUserId(customUserId, offset, limit);
        int totalItems = rentalRepository.countByUserId(customUserId);
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        int currentPage = (page == null) ? 0 : page;

        return new PaginatedRentalDetailResponseDTO<>(content, totalPages, totalItems, currentPage);
    }

    public PaginatedRentalResponseDTO findPendingByUserId(Integer userId, Integer page, Integer size) {
        int limit = getLimit(size);
        int offset = calculateOffset(page, limit);
        List<Rental> content = rentalRepository.findPendingByUserId(userId, offset, limit);
        int totalItems = rentalRepository.countPendingByUserId(userId);
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        int currentPage = (page == null) ? 0 : page;
        return new PaginatedRentalResponseDTO(content, totalPages, totalItems, currentPage);
    }

    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> findPendingRentalDetailsByUserId(Integer userId,
            Integer page, Integer size) {
        int limit = getLimit(size);
        int offset = calculateOffset(page, limit);
        List<RentalDetailsDTO> content = rentalRepository.findPendingRentalDetailsByUserId(userId, offset, limit);
        int totalItems = rentalRepository.countPendingByUserId(userId);
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        int currentPage = (page == null) ? 0 : page;
        return new PaginatedRentalDetailResponseDTO<RentalDetailsDTO>(content, totalPages, totalItems, currentPage);
    }

    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> findAll(Integer page, Integer size) {
        int limit = getLimit(size);
        int offset = calculateOffset(page, limit);
        List<RentalDetailsDTO> content = rentalRepository.findAllDetailed(offset, limit);
        int totalItems = rentalRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        int currentPage = (page == null) ? 0 : page;
        return new PaginatedRentalDetailResponseDTO<RentalDetailsDTO>(content, totalPages, totalItems, currentPage);
    }

    public PaginatedRentalResponseDTO findByEquipmentId(Integer equipmentId, Integer page, Integer size) {
        int limit = getLimit(size);
        int offset = calculateOffset(page, limit);
        List<Rental> content = rentalRepository.findByEquipmentId(equipmentId, offset, limit);
        int totalItems = rentalRepository.countByEquipmentId(equipmentId);
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        int currentPage = (page == null) ? 0 : page;
        return new PaginatedRentalResponseDTO(content, totalPages, totalItems, currentPage);
    }

    public PaginatedRentalDetailResponseDTO<RentalDetailsDTO> findRentalDetailsByEquipmentId(Integer equipmentId,
            Integer page, Integer size) {
        int limit = getLimit(size);
        int offset = calculateOffset(page, limit);
        List<RentalDetailsDTO> content = rentalRepository.findRentalDetailsByEquipmentId(equipmentId, offset, limit);
        int totalItems = rentalRepository.countByEquipmentId(equipmentId);
        int totalPages = (int) Math.ceil((double) totalItems / limit);
        int currentPage = (page == null) ? 0 : page;
        return new PaginatedRentalDetailResponseDTO<RentalDetailsDTO>(content, totalPages, totalItems, currentPage);
    }

    public Rental findById(Integer id) {
        return rentalRepository.findById(id);
    }

    public Integer updateStatus(Integer rentalId, RentalStatus newStatus) {
        return rentalRepository.updateStatus(rentalId, newStatus);
    }

    public Integer save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Integer update(Rental rental) {
        return rentalRepository.update(rental);
    }

    public Integer delete(Integer id) {
        return rentalRepository.delete(id);
    }
}
