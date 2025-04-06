package com.NBP.NBP.models;

import com.NBP.NBP.models.enums.UserType;

public class CustomUser {

    private int id;
    private int userId;
    private UserType userType;
    private int year;
    private int departmentId;
    private String email;
    private String username;
    private String password;

    public CustomUser() {
    }

    public CustomUser(int id, int userId, UserType userType, int year, int departmentId, String email, String username, String password) {
        this.id = id;
        this.userId = userId;
        this.userType = userType;
        this.year = year;
        this.departmentId = departmentId;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = username;
    }
}