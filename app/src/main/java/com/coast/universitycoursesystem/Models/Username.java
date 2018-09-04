package com.coast.universitycoursesystem.Models;

public class Username {
    String name;
    String id;

    public Username() {
    }

    public Username(String name,String id) {
        this.name = name;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
