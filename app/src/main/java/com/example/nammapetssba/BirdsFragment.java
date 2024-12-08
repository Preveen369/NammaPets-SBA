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

public class BirdsFragment extends Fragment {

    private RecyclerView recyclerView;
    private BirdsAdapter birdsAdapter;
    private String ownerId;
    private String categoryId;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_birds, container, false);

        // Get ownerId and categoryId passed from previous activities
        Bundle bundle = getArguments();
        if (bundle != null) {
            ownerId = bundle.getString("USER_ID");
            categoryId = bundle.getString("CATEGORY_ID");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_birds);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns

        categoryName = "Birds";

        // Dummy data for bird list
        List<String> birdList = Arrays.asList("African Grey Parrot", "Giant Macaw", "Love Birds", "Cockatiel", "Goldfinch",
                "Sparrow", "Pigeon", "Cuckoo");
        List<Integer> birdImages = Arrays.asList(R.drawable.african_grey_parrot, R.drawable.macaw, R.drawable.love_birds, R.drawable.cockatiel,
                R.drawable.goldfinch, R.drawable.sparrow, R.drawable.pigeon, R.drawable.cuckoo);
        List<String> birdPrices = Arrays.asList("$5000", "$5000", "$5000", "$5000", "$5000", "$5000", "$5000", "$5000");
        List<String> birdDescriptions = Arrays.asList(
                "Parrots are colorful and talkative birds, perfect as pets.",
                "Peacocks are known for their majestic feather displays.",
                "Sparrows are small, friendly birds commonly found in gardens.",
                "Pigeons are highly adaptive and widely spread birds.",
                "Crows are intelligent and resourceful birds.",
                "Eagles are powerful predators known for their sharp vision.",
                "Eagles are powerful predators known for their sharp vision.",
                "Eagles are powerful predators known for their sharp vision."
        );

        // Set up adapter
        birdsAdapter = new BirdsAdapter(birdList, birdImages, birdPrices, birdDescriptions);
        recyclerView.setAdapter(birdsAdapter);

        // Set item click listener
        birdsAdapter.setOnItemClickListener((birdName, birdImage, birdPrice, birdDescription) -> {
            // Generate deterministic petId using a hash
            String petId = generatePetId(ownerId, categoryId, birdName);

            // Check if the pet already exists in the database
            DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
            petRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    // Create and add the pet to the database if it doesn't already exist
                    Pet pet = new Pet(petId, ownerId, categoryId, birdName, categoryName, birdPrice, birdDescription);
                    petRef.setValue(pet);
                }
            });

            // Pass data to PetDetailFragment
            Fragment petDetailFragment = PetDetailFragment.newInstance(birdName, birdImage, birdPrice, birdDescription, categoryName);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, petDetailFragment)
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });

        return view;
    }

    // Utility method to generate a deterministic petId
    private String generatePetId(String ownerId, String categoryId, String birdName) {
        try {
            String input = ownerId + categoryId + birdName;
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
