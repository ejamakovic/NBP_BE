package com.NBP.NBP.models;

import com.NBP.NBP.models.enums.EquipmentStatus;

public class Equipment {
    private int id;
    private String description;
    private String name;
    private int categoryId;
    private int laboratoryId;
    private EquipmentStatus status;

    public Equipment(int id, String description, String name, int categoryId, int laboratoryId,
            EquipmentStatus status) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.categoryId = categoryId;
        this.laboratoryId = laboratoryId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(int laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }
}
