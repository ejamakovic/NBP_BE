package com.NBP.NBP.models;

import java.sql.Date;

public class Rental {
    private int id;
    private int equipmentId;
    private Date rentDate;
    private Date returnDate;

    public Rental(int id, int equipmentId, Date rentDate, Date returnDate) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
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

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
