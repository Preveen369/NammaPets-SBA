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
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Favourites extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private LinearLayout favouritesContainer;
    private List<FavouriteItem> favouriteItems;
    private TextView emptyFavouritesMessage;

    private DatabaseReference databaseReference; // Firebase reference
    private SharedPreferences sharedPreferences; // For local persistence
    private static final String PREFS_NAME = "FavouritesPrefs";
    private static final String FAVOURITES_KEY = "FavouritesItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Initialize navigation, Firebase, SharedPreferences, and views
        navigationHandler = new NavigationHandler(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("favourites");
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        favouritesContainer = findViewById(R.id.favourites_container);
        emptyFavouritesMessage = findViewById(R.id.empty_favourites_message);
        favouriteItems = loadFavouritesFromSharedPreferences();

        // Add an item if passed from the previous activity
        Intent intent = getIntent();
        String petName = intent.getStringExtra("pet_name");
        int petImageId = intent.getIntExtra("pet_image", 0);
        String petCategory = intent.getStringExtra("pet_category");

        if (petName != null) {
            addItemToFavourites(petName, petCategory, petImageId);
        }

        // Display favourite items
        displayFavouriteItems();
    }

    private void addItemToFavourites(String petName, String petCategory, int petImageId) {
        if (!isItemAlreadyFavourite(petName)) {
            FavouriteItem item = new FavouriteItem(petName, petCategory, petImageId);
            favouriteItems.add(item);
            saveFavouritesToFirebase();
            saveFavouritesToSharedPreferences();
        } else {
            Toast.makeText(this, "Item already in favourites!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isItemAlreadyFavourite(String petName) {
        for (FavouriteItem item : favouriteItems) {
            if (item.getPetName().equals(petName)) {
                return true;
            }
        }
        return false;
    }

    private void displayFavouriteItems() {
        favouritesContainer.removeAllViews();

        if (favouriteItems.isEmpty()) {
            // Show empty favourites message
            emptyFavouritesMessage.setVisibility(View.VISIBLE);
        } else {
            // Hide empty favourites message and show items
            emptyFavouritesMessage.setVisibility(View.GONE);

            for (int i = 0; i < favouriteItems.size(); i++) {
                FavouriteItem item = favouriteItems.get(i);

                // Inflate favourite item layout
                View itemView = getLayoutInflater().inflate(R.layout.favourite_item, null);
                ImageView petImageView = itemView.findViewById(R.id.favourite_item_image);
                TextView petNameTextView = itemView.findViewById(R.id.favourite_item_name);
                TextView petCategoryTextView = itemView.findViewById(R.id.favourite_item_category);
                Button removeButton = itemView.findViewById(R.id.favourite_item_remove_button);

                petImageView.setImageResource(item.getPetImageId());
                petNameTextView.setText(item.getPetName());
                petCategoryTextView.setText(item.getPetCategory());

                // Remove button functionality
                int finalIndex = i; // For use inside lambda
                removeButton.setOnClickListener(v -> {
                    favouriteItems.remove(finalIndex);
                    saveFavouritesToFirebase();
                    saveFavouritesToSharedPreferences();
                    displayFavouriteItems();
                });

                favouritesContainer.addView(itemView);
            }
        }
    }

    private void saveFavouritesToFirebase() {
        databaseReference.setValue(favouriteItems);
    }

    private void saveFavouritesToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favouriteItems);
        editor.putString(FAVOURITES_KEY, json);
        editor.apply();
    }

    private List<FavouriteItem> loadFavouritesFromSharedPreferences() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FAVOURITES_KEY, null);
        Type type = new TypeToken<ArrayList<FavouriteItem>>() {}.getType();
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

    // Placeholder class for FavouriteItem
    private static class FavouriteItem {
        private String petName;
        private String petCategory;
        private int petImageId;

        public FavouriteItem(String petName, String petCategory, int petImageId) {
            this.petName = petName;
            this.petCategory = petCategory;
            this.petImageId = petImageId;
        }

        public String getPetName() {
            return petName;
        }

        public String getPetCategory() {
            return petCategory;
        }

        public int getPetImageId() {
            return petImageId;
        }
    }
}
