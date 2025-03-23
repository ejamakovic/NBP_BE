package com.NBP.NBP.models;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private int id;
    private String name;
    private int facultyId;
    private List<Laboratory> laboratories;

    public Department(int id, String name, int facultyId){

    }

    public Department(int id, String name, int facultyId, List<Laboratory> laboratories) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
        this.laboratories = new ArrayList<Laboratory>();
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

    public int getFacultyId() {
        return facultyId;
    }

    public void setFaculty(int facultyId) {
        this.facultyId = facultyId;
    }

    public List<Laboratory> getLaboratorys() {
        return laboratories;
    }

    public void setLaboratorys(List<Laboratory> laboratories) {
        this.laboratories = laboratories;
    }

}
