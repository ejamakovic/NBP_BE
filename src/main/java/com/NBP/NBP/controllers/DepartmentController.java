package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Department;
import com.NBP.NBP.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        Department department = departmentService.getDepartmentById(id);
        return department != null ? ResponseEntity.ok(department) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createDepartment(@RequestBody Department department) {
        int result = departmentService.saveDepartment(department);
        return result > 0 ? ResponseEntity.ok("Department created successfully") : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        department.setId(id);
        int result = departmentService.updateDepartment(department);
        return result > 0 ? ResponseEntity.ok("Department updated successfully") : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id) {
        int result = departmentService.deleteDepartment(id);
        return result > 0 ? ResponseEntity.ok("Department deleted successfully") : ResponseEntity.badRequest().build();
    }
}
