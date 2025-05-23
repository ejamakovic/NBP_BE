package com.NBP.NBP.services;

import com.NBP.NBP.models.Laboratory;
import com.NBP.NBP.models.dtos.LaboratoryWithDepartmentDTO;
import com.NBP.NBP.models.dtos.PaginatedLaboratoryResponseDTO;
import com.NBP.NBP.repositories.LaboratoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratoryService {
    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryService(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    public PaginatedLaboratoryResponseDTO getPaginatedLaboratories(Integer page, Integer size, String sortKey,
            String sortDirection) {
        if (page == null || size == null) {
            List<LaboratoryWithDepartmentDTO> allLabs = laboratoryRepository.findPaginated(null, null, sortKey, sortDirection);
            return new PaginatedLaboratoryResponseDTO(allLabs, 1, allLabs.size(), 0);
        }
        int offset = page * size;
        List<LaboratoryWithDepartmentDTO> labs = laboratoryRepository.findPaginated(offset, size, sortKey,
                sortDirection);
        int totalLabs = laboratoryRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalLabs / size);

        return new PaginatedLaboratoryResponseDTO(labs, totalPages, totalLabs, page);
    }

    public PaginatedLaboratoryResponseDTO getPaginatedLaboratoriesForUser(Integer userId, Integer page, Integer size,
            String sortKey, String sortDirection) {
        if (page == null || size == null) {
            List<LaboratoryWithDepartmentDTO> allLabs = laboratoryRepository.findPaginatedForUser(userId, null, null, sortKey, sortDirection);
            return new PaginatedLaboratoryResponseDTO(allLabs, 1, allLabs.size(), 0);
        }
        Integer offset = page * size;
        List<LaboratoryWithDepartmentDTO> labs = laboratoryRepository.findPaginatedForUser(userId, offset, size,
                sortKey, sortDirection);
        Integer totalLabs = laboratoryRepository.countAllForUser(userId);
        Integer totalPages = (int) Math.ceil((double) totalLabs / size);

        return new PaginatedLaboratoryResponseDTO(labs, totalPages, totalLabs, page);
    }

    public Laboratory getLaboratoryById(int id) {
        return laboratoryRepository.findById(id);
    }

    public void saveLaboratory(Laboratory laboratory) {
        laboratoryRepository.save(laboratory);
    }

    public void updateLaboratory(Laboratory laboratory) {
        laboratoryRepository.update(laboratory);
    }

    public void deleteLaboratory(int id) {
        laboratoryRepository.delete(id);
    }

    public Optional<Laboratory> findByName(String name) {
        return laboratoryRepository.findByName(name);
    }
}
