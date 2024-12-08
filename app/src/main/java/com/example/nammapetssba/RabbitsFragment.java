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

public class RabbitsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RabbitsAdapter rabbitsAdapter;
    private String ownerId;
    private String categoryId;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rabbits, container, false);

        // Get ownerId and categoryId passed from previous activities
        Bundle bundle = getArguments();
        if (bundle != null) {
            ownerId = bundle.getString("USER_ID");
            categoryId = bundle.getString("CATEGORY_ID");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_rabbits);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns

        categoryName = "Rabbits";

        // Dummy data for rabbit list
        List<String> rabbitList = Arrays.asList("Orange Rabbit", "Champaign Rabbit", "Flemish Giant", "White Rabbit", "Dutch Rabbit", "European Rabbit");

        List<Integer> rabbitImages = Arrays.asList(R.drawable.orange_rabbit, R.drawable.champaign_d_argent_rabbit, R.drawable.flemish_giant_rabbit, R.drawable.white_rabbit,
                R.drawable.dutch_rabbit, R.drawable.european_rabbit);

        List<String> rabbitPrices = Arrays.asList("$1800", "$2500", "$2000", "$2200", "$2700", "$3000");

        List<String> rabbitDescriptions = Arrays.asList(
                "A small, friendly, and active rabbit.",
                "Known for its elegant fur and calm nature.",
                "Large breed, gentle and friendly.",
                "A classic white rabbit known for its friendly nature.",
                "A domesticated breed with a variety of colors.",
                "A hardy rabbit breed, great for beginners."
        );

        // Set up the adapter with the new data
        rabbitsAdapter = new RabbitsAdapter(rabbitList, rabbitImages, rabbitPrices, rabbitDescriptions);
        recyclerView.setAdapter(rabbitsAdapter);

        // Set the item click listener to handle clicks
        rabbitsAdapter.setOnItemClickListener((rabbitName, rabbitImage, rabbitPrice, rabbitDescription) -> {
            // Generate deterministic petId using a hash
            String petId = generatePetId(ownerId, categoryId, rabbitName);

            // Check if the rabbit already exists in the database
            DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
            petRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    // Create and add the pet to the database if it doesn't already exist
                    Pet pet = new Pet(petId, ownerId, categoryId, rabbitName, categoryName, rabbitPrice, rabbitDescription);
                    petRef.setValue(pet);
                }
            });

            // Pass the rabbit details to PetDetailFragment
            Fragment petDetailFragment = PetDetailFragment.newInstance(rabbitName, rabbitImage, rabbitPrice, rabbitDescription, categoryName);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, petDetailFragment)
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });

        return view;
    }

    // Utility method to generate a deterministic petId
    private String generatePetId(String ownerId, String categoryId, String rabbitName) {
        try {
            String input = ownerId + categoryId + rabbitName;
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
