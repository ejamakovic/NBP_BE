package com.NBP.NBP.models;

public class Service {

    private int id;
    private int equipementId;
    private String description;

    public Service() {
    }

    public Service(int id, int equipementId, String description) {
        this.id = id;
        this.equipementId = equipementId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipementId() {
        return equipementId;
    }

    public void setEquipementId(int equipementId) {
        this.equipementId = equipementId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
