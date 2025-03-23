package com.NBP.NBP.services;

import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.repositories.LaboratoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratoryService {
    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryService(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    public Laboratory getLaboratoryById(int id) {
        return laboratoryRepository.findById(id);
    }

    public void createLaboratory(Laboratory laboratory) {
        laboratoryRepository.save(laboratory);
    }

    public void updateLaboratory(Laboratory laboratory) {
        laboratoryRepository.update(laboratory);
    }

    public void deleteLaboratory(int id) {
        laboratoryRepository.delete(id);
    }
}
