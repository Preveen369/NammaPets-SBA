package com.example.nammapetssba;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoryActivity extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private FirebaseAuth mAuth; // Firebase Authentication instance
    private ImageView profileIcon; // Reference to the profile icon
    private FirebaseDatabase mDatabase;
    private DatabaseReference mCategoriesRef;
    private String categoryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category); // Link the XML layout

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance();
        mCategoriesRef = mDatabase.getReference("categories");

        // Set up click listener for the cow icon
        ImageView cowIcon = findViewById(R.id.cat1);
        cowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetListingsActivity("Cows"); // Pass "Cows" as the category
            }
        });

        // Similarly, set up listeners for other icons (e.g., dog icon)
        ImageView dogIcon = findViewById(R.id.cat2);
        dogIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetListingsActivity("Dogs"); // Pass "Dogs" as the category
            }
        });

        // Similarly, set up listeners for other icons (e.g., dog icon)
        ImageView catIcon = findViewById(R.id.cat3);
        catIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetListingsActivity("Cats"); // Pass "Dogs" as the category
            }
        });

        // Similarly, set up listeners for other icons (e.g., dog icon)
        ImageView rabbitIcon = findViewById(R.id.cat4);
        rabbitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetListingsActivity("Rabbits"); // Pass "Dogs" as the category
            }
        });

        // Similarly, set up listeners for other icons (e.g., dog icon)
        ImageView goatIcon = findViewById(R.id.cat7);
        goatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetListingsActivity("Goats"); // Pass "Dogs" as the category
            }
        });

        // Similarly, set up listeners for other icons (e.g., dog icon)
        ImageView pugIcon = findViewById(R.id.cat8);
        pugIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetListingsActivity("Pugs"); // Pass "Dogs" as the category
            }
        });


        // Similarly, set up listeners for other icons (e.g., dog icon)
        ImageView birdIcon = findViewById(R.id.cat11);
        birdIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetListingsActivity("Birds"); // Pass "Dogs" as the category
            }
        });

        // Similarly, set up listeners for other icons (e.g., dog icon)
        ImageView fishIcon = findViewById(R.id.cat12);
        fishIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPetListingsActivity("Fishes"); // Pass "Dogs" as the category
            }
        });

        // Category Names:
        TextView[] categoryNames = {findViewById(R.id.cow), findViewById(R.id.dog), findViewById(R.id.goat), findViewById(R.id.pug),
                findViewById(R.id.bird), findViewById(R.id.fish), findViewById(R.id.cat), findViewById(R.id.rabbit)};

        // Category Icon Names:
        ImageView[] categoryIcons = {cowIcon, dogIcon, goatIcon, pugIcon, birdIcon, fishIcon, catIcon, rabbitIcon};

        // Add categories details to Firebase Database
        for (int i = 0; i < categoryNames.length; i++) {
            addCategory(categoryNames[i].getText().toString(), getResources().getResourceEntryName(categoryIcons[i].getId()));
        }

        // Initialize UI and Navigation handlers
        CategoryUIManager uiManager = new CategoryUIManager(this);
        navigationHandler = new NavigationHandler(this);

        // Set up click listeners for navigation
        navigationHandler.setNavigationListeners(
                uiManager.getProfile(),
                uiManager.getHome(),
                uiManager.getUploads(),
                uiManager.getCart(),
                uiManager.getFavourites()
        );

        // Set up click listener for profile icon
        profileIcon = uiManager.getProfile();
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserAuthentication(); // Check if the user is authenticated
            }
        });

        // Set up click listener for profile icon
        profileIcon = uiManager.getProfile();
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserAuthentication(); // Check if the user is authenticated
            }
        });
    }

    private void addCategory(String categoryName, String categoryIcon) {
        categoryId = mCategoriesRef.push().getKey(); // Automatically generates unique ID
        if (categoryId != null) {
            Category category = new Category(categoryName, categoryIcon);
            // Store the category with the unique ID
            mCategoriesRef.child(categoryId).setValue(category);
        }
    }

    // Method to start the PetListingsActivity
    private void openPetListingsActivity(String category) {
        Intent intent = new Intent(CategoryActivity.this, PetListings.class);
        intent.putExtra("CATEGORY", category); // Pass the selected category
        intent.putExtra("CATEGORY_ID", categoryId); // Pass the selected category
        startActivity(intent);
    }

    private void checkUserAuthentication() {
        if (mAuth.getCurrentUser() == null) {
            // User is not logged in, redirect to LoginActivity
            Toast.makeText(CategoryActivity.this, "You need to log in first", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CategoryActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack
            startActivity(intent);
            finish(); // Finish CategoryActivity
        } else {
            // User is logged in, navigate to ProfileActivity
            Intent intent = new Intent(CategoryActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Delegate to the NavigationHandler
        return navigationHandler.handleMenuSelection(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    // Category class to represent the category data
    public static class Category {
        public String categoryName;
        public String categoryIcon;

        public Category() {
            // Default constructor required for Firebase
        }

        public Category(String categoryName, String categoryIcon) {
            this.categoryName = categoryName;
            this.categoryIcon = categoryIcon;
        }
    }
}

