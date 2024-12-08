package com.example.nammapetssba;

public class CartItem {
    private String cartId;  // Unique cart ID
    private String petName;
    private String petPrice;  // Store as string to match input, parse when needed
    private int petImageId;
    private String petCategory;
    private int quantity;

    public CartItem(String cartId, String petName, String petPrice, int petImageId, String petCategory, int quantity) {
        this.cartId = cartId;  // Initialize unique cart ID
        this.petName = petName;
        this.petPrice = petPrice;
        this.petImageId = petImageId;
        this.petCategory = petCategory;
        this.quantity = quantity > 0 ? quantity : 1; // Ensure minimum quantity is 1
    }

    public String getCartId() {
        return cartId;
    }

    public String getPetName() {
        return petName;
    }

    public String getPetPrice() {
        return petPrice;
    }

    public int getPetImageId() {
        return petImageId;
    }

    public String getPetCategory() {
        return petCategory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    public double calculateTotalPrice() {
        try {
            double price = Double.parseDouble(petPrice.replace("$", "").trim());
            return price * quantity;
        } catch (NumberFormatException e) {
            // Handle invalid price formats gracefully
            return 0.0;
        }
    }
}
