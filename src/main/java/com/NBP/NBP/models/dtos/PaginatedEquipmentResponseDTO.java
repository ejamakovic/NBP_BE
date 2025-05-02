package com.NBP.NBP.models.dtos;

import java.util.List;

import com.NBP.NBP.models.Equipment;

public class PaginatedEquipmentResponseDTO {
    private List<Equipment> content;
    private int totalPages;
    private int totalElements;
    private int currentPage;

    // Constructor
    public PaginatedEquipmentResponseDTO(List<Equipment> content, int totalPages, int totalElements, int currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }

    // Getters and Setters
    public List<Equipment> getContent() {
        return content;
    }

    public void setContent(List<Equipment> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
