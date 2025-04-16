package com.NBP.NBP.services;

import com.NBP.NBP.models.Faculty;
import com.NBP.NBP.repositories.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(int id) {
        return facultyRepository.findById(id);
    }

    public void saveFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    public void updateFaculty(Faculty faculty) {
        facultyRepository.update(faculty);
    }

    public void deleteFaculty(int id) {
        facultyRepository.delete(id);
    }

    public Optional<Faculty> findByName(String name) {
        return facultyRepository.findByName(name);
    }
}
