package com.NBP.NBP.models;

public class Department {

    private int id;
    private String name;
    private int facultyId;

    public Department() {
    }

    public Department(String name, int facultyId) {
        this.name = name;
        this.facultyId = facultyId;
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(int id, String name, int facultyId) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
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
}