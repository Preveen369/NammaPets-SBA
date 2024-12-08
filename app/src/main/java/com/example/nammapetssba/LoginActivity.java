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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

public class LoginActivity extends AppCompatActivity {

    private NavigationHandler navigationHandler;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase; // Firebase Realtime Database reference
    private EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("users"); // Reference to the "users" node in the database

        // Initialize the NavigationHandler
        navigationHandler = new NavigationHandler(this);

        // UI components
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);
        emailField = findViewById(R.id.emailInput);
        passwordField = findViewById(R.id.passwordInput);

        // Set onClick listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        // Set onClick listener for signup button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Signup screen
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performLogin() {
        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();

        // Validate input fields
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Email cannot be empty");
            emailField.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Password cannot be empty");
            passwordField.requestFocus();
            return;
        }

        // Check if the email exists in the Firebase Database
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If the email exists in the database, authenticate the user with Firebase Authentication
                    signInWithFirebaseAuth(email, password);
                } else {
                    // If the email is not found in the database
                    Toast.makeText(LoginActivity.this, "Login failed: Email not registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInWithFirebaseAuth(final String email, String password) {
        // Firebase Authentication logic
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Navigate to Home screen after successful login
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // If login fails, show a message
                    Toast.makeText(LoginActivity.this, "Login failed: Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        // Navigate to Home Activity
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
