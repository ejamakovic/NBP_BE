package com.NBP.NBP.models.dtos;

import java.time.LocalDate;
import java.util.List;

import com.NBP.NBP.models.Department;
import com.NBP.NBP.models.enums.UserType;

public class CustomUserWithDepartments {

    private Integer id;
    private Integer userId;
    private Integer year;
    private List<Department> departments;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private LocalDate birthDate;
    private Integer roleId;
    private String roleName;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
