package com.NBP.NBP.models;

public class Laboratory {
    private int id;
    private String name;
    private int departmentId;

    public Laboratory() {}

    public Laboratory(int id, String name, int departmentId) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
    }

    public Laboratory(String name, int departmentId) {
        this.departmentId = departmentId;
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
}
