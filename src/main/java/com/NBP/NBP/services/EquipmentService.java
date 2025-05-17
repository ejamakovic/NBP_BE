package com.NBP.NBP.services;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.dtos.EquipmentWithDetailsDTO;
import com.NBP.NBP.models.dtos.PaginatedEquipmentResponseDTO;
import com.NBP.NBP.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public PaginatedEquipmentResponseDTO getPaginatedEquipment(int page, int size, String sortKey,
            String sortDirection) {
        int offset = page * size;
        List<EquipmentWithDetailsDTO> equipmentList = equipmentRepository.findPaginated(offset, size, sortKey,
                sortDirection);
        int totalEquipment = equipmentRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalEquipment / size);

        return new PaginatedEquipmentResponseDTO(equipmentList, totalPages, totalEquipment, page);
    }

    public PaginatedEquipmentResponseDTO getPaginatedEquipmentForUser(Integer userId, int page, int size,
            String sortKey,
            String sortDirection) {
        int offset = page * size;
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

    public int updateEquipment(EquipmentWithDetailsDTO equipment) {
        return equipmentRepository.update(equipment);
    }

    public int deleteEquipment(int id) {
        return equipmentRepository.delete(id);
    }

    public Optional<Equipment> findByName(String name) {
        return equipmentRepository.findByName(name);
    }
}