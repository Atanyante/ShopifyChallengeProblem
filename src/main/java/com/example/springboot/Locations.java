package com.example.springboot;

public class Locations {
    String name;
    // Address should be in form (number street name, city/state, country
    String address;

    public Locations(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
