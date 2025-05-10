package com.example.nammapetssba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private LinearLayout cartContainer;
    private List<CartItem> cartItems;
    private Button proceedToBuyButton;
    private TextView emptyCartMessage;
    private TextView totalPriceTextView;

    private DatabaseReference databaseReference; // Firebase reference
    private SharedPreferences sharedPreferences; // For local persistence
    private static final String PREFS_NAME = "ShoppingCartPrefs";
    private static final String CART_KEY = "CartItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        // Initialize navigation, Firebase, SharedPreferences, and views
        navigationHandler = new NavigationHandler(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("cart");
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        cartContainer = findViewById(R.id.cart_container);
        proceedToBuyButton = findViewById(R.id.proceed_to_buy_button);
        emptyCartMessage = findViewById(R.id.empty_cart_message);
        totalPriceTextView = findViewById(R.id.total_price);
        cartItems = loadCartFromSharedPreferences();

        // Add an item if passed from the previous activity
        final Intent[] intent = {getIntent()};
        String petName = intent[0].getStringExtra("pet_name");
        String petPrice = intent[0].getStringExtra("pet_price");
        int petImageId = intent[0].getIntExtra("pet_image", 0);
        String petCategory = intent[0].getStringExtra("pet_category");
        int petQuantity = intent[0].getIntExtra("pet_qty", 1);

        if (petName != null && petPrice != null) {
            addItemToCart(petName, petPrice, petImageId, petCategory, petQuantity);
        }

        // Button click to proceed
        proceedToBuyButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Proceeding to buy...", Toast.LENGTH_SHORT).show();
                Intent intents = new Intent(this, PaymentActivity.class);

                // Pass all cart items to PaymentActivity
                Gson gson = new Gson();
                String cartItemsJson = gson.toJson(cartItems);
                intents.putExtra("cart_items", cartItemsJson);

                startActivity(intents);
            }
        });

        // Display cart items
        displayCartItems();
    }

    private void addItemToCart(String petName, String petPrice, int petImageId, String petCategory, int petQuantity) {
        if (!isItemAlreadyInCart(petName)) {
            String uniqueCartId = generateUniqueCartId();
            CartItem item = new CartItem(uniqueCartId, petName, petPrice, petImageId, petCategory, petQuantity);
            cartItems.add(item);

            // Save to Firebase
            saveCartToFirebase();

            // Save locally
            saveCartToSharedPreferences();

            // Refresh UI
            displayCartItems();
        } else {
            Toast.makeText(this, "Item already in the cart!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isItemAlreadyInCart(String petName) {
        for (CartItem item : cartItems) {
            if (item.getPetName().equals(petName)) {
                return true;
            }
        }
        return false;
    }


    private String generateUniqueCartId() {
        return String.valueOf(System.currentTimeMillis());
    }

    private void displayCartItems() {
        cartContainer.removeAllViews();
        double totalPrice = 0.0;

        if (cartItems.isEmpty()) {
            // Show empty cart message
            emptyCartMessage.setVisibility(View.VISIBLE);
            proceedToBuyButton.setVisibility(View.GONE);
        } else {
            // Hide empty cart message and show items
            emptyCartMessage.setVisibility(View.GONE);
            proceedToBuyButton.setVisibility(View.VISIBLE);

            for (CartItem item : cartItems) {
                // Inflate cart item layout
                View itemView = getLayoutInflater().inflate(R.layout.cart_item, null);

                ImageView petImageView = itemView.findViewById(R.id.cart_item_image);
                TextView petNameTextView = itemView.findViewById(R.id.cart_item_name);
                TextView petPriceTextView = itemView.findViewById(R.id.cart_item_price);
                TextView petQuantityTextView = itemView.findViewById(R.id.cart_item_quantity);
                Button increaseQuantityButton = itemView.findViewById(R.id.cart_item_increase_quantity);
                Button decreaseQuantityButton = itemView.findViewById(R.id.cart_item_decrease_quantity);
                Button deleteButton = itemView.findViewById(R.id.cart_item_delete_button);

                petImageView.setImageResource(item.getPetImageId());
                petNameTextView.setText(item.getPetName());
                petQuantityTextView.setText("Quantity: " + item.getQuantity());
                petPriceTextView.setText("Price Amount: $" + item.calculateTotalPrice());

                // Increase quantity
                increaseQuantityButton.setOnClickListener(v -> {
                    item.increaseQuantity();
                    saveCartToFirebase();  // Save changes to Firebase
                    saveCartToSharedPreferences();
                    displayCartItems();
                });

                // Decrease quantity
                decreaseQuantityButton.setOnClickListener(v -> {
                    item.decreaseQuantity();
                    saveCartToFirebase();  // Save changes to Firebase
                    saveCartToSharedPreferences();
                    displayCartItems();
                });

                // Delete button functionality
                int finalIndex = cartItems.indexOf(item); // For use inside lambda
                deleteButton.setOnClickListener(v -> {
                    cartItems.remove(finalIndex);
                    saveCartToFirebase();
                    saveCartToSharedPreferences();
                    displayCartItems();
                });

                totalPrice += item.calculateTotalPrice();

                cartContainer.addView(itemView);
            }
        }

        // Display total price
        totalPriceTextView.setText(String.format("Total: $%.2f", totalPrice));
    }

    private void saveCartToFirebase() {
        databaseReference.setValue(cartItems);
    }

    private void saveCartToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    private List<CartItem> loadCartFromSharedPreferences() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CART_KEY, null);
        Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
        return json == null ? new ArrayList<>() : gson.fromJson(json, type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return navigationHandler.handleMenuSelection(item.getItemId()) || super.onOptionsItemSelected(item);
    }
}
