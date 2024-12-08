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

public class FishesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FishesAdapter fishesAdapter;
    private String ownerId;
    private String categoryId;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fishes, container, false);

        // Get ownerId and categoryId passed from previous activities
        Bundle bundle = getArguments();
        if (bundle != null) {
            ownerId = bundle.getString("USER_ID");
            categoryId = bundle.getString("CATEGORY_ID");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_fishes);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns

        categoryName = "Fishes";

        // Dummy data for fish list
        List<String> fishList = Arrays.asList("Gourami Fish", "Betta Fish", "Platy Fish", "Koi Fish", "Gold Fish",
                "Flowerhorn", "Angel Fish", "Fish Pellets");

        List<Integer> fishImages = Arrays.asList(R.drawable.gourami_fish, R.drawable.betta_fish, R.drawable.platy_fish, R.drawable.koi_fish,
                R.drawable.gold_fish, R.drawable.flowerhorn_fish, R.drawable.freshwater_angelfish, R.drawable.fish_pellets);

        List<String> fishPrices = Arrays.asList("$200", "$250", "$300", "$350", "$400", "$450", "$500", "$550");

        List<String> fishDescriptions = Arrays.asList(
                "A peaceful fish with vibrant colors.",
                "Known for its aggressive behavior and colorful appearance.",
                "A small and hardy fish, great for beginners.",
                "A popular ornamental fish, often found in ponds.",
                "A well-known fish species, popular in home aquariums.",
                "A large cichlid species known for its aggressive nature.",
                "A freshwater species known for its beauty.",
                "A high-quality fish food, ideal for a variety of species."
        );

        // Set up adapter with descriptions
        fishesAdapter = new FishesAdapter(fishList, fishImages, fishPrices, fishDescriptions);
        recyclerView.setAdapter(fishesAdapter);

        // Set up item click listener
        fishesAdapter.setOnItemClickListener((fishName, fishImage, fishPrice, fishDescription) -> {
            // Generate deterministic petId using a hash
            String petId = generatePetId(ownerId, categoryId, fishName);

            // Check if the fish already exists in the database
            DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
            petRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    // Create and add the pet to the database if it doesn't already exist
                    Pet pet = new Pet(petId, ownerId, categoryId, fishName, categoryName, fishPrice, fishDescription);
                    petRef.setValue(pet);
                }
            });

            // Pass the fish details to PetDetailFragment
            Fragment petDetailFragment = PetDetailFragment.newInstance(fishName, fishImage, fishPrice, fishDescription, categoryName);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, petDetailFragment)
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });

        return view;
    }

    // Utility method to generate a deterministic petId
    private String generatePetId(String ownerId, String categoryId, String fishName) {
        try {
            String input = ownerId + categoryId + fishName;
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
