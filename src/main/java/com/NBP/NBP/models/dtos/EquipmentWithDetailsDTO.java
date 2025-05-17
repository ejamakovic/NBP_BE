package com.NBP.NBP.models.dtos;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.enums.EquipmentStatus;

public class EquipmentWithDetailsDTO {
    private Integer id;
    private String description;
    private String name;
    private Integer categoryId;
    private Integer laboratoryId;
    private EquipmentStatus status;
    private String categoryName;
    private String laboratoryName;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public Equipment toEquipment() {
        return new Equipment(
                this.id,
                this.description,
                this.name,
                this.categoryId,
                this.laboratoryId,
                this.status);
    }

}
