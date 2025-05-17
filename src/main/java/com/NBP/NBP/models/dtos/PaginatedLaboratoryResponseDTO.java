package com.NBP.NBP.models.dtos;

import java.util.List;

public class PaginatedLaboratoryResponseDTO {
    private List<LaboratoryWithDepartmentDTO> laboratories;
    private int totalPages;
    private int totalElements;
    private int currentPage;

    public PaginatedLaboratoryResponseDTO(List<LaboratoryWithDepartmentDTO> laboratories, int totalPages,
            int totalElements, int currentPage) {
        this.laboratories = laboratories;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }

    public List<LaboratoryWithDepartmentDTO> getLaboratories() {
        return laboratories;
    }

    public void setLaboratories(List<LaboratoryWithDepartmentDTO> laboratories) {
        this.laboratories = laboratories;
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
