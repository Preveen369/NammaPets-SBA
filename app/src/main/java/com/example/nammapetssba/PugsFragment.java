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

public class PugsFragment extends Fragment {
    private RecyclerView recyclerView;
    private PugsAdapter pugsAdapter;
    private String ownerId;
    private String categoryId;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pugs, container, false);

        // Get ownerId and categoryId passed from previous activities
        Bundle bundle = getArguments();
        if (bundle != null) {
            ownerId = bundle.getString("USER_ID");
            categoryId = bundle.getString("CATEGORY_ID");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_pugs);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns

        categoryName = "Pugs";

        // Dummy data for pug list
        List<String> pugList = Arrays.asList("Sable Variant", "White Variant", "Fawn Variant", "Black Variant", "Apricot Variant",
                "Panda Variant");

        List<Integer> pugImages = Arrays.asList(R.drawable.sable_pug, R.drawable.white_pug, R.drawable.fawn_pug, R.drawable.black_pug,
                R.drawable.apricot_pug, R.drawable.panda_pug);

        List<String> pugPrices = Arrays.asList("$2500", "$3000", "$3200", "$2800", "$3500", "$2700");

        List<String> pugDescriptions = Arrays.asList("Friendly, loyal, and affectionate dog breed.",
                "Cute and energetic pug, loves to play around.",
                "A playful and strong-willed pug breed.",
                "Loyal and loving, always ready for a cuddle.",
                "An intelligent and charming pug with lots of energy.",
                "An adorable and playful pug with great personality.");

        // Set up adapter with description list
        pugsAdapter = new PugsAdapter(pugList, pugImages, pugPrices, pugDescriptions);
        recyclerView.setAdapter(pugsAdapter);

        // Set item click listener
        pugsAdapter.setOnItemClickListener((pugName, pugImage, pugPrice, pugDescription) -> {
            // Generate deterministic petId using a hash
            String petId = generatePetId(ownerId, categoryId, pugName);

            // Check if the pet already exists in the database
            DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
            petRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().exists()) {
                    // Create and add the pet to the database if it doesn't already exist
                    Pet pet = new Pet(petId, ownerId, categoryId, pugName, categoryName, pugPrice, pugDescription);
                    petRef.setValue(pet);
                }
            });

            // Pass data to PetDetailFragment
            Fragment petDetailFragment = PetDetailFragment.newInstance(pugName, pugImage, pugPrice, pugDescription, categoryName);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, petDetailFragment)
                    .addToBackStack(null) // Add to back stack for navigation
                    .commit();
        });

        return view;
    }

    // Utility method to generate a deterministic petId
    private String generatePetId(String ownerId, String categoryId, String pugName) {
        try {
            String input = ownerId + categoryId + pugName;
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
