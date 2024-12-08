package com.example.nammapetssba;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase; // Reference to the Firebase Realtime Database
    private EditText nameField, emailField, passwordField;
    private String userId ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);  // Ensure activity_signup.xml is created

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("users"); // Create a "users" node in the database

        // Initialize the NavigationHandler
        navigationHandler = new NavigationHandler(this);

        // UI components
        nameField = findViewById(R.id.nameInput);
        emailField = findViewById(R.id.emailInput);
        passwordField = findViewById(R.id.passwordInput);
        Button createAccountButton = findViewById(R.id.createAccountButton);

        // Set onClick listener for create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignup();
            }
        });
    }

    private void performSignup() {
        final String fullName = nameField.getText().toString();
        final String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        // Validate input fields
        if (TextUtils.isEmpty(fullName)) {
            nameField.setError("Full name cannot be empty");
            nameField.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            emailField.setError("Email cannot be empty");
            emailField.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordField.setError("Password cannot be empty");
            passwordField.requestFocus();
        } else {
            // Firebase Authentication logic for user registration
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Account registered successfully", Toast.LENGTH_SHORT).show();

                        // Store the user's full name and email in the Firebase Realtime Database
                        userId = mAuth.getCurrentUser().getUid(); // Get the unique user ID
                        User user = new User(fullName, email); // Create a user object
                        mDatabase.child(userId).setValue(user); // Save the user object to the "users" node

                        // Navigate to Home screen after successful signup
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        intent.putExtra("USER_ID", userId); // Pass the user ID to the next activity
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignupActivity.this, "Registration failed: Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
        // Delegate to NavigationHandler for handling menu selection
        return navigationHandler.handleMenuSelection(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    // User class to represent user data in the Firebase Realtime Database
    public static class User {
        public String fullName;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }
    }
}
