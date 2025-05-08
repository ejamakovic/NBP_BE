package com.NBP.NBP.models.reports;

import java.util.Date;

public class EquipmentByLabReport {

    private int laboratoryId;
    private String laboratoryName;
    private int equipmentCount;
    private Date reportGeneratedAt;

    public int getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(int laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public int getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(int equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public Date getReportGeneratedAt() {
        return reportGeneratedAt;
    }

    public void setReportGeneratedAt(Date reportGeneratedAt) {
        this.reportGeneratedAt = reportGeneratedAt;
    }
}

