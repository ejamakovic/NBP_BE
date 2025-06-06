package com.NBP.NBP.models;

import com.NBP.NBP.models.enums.UserType;

public class CustomUser {

    private Integer id;
    private Integer userId;
    private Integer year;

    public CustomUser() {
    }

    public CustomUser(Integer userId, UserType userType, Integer year, Integer departmentId) {
        this.userId = userId;
        this.year = year;
    }

    public CustomUser(Integer id, Integer userId, UserType userType, Integer year, Integer departmentId) {
        this.id = id;
        this.userId = userId;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
