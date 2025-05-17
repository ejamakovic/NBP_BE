package com.NBP.NBP.models.dtos;

public class RentalRequestDTO {
    private int equipmentId;

    public RentalRequestDTO() {
    }

    public RentalRequestDTO(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
}
