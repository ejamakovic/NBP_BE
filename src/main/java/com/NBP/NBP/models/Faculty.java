package com.NBP.NBP.models;

import java.util.ArrayList;
import java.util.List;

public class Faculty {

    private Long id;
    private String name;
    private List<Department> departments;

    public Faculty(int id, String name) {
        this.departments = new ArrayList<>();
    }

    public Faculty(Long id, String name, List<Department> departments, List<Laboratory> laboratories) {
        this.id = id;
        this.name = name;
        this.departments = departments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

}
