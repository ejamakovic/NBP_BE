package com.NBP.NBP.models.dtos;

import java.util.List;
import com.NBP.NBP.models.Rental;

public class PaginatedRentalResponseDTO {
    private List<Rental> content;
    private Integer totalPages;
    private Integer totalItems;
    private Integer currentPage;

    public PaginatedRentalResponseDTO(List<Rental> content, Integer totalPages, Integer totalItems,
            Integer currentPage) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
    }

    public List<Rental> getContent() {
        return content;
    }

    public void setContent(List<Rental> content) {
        this.content = content;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
