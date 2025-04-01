package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Faculty;
import com.NBP.NBP.services.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public List<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable int id) {
        return facultyService.getFacultyById(id);
    }

    @PostMapping
    public void createFaculty(@RequestBody Faculty faculty) {
        facultyService.createFaculty(faculty);
    }

    @PutMapping("/{id}")
    public void updateFaculty(@PathVariable int id, @RequestBody Faculty faculty) {
        faculty.setId(id);
        facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable int id) {
        facultyService.deleteFaculty(id);
    }
}
