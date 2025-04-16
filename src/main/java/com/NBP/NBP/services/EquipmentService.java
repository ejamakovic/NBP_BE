package com.NBP.NBP.services;

import com.NBP.NBP.models.Equipment;
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

    public Equipment getEquipmentById(int id) {
        return equipmentRepository.findById(id);
    }

    public int saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public int updateEquipment(Equipment equipment) {
        return equipmentRepository.update(equipment);
    }

    public int deleteEquipment(int id) {
        return equipmentRepository.delete(id);
    }

    public Optional<Equipment> findByName(String name) {
        return equipmentRepository.findByName(name);
    }
}