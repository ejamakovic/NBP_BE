package com.NBP.NBP.models;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private Long id;
    private String name;
    private Faculty faculty;
    private List<Laboratory> laboratories;

    public Department(){

    }

    public Department(Long id, String name, Faculty faculty) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.laboratories = new ArrayList<Laboratory>();
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<Laboratory> getLaboratorys() {
        return laboratories;
    }

    public void setLaboratorys(List<Laboratory> laboratories) {
        this.laboratories = laboratories;
    }

}
