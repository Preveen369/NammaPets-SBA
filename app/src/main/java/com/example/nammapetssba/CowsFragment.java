package com.example.nammapetssba;

import android.content.Intent;
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

public class CowsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CowsAdapter cowsAdapter;
    private String ownerId;
    private String categoryId;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cows, container, false);

        // Get ownerId and categoryId passed from previous activities
        Bundle bundle = getArguments();
        if (bundle != null) {
            ownerId = bundle.getString("USER_ID");
            categoryId = bundle.getString("CATEGORY_ID");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_cows);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns

        categoryName = "Cows";

        // Dummy data for cow list
        List<String> cowList = Arrays.asList(
                "Jersey Variant",
                "Alambadi Variant",
                "Gir Variant",
                "Kangayam Variant",
                "Baraguru Variant",
                "Sindhi Variant"
        );

        List<Integer> cowImages = Arrays.asList(
                R.drawable.jersey,
                R.drawable.alambadi,
                R.drawable.gir,
                R.drawable.kangayam,
                R.drawable.baraguru,
                R.drawable.sindhi
        );

        List<String> cowPrices = Arrays.asList("$4000", "$4500", "$5000", "$5500", "$6000", "$6500");

        List<String> cowDescriptions = Arrays.asList(
                "Jersey Variant is known for its high milk yield.",
                "Alambadi Variant is a native breed known for its hardiness.",
                "Gir Variant originates from Gujarat and is popular for its rich milk.",
                "Kangayam Variant is a strong breed native to Tamil Nadu.",
                "Baraguru Variant is known for its strength and adaptability.",
                "Sindhi Variant is a productive breed with high resistance to heat."
        );

        // Set up adapter
        cowsAdapter = new CowsAdapter(cowList, cowImages, cowPrices, cowDescriptions);
        recyclerView.setAdapter(cowsAdapter);

        // Set item click listener
        cowsAdapter.setOnItemClickListener((cowName, cowImage, cowPrice, cowDescription) -> {
            // Generate deterministic petId using a hash
            String petId = generatePetId(ownerId, categoryId, cowName);

            // Check if the pet already exists in the database
            DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
            petRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    // Create and add the pet to the database if it doesn't already exist
                    Pet pet = new Pet(petId, ownerId, categoryId, cowName, categoryName, cowPrice, cowDescription);
                    petRef.setValue(pet);
                } else {
                    Toast.makeText(getContext(), "Pet already exists in the database.", Toast.LENGTH_SHORT).show();
                }
            });

            // Pass data to PetDetailFragment
            Fragment petDetailFragment = PetDetailFragment.newInstance(cowName, cowImage, cowPrice, cowDescription, categoryName);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, petDetailFragment)
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });

        return view;
    }

    // Utility method to generate a deterministic petId
    private String generatePetId(String ownerId, String categoryId, String cowName) {
        try {
            String input = ownerId + categoryId + cowName;
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
