package com.oyopizmanga.landownerverification;

public class Lands {
    String locaton, size, email, price, url;

    public Lands(String locaton, String size, String email, String price, String url) {
        this.locaton = locaton;
        this.size = size;
        this.email = email;
        this.price = price;
        this.url = url;
    }

    public Lands() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}