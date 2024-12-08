package com.example.nammapetssba;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class PetListings extends AppCompatActivity {

    private NavigationHandler navigationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_listings); // Link to pet listings layout

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

        // Get the category selected from the Intent
        String category = getIntent().getStringExtra("CATEGORY");

        // Load the appropriate fragment based on the selected category
        assert category != null;
        loadFragment(category);
    }

    private void loadFragment(String category) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Determine which fragment to load based on the selected category
        switch (category) {
            case "Cows":
                transaction.replace(R.id.fragment_container, new CowsFragment());
                break;
            case "Dogs":
                transaction.replace(R.id.fragment_container, new DogsFragment());
                break;
            case "Cats":
                transaction.replace(R.id.fragment_container, new CatsFragment());
                break;
            case "Rabbits":
                transaction.replace(R.id.fragment_container, new RabbitsFragment());
                break;
            case "Goats":
                transaction.replace(R.id.fragment_container, new GoatsFragment());
                break;
            case "Pugs":
                transaction.replace(R.id.fragment_container, new PugsFragment());
                break;
            case "Birds":
                transaction.replace(R.id.fragment_container, new BirdsFragment());
                break;
            case "Fishes":
                transaction.replace(R.id.fragment_container, new FishesFragment());
                break;
        }
        transaction.commit();
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
}