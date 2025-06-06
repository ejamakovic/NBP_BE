package com.NBP.NBP.models;

import java.sql.Date;
import java.time.LocalDate;

import com.NBP.NBP.models.enums.RentalStatus;

public class Rental {
    private Integer id;
    private Integer equipmentId;
    private Integer customUserId;
    private Date rentDate;
    private Date returnDate;
    private RentalStatus status;

    public Rental() {
    };

    public Rental(Integer id, Integer equipmentId, Integer customUserId, Date rentDate, Date returnDate,
            RentalStatus status) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.customUserId = customUserId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Rental(Integer equipmentId, Integer customUserId, LocalDate rentDate, LocalDate returnDate, RentalStatus status) {
        this.equipmentId = equipmentId;
        this.customUserId = customUserId;
        this.rentDate = Date.valueOf(rentDate);
        this.returnDate = Date.valueOf(returnDate);
        this.status = status;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getCustomUserId() {
        return customUserId;
    }

    public void setCustomUserId(Integer customUserId) {
        this.customUserId = customUserId;
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

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }
}
