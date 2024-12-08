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

public class GoatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private GoatsAdapter goatsAdapter;
    private String ownerId;
    private String categoryId;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goats, container, false);

        // Get ownerId and categoryId passed from previous activities
        Bundle bundle = getArguments();
        if (bundle != null) {
            ownerId = bundle.getString("USER_ID");
            categoryId = bundle.getString("CATEGORY_ID");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_goats);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns

        categoryName = "Goats";

        // Dummy data for goat list
        List<String> goatList = Arrays.asList("Mehsana Goat", "Marwari Goat", "Surti Goat", "Saneen Goat", "Beetal Goat",
                "Combo feeder");

        List<Integer> goatImages = Arrays.asList(R.drawable.mehsana, R.drawable.marwari_goat, R.drawable.surti_goat,
                R.drawable.saneen_goat, R.drawable.beetal_goat, R.drawable.combination_feeder);

        List<String> goatPrices = Arrays.asList("$1500", "$1800", "$2000", "$2200", "$2500", "$3000");

        List<String> goatDescriptions = Arrays.asList(
                "Mehsana goats are well-known for their milk production and high-quality dairy products.",
                "Marwari goats are known for their excellent adaptability to arid and semi-arid climates.",
                "Surti goats are recognized for their high milk yield and ability to thrive in dry conditions.",
                "Saneen goats are large-sized, milk-producing goats originating from Switzerland.",
                "Beetal goats are known for their adaptability and are excellent for both milk and meat production.",
                "Combo feeder goats are bred for combined meat and milk production, offering versatility in farming."
        );

        // Set up adapter with descriptions
        goatsAdapter = new GoatsAdapter(goatList, goatImages, goatPrices, goatDescriptions);
        recyclerView.setAdapter(goatsAdapter);

        // Set up item click listener to handle description
        goatsAdapter.setOnItemClickListener((goatName, goatImage, goatPrice, goatDescription) -> {
            // Generate deterministic petId using a hash
            String petId = generatePetId(ownerId, categoryId, goatName);

            // Check if the goat already exists in the database
            DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
            petRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    // Create and add the pet to the database if it doesn't already exist
                    Pet pet = new Pet(petId, ownerId, categoryId, goatName, categoryName, goatPrice, goatDescription);
                    petRef.setValue(pet);
                }
            });

            // Pass the goat details to PetDetailFragment
            Fragment petDetailFragment = PetDetailFragment.newInstance(goatName, goatImage, goatPrice, goatDescription, categoryName);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, petDetailFragment)
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });

        return view;
    }

    // Utility method to generate a deterministic petId
    private String generatePetId(String ownerId, String categoryId, String goatName) {
        try {
            String input = ownerId + categoryId + goatName;
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
