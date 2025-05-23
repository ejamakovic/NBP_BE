package com.NBP.NBP.models.dtos;

public class LaboratoryWithDepartmentDTO {
    private Integer id;
    private String name;
    private Integer departmentId;
    private String departmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer laboratoryId) {
        this.id = laboratoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String laboratoryName) {
        this.name = laboratoryName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
