package com.NBP.NBP.models;

import java.util.ArrayList;
import java.util.List;

public class Faculty {

    private int id;
    private String name;
    private List<Department> departments;

    public Faculty() {
        this.departments = new ArrayList<>();
    }

    public Faculty(String name) {
        this.name = name;
        this.departments = new ArrayList<>();
    }

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
        this.departments = new ArrayList<>();
    }

    public Faculty(int id, String name, List<Department> departments) {
        this.id = id;
        this.name = name;
        this.departments = departments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
