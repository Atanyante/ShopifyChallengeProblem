package com.example.springboot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Items {
    private int id;
    private String name;
    private String itemtype;
    private int quantity;
    private String description;
    private String location = "";

    public Items(int id, String name, String itemtype, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.itemtype = itemtype;
        this.quantity = quantity;
        this.description = description;
    }

    public void editItem(String name, String itemType, int quantity, String desc, String locationName) {
        this.name = name;
        this.itemtype = itemType;
        this.quantity = quantity;
        this.description = desc;
        this.location = locationName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItemType() {
        return itemtype;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDesc() {
        return description;
    }

    public String getLocation() {
        return location;
    }
}
