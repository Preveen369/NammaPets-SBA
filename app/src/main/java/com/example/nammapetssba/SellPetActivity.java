package com.example.nammapetssba;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class SellPetActivity extends AppCompatActivity {

    // Constants
    private static final int PICK_IMAGE_REQUEST = 1;

    // UI Components
    private EditText petNameEditText;
    private EditText petBreedEditText;
    private RadioGroup petCategoryGroup;
    private Button uploadImageButton;
    private EditText petDescriptionEditText;
    private Button sellPetButton;
    private ImageView petImageView;

    // Image Variables
    private Uri selectedImageUri;
    private Bitmap selectedImageBitmap;

    // Navigation Handler
    private NavigationHandler navigationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_pet);

        // Initialize UI components
        initializeUIComponents();

        // Initialize navigation handler
        navigationHandler = new NavigationHandler(this);

        // Set up upload image button click listener
        uploadImageButton.setOnClickListener(v -> openFileChooser());

        // Set up sell pet button click listener
        sellPetButton.setOnClickListener(v -> handleSellPetAction());
    }

    /**
     * Initializes the UI components by binding them to their respective views.
     */
    private void initializeUIComponents() {
        petNameEditText = findViewById(R.id.petName);
        petBreedEditText = findViewById(R.id.petBreed);
        petCategoryGroup = findViewById(R.id.petCategoryGroup);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        petDescriptionEditText = findViewById(R.id.petDescription);
        sellPetButton = findViewById(R.id.sellPetButton);
    }

    private void handleSellPetAction() {
        // Get user inputs
        String petName = petNameEditText.getText().toString().trim();
        String petBreed = petBreedEditText.getText().toString().trim();
        String petCategory = getSelectedCategory();
        String petDescription = petDescriptionEditText.getText().toString().trim();

        // Validate inputs
        if (isInputValid(petName, petBreed, petCategory, petDescription)) {
            showPetDetailsDialog(petName, petBreed, petCategory, petDescription);
        } else {
            showErrorDialog("Please fill in all fields and upload an image!");
        }
    }


    private String getSelectedCategory() {
        int selectedCategoryId = petCategoryGroup.getCheckedRadioButtonId();
        if (selectedCategoryId != -1) {
            RadioButton selectedCategoryButton = findViewById(selectedCategoryId);
            return selectedCategoryButton.getText().toString();
        }
        return "";
    }


    private boolean isInputValid(String petName, String petBreed, String petCategory, String petDescription) {
        return !petName.isEmpty() && !petBreed.isEmpty() && !petCategory.isEmpty() && !petDescription.isEmpty() && selectedImageBitmap != null;
    }

    /**
     * Shows an error dialog if input validation fails.
     * @param message Error message to display.
     */
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(SellPetActivity.this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }


    private void showPetDetailsDialog(String petName, String petBreed, String petCategory, String petDescription) {
        // Inflate dialog view
        View dialogView = LayoutInflater.from(SellPetActivity.this).inflate(R.layout.dialog_pet_details, null);
        petImageView = dialogView.findViewById(R.id.petImageView);
        petImageView.setImageBitmap(selectedImageBitmap);

        // Show dialog
        new AlertDialog.Builder(SellPetActivity.this)
                .setTitle("Pet Details Submitted")
                .setView(dialogView)
                .setMessage("Name: " + petName + "\nBreed: " + petBreed + "\nCategory: " + petCategory + "\nDescription: " + petDescription)
                .setPositiveButton("OK", (dialog, which) -> {})
                .show();
    }

    /**
     * Opens the file chooser to allow the user to select an image.
     * @noinspection deprecation
     */
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                Toast.makeText(this, "Image selected successfully!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image!", Toast.LENGTH_SHORT).show();
            }
        }
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
