package com.oyopizmanga.landownerverification;

public class Db {
    String name, id, location;

    public Db(String name, String id, String location) {
        this.name = name;
        this.id = id;
        this.location = location;
    }

    public Db() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
