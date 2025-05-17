package com.NBP.NBP.models;

import com.NBP.NBP.models.enums.UserType;

public class CustomUser {

    private int id;
    private int userId;
    private int year;
    private int departmentId;

    public CustomUser() {
    }

    public CustomUser(int userId, UserType userType, int year, int departmentId) {
        this.userId = userId;
        this.year = year;
        this.departmentId = departmentId;
    }

    public CustomUser(int id, int userId, UserType userType, int year, int departmentId) {
        this.id = id;
        this.userId = userId;
        this.year = year;
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
