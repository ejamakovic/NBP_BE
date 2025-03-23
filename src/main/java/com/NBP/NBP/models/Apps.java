package com.NBP.NBP.models;

import java.sql.Date;

public class Apps {
    private int id;
    private String appId;
    private Integer managerId;
    private Date expiryDate;

    public Apps(int id, String appId, Integer managerId, Date expiryDate) {
        this.id = id;
        this.appId = appId;
        this.managerId = managerId;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
