package com.NBP.NBP.models;

import java.time.LocalDateTime;

import com.NBP.NBP.models.enums.UserType;

public class User {
    private int id;
    private int userId;
    private String name;
    private int departmentId;
    private int year;
    private UserType userType;

    public User() {
    }

    public User(int id, int userId, String name, int departmentId, int year, UserType userType) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.departmentId = departmentId;
        this.year = year;
        this.userType = userType;
    }

    // Getters and Setters
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
