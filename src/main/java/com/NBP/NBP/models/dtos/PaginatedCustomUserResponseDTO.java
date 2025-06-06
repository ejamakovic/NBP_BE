package com.NBP.NBP.models.dtos;

import java.util.List;

public class PaginatedCustomUserResponseDTO {
    private List<CustomUserWithDepartments> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public List<CustomUserWithDepartments> getContent() {
        return content;
    }

    public void setContent(List<CustomUserWithDepartments> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
