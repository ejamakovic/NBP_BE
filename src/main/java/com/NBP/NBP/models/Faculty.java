package com.NBP.NBP.models;

public class Faculty {

    private int id;
    private String abbreviation;
    private String name;

    public Faculty() {
    }

    public Faculty(String abbreviation, String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public Faculty(int id, String abbreviation, String name) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
