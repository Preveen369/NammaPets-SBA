package com.example.nammapetssba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // UI Components
    private TextView itemText1, itemDescription;
    private ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // Initialize Firebase Authentication and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Initialize the NavigationHandler
        navigationHandler = new NavigationHandler(this);

        // Initialize views from the layout
        itemText1 = findViewById(R.id.item_text_1);
        itemDescription = findViewById(R.id.item_description);

        TextView sellValue = findViewById(R.id.sell_value);
        TextView buyValue = findViewById(R.id.buy_value);
        ImageView logoutIcon = findViewById(R.id.logout_icon);

        CardView itemButton1Card = findViewById(R.id.item_button_1_card_parent);
        // CardView itemButton2Card = findViewById(R.id.item_button_2_card_parent);
        userImage = findViewById(R.id.user_image);

        // Set default profile image
        userImage.setImageResource(R.drawable.baseline_supervised_user_circle_24);

        // Set icon colors dynamically
        logoutIcon.setColorFilter(ContextCompat.getColor(this, R.color.black));

        // Load user profile information
        loadUserProfile();

        // Add click listener for "Edit Profile" button
        itemButton1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Edit Profile" button click
            }
        });


        // Set click listener for settings icon
        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        sellValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SellPetActivity.class);
                startActivity(intent);
            }
        });

        buyValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadUserProfile() {
        // Get the currently logged-in user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Get the user's unique ID
            String userId = currentUser.getUid();

            // Fetch user data from Firebase Realtime Database
            mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve user profile data
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String name = dataSnapshot.child("fullName").getValue(String.class);

                        // Set profile information dynamically
                        itemText1.setText(name != null ? name : "N/A");
                        itemDescription.setText("Email: " + (email != null ? email : "N/A"));
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to load profile information.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                    Toast.makeText(ProfileActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // If no user is logged in, show an error
            Toast.makeText(this, "No user is logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void logOut() {
        // Sign out the user from Firebase
        mAuth.signOut();

        // Show a toast message
        Toast.makeText(ProfileActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack
        startActivity(intent);
        finish(); // Finish the ProfileActivity
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Delegate to NavigationHandler for handling menu selection
        return navigationHandler.handleMenuSelection(item.getItemId()) || super.onOptionsItemSelected(item);
    }
}
