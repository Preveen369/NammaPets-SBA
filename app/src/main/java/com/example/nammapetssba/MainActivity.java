package com.example.nammapetssba;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private FirebaseAuth mAuth; // Firebase Authentication instance
    private ImageView profileIcon; // Reference to the profile icon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Link the XML layout

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI and Navigation handlers
        MainUIManager uiManager = new MainUIManager(this);
        navigationHandler = new NavigationHandler(this);


        // Set up click listeners for navigation
        navigationHandler.setNavigationListeners(
                uiManager.getViewAll(),
                uiManager.getProfile(),
                uiManager.getHome(),
                uiManager.getCart(),
                uiManager.getFavourites()
        );

        // Set up click listener for profile icon
        profileIcon = uiManager.getProfile();
        profileIcon.setOnClickListener(v -> {
            checkUserAuthentication(); // Check if the user is authenticated
        });

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

    }

    private void checkUserAuthentication() {
        if (mAuth.getCurrentUser() == null) {
            // User is not logged in, redirect to LoginActivity
            Toast.makeText(MainActivity.this, "You need to log in first", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack
            startActivity(intent);
            finish(); // Finish MainActivity
        } else {
            // User is logged in, navigate to ProfileActivity
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    // Method to start the PetListings Activity
    private void openPetListingsActivity(String category) {
        Intent intent = new Intent(MainActivity.this, PetListings.class);
        intent.putExtra("CATEGORY", category); // Pass the selected category
        startActivity(intent);
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

    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        // Exit the app instead of going back to the launcher
        super.onBackPressed();
        finishAffinity(); // Close all activities and exit the app
    }
}
