package com.example.vietpham.restaurant;

public class Meal {
    private String id;
    private int orderedId;
    private String name;
    private String description;
    private int quantity;
    private String category;
    private String imageUrl;
    private double price;
    private String status = "In process";

    public Meal(String id, String name, String description, String category, String imageUrl, double price) {
        this.id = id;
        this.orderedId = -1;
        this.name = name;
        this.description = description;
        this.quantity = 0;
        this.category = category;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Meal(String id, int orderedId, String name, String description, int quantity, String category, String imageUrl, double price) {
        this.id = id;
        this.orderedId = orderedId;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String toString() {
        return ("Meal id: "+id+"\nMeal name: "+name+"\nDescription: "+description+"\nPrice: " + price + "\nQuantity: " + quantity + "\n");
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void finishMeal() {status = "Completed";}

    public String getId() {
        return id;
    }

    public int getOrderedId() {return orderedId;}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {return category;}

    public String getImageUrl() {
        return imageUrl;
    }

    public double getPrice() { return price; }

    public int getQuantity() { return quantity; }

    public String getStatus() {return status;}
}