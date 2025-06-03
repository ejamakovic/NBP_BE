package com.NBP.NBP.models;

import com.NBP.NBP.models.enums.EquipmentStatus;

public class Equipment {
    private Integer id;
    private String description;
    private String name;
    private Integer categoryId;
    private Integer laboratoryId;
    private EquipmentStatus status;

    public Equipment() {
    };

    public Equipment(Integer id, String description, String name, Integer categoryId, Integer laboratoryId,
            EquipmentStatus status) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.categoryId = categoryId;
        this.laboratoryId = laboratoryId;
        this.status = status;
    }

    public Equipment(String name, Integer categoryId, Integer laboratoryId, EquipmentStatus status) {
        this.name = name;
        this.categoryId = categoryId;
        this.laboratoryId = laboratoryId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(Integer laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }
}
