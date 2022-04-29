package com.example.springboot;

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
