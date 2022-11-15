package com.oyopizmanga.landownerverification;

public class UserHelper {
    String username,location,email;

    public UserHelper(String username, String location, String email) {
        this.username = username;
        this.location = location;
        this.email = email;
    }

    public UserHelper() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
