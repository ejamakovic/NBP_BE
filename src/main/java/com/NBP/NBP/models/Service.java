package com.NBP.NBP.models;

import java.time.LocalDate;

public class Service {

    private int id;
    private int equipmentId;
    private String description;
    private LocalDate serviceDate;

    public Service() {
        // Default constructor
    }

    public Service(int equipmentId, String description, LocalDate serviceDate) {
        this.equipmentId = equipmentId;
        this.description = description;
        this.serviceDate = serviceDate;
    }

    public Service(int id, int equipmentId, String description, LocalDate serviceDate) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.description = description;
        this.serviceDate = serviceDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }
}
