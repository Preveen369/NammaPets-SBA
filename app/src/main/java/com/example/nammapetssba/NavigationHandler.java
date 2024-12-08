package com.example.nammapetssba;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NavigationHandler {

    private final Activity activity;

    // Initialize Firebase Authentication and Database
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public NavigationHandler(Activity activity) {
        this.activity = activity;
    }

    // Overloaded methods for navigation listeners
    public void setNavigationListeners(View viewAll, View profile, View home, View uploads, View cart, View favourites) {
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToCategory();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToProfile();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToHome();
            }
        });
        uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToUploads();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToCart();
            }
        });
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToFavourites();
            }
        });
    }

    public void setNavigationListeners(View profile, View home, View uploads, View cart, View favourites) {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToProfile();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToHome();
            }
        });
        uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToUploads();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToCart();
            }
        });
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHandler.this.navigateToFavourites();
            }
        });
    }

    private void navigateToCategory() {
        Intent intent = new Intent(activity, CategoryActivity.class);
        activity.startActivity(intent);
    }

    private void navigateToProfile() {
        // Check if the user is authenticated
        if (mAuth.getCurrentUser() == null) {
            // User is not logged in, redirect to LoginActivity
            Toast.makeText(activity, "You need to log in first", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack
            activity.startActivity(intent);
            activity.finish(); // Finish the current activity
        } else {
            // User is logged in, navigate to ProfileActivity
            Intent intent = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intent);
        }
    }

    private void navigateToHome() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    private void navigateToCart() {
        Intent intent = new Intent(activity, ShoppingCart.class);
        activity.startActivity(intent);
        Toast.makeText(activity, "Navigating to Cart", Toast.LENGTH_SHORT).show();
    }

    private void navigateToFavourites() {
        Intent intent = new Intent(activity, Favourites.class);
        activity.startActivity(intent);
        Toast.makeText(activity, "Navigating to Favourites", Toast.LENGTH_SHORT).show();
    }

    private void navigateToUploads() {
        Intent intent = new Intent(activity, UploadActivity.class);
        activity.startActivity(intent);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public boolean handleMenuSelection(int itemId) {
        if (itemId == R.id.menu_category) {
            navigateToCategory(); // Navigate to CategoryActivity
            return true;
        } else if (itemId == R.id.menu_profile) {
            navigateToProfile();
            return true;
        } else if (itemId == R.id.menu_home) {
            navigateToHome();
            return true;
        } else if (itemId == R.id.menu_cart) {
            navigateToCart();
            return true;
        } else if (itemId == R.id.menu_uploads) {
            navigateToUploads();
            return true;
        } else if (itemId == R.id.menu_login) {
            navigateToLogin();
            return true;
        } else if (itemId == R.id.menu_favourites) {
            navigateToFavourites();
            return true;
        } else {
            return false;
        }

    }
}
