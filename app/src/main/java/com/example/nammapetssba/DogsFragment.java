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

public class DogsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DogsAdapter dogsAdapter;
    private String ownerId;
    private String categoryId;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dogs, container, false);

        // Get ownerId and categoryId passed from previous activities
        Bundle bundle = getArguments();
        if (bundle != null) {
            ownerId = bundle.getString("USER_ID");
            categoryId = bundle.getString("CATEGORY_ID");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_dogs);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns

        categoryName = "Dogs";

        // Dummy data for dog list
        List<String> dogList = Arrays.asList("Boxer Variant", "German Sheperd", "Rajapalayam", "Kombai Variant", "Labrador Variant",
                "Dalmatian Variant");

        // Images for each dog breed
        List<Integer> dogImages = Arrays.asList(R.drawable.boxer_dog, R.drawable.german_shperd, R.drawable.rajapalayam_dog,
                R.drawable.kombai_dog, R.drawable.labrador_dog, R.drawable.dalmatian_dog);

        // Prices for each dog breed
        List<String> dogPrices = Arrays.asList("$3000", "$3500", "$4000", "$4500", "$5000", "$5500");

        // Descriptions for each dog breed
        List<String> dogDescriptions = Arrays.asList(
                "The Boxer is a medium-sized dog breed developed in Germany, known for its energetic and playful nature.",
                "The German Shepherd is a large-sized breed known for its intelligence, loyalty, and versatility.",
                "The Rajapalayam is an Indian breed known for its hunting and guarding abilities, often used for hunting boar.",
                "The Kombai is an Indian dog breed, also known for its guarding abilities, originally bred for hunting.",
                "The Labrador Retriever is one of the most popular breeds known for its friendly, outgoing, and high-energy personality.",
                "The Dalmatian is famous for its distinctive black or liver-colored spots and is known for its intelligence and high activity level."
        );

        // Set up adapter with descriptions
        dogsAdapter = new DogsAdapter(dogList, dogImages, dogPrices, dogDescriptions);
        recyclerView.setAdapter(dogsAdapter);

        // Set up item click listener to handle description
        dogsAdapter.setOnItemClickListener((dogName, dogImage, dogPrice, dogDescription) -> {
            // Generate deterministic petId using a hash
            String petId = generatePetId(ownerId, categoryId, dogName);

            // Check if the dog already exists in the database
            DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
            petRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    // Create and add the pet to the database if it doesn't already exist
                    Pet pet = new Pet(petId, ownerId, categoryId, dogName, categoryName, dogPrice, dogDescription);
                    petRef.setValue(pet);
                } else {
                    Toast.makeText(getContext(), "Pet already exists in the database.", Toast.LENGTH_SHORT).show();
                }
            });

            // Pass the dog details to PetDetailFragment
            Fragment petDetailFragment = PetDetailFragment.newInstance(dogName, dogImage, dogPrice, dogDescription, categoryName);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, petDetailFragment)
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });

        return view;
    }

    // Utility method to generate a deterministic petId
    private String generatePetId(String ownerId, String categoryId, String dogName) {
        try {
            String input = ownerId + categoryId + dogName;
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
