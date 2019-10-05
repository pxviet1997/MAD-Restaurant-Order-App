package com.example.vietpham.restaurant;

public class OrderedList {

    private String image;
    private String name;
    private int quantity;

    public OrderedList(String image, String name, int quantity) {
        this.image = image;
        this.name = name;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
