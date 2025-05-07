package com.NBP.NBP.models.dtos;

import java.util.List;

public class PaginatedEquipmentResponseDTO {
    private List<EquipmentWithDetailsDTO> content;
    private int totalPages;
    private int totalElements;
    private int currentPage;

    public PaginatedEquipmentResponseDTO(List<EquipmentWithDetailsDTO> content, int totalPages, int totalElements, int currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }

    public List<EquipmentWithDetailsDTO> getContent() {
        return content;
    }

    public void setContent(List<EquipmentWithDetailsDTO> content) {
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
