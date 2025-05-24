package com.NBP.NBP.models.dtos;

import java.time.LocalDate;
import java.util.List;

import com.NBP.NBP.models.User;

public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String username;
    private LocalDate birthDate;
    private Integer addressId;
    private Integer roleId;
    private List<Integer> departmentIds;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentId(List<Integer> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public User getUser() {
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setPhoneNumber(this.phoneNumber);
        user.setUsername(this.username);
        user.setBirthDate(this.birthDate);
        user.setAddressId(this.addressId);
        user.setRoleId(this.roleId);
        return user;
    }
}
