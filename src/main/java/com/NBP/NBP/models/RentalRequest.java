package com.NBP.NBP.models;

import java.time.LocalDate;

public class RentalRequest {
    private int id;
    private int equipmentId;
    private int customUserId;
    private LocalDate requestDate;
    private String status; // PENDING, APPROVED, REJECTED

    public RentalRequest(int id, int equipmentId, int customUserId, LocalDate requestDate, String status) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.customUserId = customUserId;
        this.requestDate = requestDate;
        this.status = status;
    }

    public RentalRequest(int equipmentId, int customUserId, LocalDate requestDate, String status) {
        this.equipmentId = equipmentId;
        this.customUserId = customUserId;
        this.requestDate = requestDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getCustomUserId() {
        return customUserId;
    }

    public void setCustomUserId(int customUserId) {
        this.customUserId = customUserId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
