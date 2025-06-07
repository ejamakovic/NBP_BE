package com.NBP.NBP.models.dtos;

import java.time.LocalDateTime;

public class GeneratedReportDTO {
    private int id;
    private String reportName;
    private byte[] reportFile;
    private LocalDateTime generatedAt;

    public GeneratedReportDTO() {
    }

    public GeneratedReportDTO(int id, String reportName, byte[] reportFile, LocalDateTime generatedAt) {
        this.id = id;
        this.reportName = reportName;
        this.reportFile = reportFile;
        this.generatedAt = generatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public byte[] getReportFile() {
        return reportFile;
    }

    public void setReportFile(byte[] reportFile) {
        this.reportFile = reportFile;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}
