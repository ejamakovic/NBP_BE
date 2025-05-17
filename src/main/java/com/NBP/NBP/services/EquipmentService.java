package com.NBP.NBP.services;

import com.NBP.NBP.models.Category;
import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.models.dtos.EquipmentWithDetailsDTO;
import com.NBP.NBP.models.dtos.PaginatedEquipmentResponseDTO;
import com.NBP.NBP.models.enums.EquipmentStatus;
import com.NBP.NBP.repositories.CategoryRepository;
import com.NBP.NBP.repositories.EquipmentRepository;
import com.NBP.NBP.repositories.LaboratoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final CategoryRepository categoryRepository;
    private final LaboratoryRepository laboratoryRepository;

    @Autowired
    public EquipmentService(
            EquipmentRepository equipmentRepository,
            CategoryRepository categoryRepository,
            LaboratoryRepository laboratoryRepository) {
        this.equipmentRepository = equipmentRepository;
        this.categoryRepository = categoryRepository;
        this.laboratoryRepository = laboratoryRepository;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public PaginatedEquipmentResponseDTO getPaginatedEquipment(int page, int size, String sortKey,
            String sortDirection) {
        int offset = (page - 1) * size;
        List<EquipmentWithDetailsDTO> equipmentList = equipmentRepository.findPaginated(offset, size, sortKey,
                sortDirection);
        int totalEquipment = equipmentRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalEquipment / size);

        return new PaginatedEquipmentResponseDTO(equipmentList, totalPages, totalEquipment, page);
    }

    public PaginatedEquipmentResponseDTO getPaginatedEquipmentForUser(Integer userId, int page, int size,
            String sortKey,
            String sortDirection) {
        int offset = (page - 1) * size;
        List<EquipmentWithDetailsDTO> equipmentList = equipmentRepository.findPaginatedForUser(userId, offset, size,
                sortKey, sortDirection);
        int totalEquipment = equipmentRepository.countAllForUser(userId);
        int totalPages = (int) Math.ceil((double) totalEquipment / size);

        return new PaginatedEquipmentResponseDTO(equipmentList, totalPages, totalEquipment, page);
    }

    public Equipment getEquipmentById(int id) {
        return equipmentRepository.findById(id);
    }

    public int saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public int saveEquipmentWithDTO(EquipmentWithDetailsDTO equipment) {
        if (equipment.getLaboratoryName() == null && EquipmentStatus.LABORATORY.equals(equipment.getStatus())) {
            throw new IllegalArgumentException("Cannot set status to LABORATORY without providing laboratoryName.");
        }

        if (equipment.getLaboratoryName() != null) {
            Optional<Laboratory> labOpt = laboratoryRepository.findByName(equipment.getLaboratoryName());

            if (labOpt.isEmpty()) {
                throw new IllegalArgumentException(
                        "Laboratory with name '" + equipment.getLaboratoryName() + "' not found.");
            }

            equipment.setLaboratoryId(labOpt.get().getId());
        }

        if (equipment.getCategoryName() != null) {
            Optional<Category> categoryOpt = categoryRepository.findByName(equipment.getCategoryName());
            if (categoryOpt.isEmpty()) {
                throw new IllegalArgumentException(
                        "Category with name '" + equipment.getCategoryName() + "' not found.");
            }
            equipment.setCategoryId(categoryOpt.get().getId());
        }

        if (equipment.getCategoryId() == null) {
            throw new IllegalArgumentException("categoryId must be set either directly or via categoryName.");
        }
        try {
            return equipmentRepository.save(equipment.toEquipment());
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException(
                    "An equipment with the name '" + equipment.getName() + "' already exists.");
        }
    }

    public int updateEquipment(EquipmentWithDetailsDTO equipment) {
        return equipmentRepository.update(equipment);
    }

    public int deleteEquipment(int id) {
        return equipmentRepository.delete(id);
    }

    public Optional<Equipment> findByName(String name) {
        return equipmentRepository.findByName(name);
    }

    public boolean isEquipmentRented(int equipmentId) {
        EquipmentStatus status = equipmentRepository.getStatusById(equipmentId);
        return status == EquipmentStatus.RENTED;
    }
}