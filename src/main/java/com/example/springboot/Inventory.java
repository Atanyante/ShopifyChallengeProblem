package com.example.springboot;

import java.util.List;

public class Inventory {
    Locations location;
    List<Items> inventory;
    public Inventory(Locations location, List<Items> inventory) {
        this.location = location;
        this.inventory = inventory;
    }

    public Locations getLocation() {
        return location;
    }

    public List<Items> getInventory() {
        return inventory;
    }
}
