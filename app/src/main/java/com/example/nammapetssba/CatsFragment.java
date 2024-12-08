package com.example.nammapetssba;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class CatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CatsAdapter catsAdapter;
    private String ownerId;
    private String categoryId;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cats, container, false);

        // Get ownerId and categoryId passed from previous activities
        Bundle bundle = getArguments();
        if (bundle != null) {
            ownerId = bundle.getString("USER_ID");
            categoryId = bundle.getString("CATEGORY_ID");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_cats);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns

        categoryName = "Cats";

        // Dummy data for cat list
        List<String> catList = Arrays.asList("British Shorthair", "Persian Cat", "Scottish Cat", "Serbian Cat", "Siamese Cat", "Tabby Cat");
        List<Integer> catImages = Arrays.asList(
                R.drawable.british_shorthair_cat,
                R.drawable.persian_cat,
                R.drawable.scottish_cat,
                R.drawable.serbian_cat,
                R.drawable.siamese_cat,
                R.drawable.tabby_cat
        );

        List<String> catPrices = Arrays.asList("$5000", "$4500", "$4700", "$4800", "$4000", "$3900");

        List<String> catDescriptions = Arrays.asList(
                "The British Shorthair is calm and affectionate, a great family pet.",
                "Persian cats are known for their luxurious coats and gentle temperament.",
                "Scottish Fold cats have unique folded ears and are highly sociable.",
                "Serbian cats are rare and have a playful personality.",
                "Siamese cats are highly intelligent and vocal companions.",
                "Tabby cats are known for their beautiful striped patterns."
        );

        // Set up adapter with descriptions
        catsAdapter = new CatsAdapter(catList, catImages, catPrices, catDescriptions);
        recyclerView.setAdapter(catsAdapter);

        // Set item click listener to handle description and Firebase logic
        catsAdapter.setOnItemClickListener((catName, catImage, catPrice, catDescription) -> {
            // Generate deterministic petId using a hash
            String petId = generatePetId(ownerId, categoryId, catName);

            // Check if the cat already exists in the database
            DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
            petRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    // Create and add the pet to the database if it doesn't already exist
                    Pet pet = new Pet(petId, ownerId, categoryId, catName, categoryName, catPrice, catDescription);
                    petRef.setValue(pet);
                }
            });

            // Navigate to PetDetailFragment with the cat details
            Fragment petDetailFragment = PetDetailFragment.newInstance(catName, catImage, catPrice, catDescription, categoryName);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, petDetailFragment)
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });

        return view;
    }

    // Utility method to generate a deterministic petId
    private String generatePetId(String ownerId, String categoryId, String catName) {
        try {
            String input = ownerId + categoryId + catName;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Convert hash to Base64 string
            String base64Hash = Base64.encodeToString(hash, Base64.NO_WRAP);

            // Trim to a desired length and ensure it contains only alphanumeric characters
            return base64Hash.replaceAll("[^a-zA-Z0-9]", "").substring(0, 16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating petId", e);
        }
    }
}
