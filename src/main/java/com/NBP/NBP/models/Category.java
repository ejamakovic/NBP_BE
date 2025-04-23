package com.NBP.NBP.models;

public class Category {
    private int id;
    private String description;
    private String name;

    public Category(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public Category(int id, String name, String description) {
        this.id = id;
        this.description = description;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
