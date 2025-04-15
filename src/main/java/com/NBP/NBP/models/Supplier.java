package com.NBP.NBP.models;

public class Supplier {
    private int id;
    private int equipmentId;
    private String name;

    public Supplier(int id, int equipmentId, String name) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.name = name;
    }

    public Supplier(String name, int equipmentId) {
        this.name = name;
        this.equipmentId = equipmentId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
