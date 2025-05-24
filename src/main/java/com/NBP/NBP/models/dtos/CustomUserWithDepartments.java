package com.NBP.NBP.models.dtos;

import java.util.List;

import com.NBP.NBP.models.Department;
import com.NBP.NBP.models.enums.UserType;

public class CustomUserWithDepartments {

    private Integer id;
    private Integer userId;
    private Integer year;
    private List<Department> departments;

    public CustomUserWithDepartments() {
    }

    public CustomUserWithDepartments(Integer userId, UserType userType, Integer year, Integer departmentId) {
        this.userId = userId;
        this.year = year;
    }

    public CustomUserWithDepartments(Integer id, Integer userId, UserType userType, Integer year,
            Integer departmentId) {
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
