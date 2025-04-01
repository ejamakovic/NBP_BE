package com.NBP.NBP.services;

import com.NBP.NBP.models.Department;
import com.NBP.NBP.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(int id) {
        return departmentRepository.findById(id);
    }

    public int saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public int updateDepartment(Department department) {
        return departmentRepository.update(department);
    }

    public int deleteDepartment(int id) {
        return departmentRepository.delete(id);
    }
}