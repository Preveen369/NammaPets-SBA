package com.example.nammapetssba;

// Pet class for Firebase structure
public class Pet {
    public String petId;
    public String ownerId;
    public String categoryId;
    public String name;
    public String categoryName;
    public String price;
    public String description;

    public Pet(String petId, String ownerId, String categoryId, String name, String categoryName, String price, String description) {
        this.petId = petId;
        this.ownerId = ownerId;
        this.categoryId = categoryId;
        this.name = name;
        this.categoryName = categoryName;
        this.price = price;
        this.description = description;
    }
}
