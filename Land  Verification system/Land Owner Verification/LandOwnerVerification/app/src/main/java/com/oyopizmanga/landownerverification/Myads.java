package com.oyopizmanga.landownerverification;

public class Myads {
    String locaton, size, email, price;

    public Myads() {
    }

    public Myads(String locaton, String size, String email, String price) {

        this.locaton = locaton;
        this.size = size;
        this.email = email;
        this.price = price;
    }

    public String getLocaton() {
        return locaton;
    }

    public void setLocaton(String locaton) {
        this.locaton = locaton;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
