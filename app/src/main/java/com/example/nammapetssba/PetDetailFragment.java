package com.example.nammapetssba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PetDetailFragment extends Fragment {

    private static final String ARG_PET_NAME = "pet_name";
    private static final String ARG_PET_IMAGE = "pet_image";
    private static final String ARG_PET_PRICE = "pet_price";
    private static final String ARG_PET_DESCRIPTION = "pet_description";
    private static final String ARG_PET_CATEGORY = "pet_category_name";

    private FirebaseAuth firebaseAuth;

    public static PetDetailFragment newInstance(String petName, int petImage, String petPrice, String petDescription, String petCategory) {
        PetDetailFragment fragment = new PetDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PET_NAME, petName);
        args.putInt(ARG_PET_IMAGE, petImage);
        args.putString(ARG_PET_PRICE, petPrice);
        args.putString(ARG_PET_DESCRIPTION, petDescription);
        args.putString(ARG_PET_CATEGORY, petCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_detail, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        ImageView petImageView = view.findViewById(R.id.pet_image_view);
        TextView petNameTextView = view.findViewById(R.id.pet_name_text_view);
        TextView petPriceTextView = view.findViewById(R.id.pet_price_text_view);
        TextView petDescriptionTextView = view.findViewById(R.id.pet_description_text_view);
        TextView petCategoryTextView = view.findViewById(R.id.pet_category_text_view);
        Button addToCartButton = view.findViewById(R.id.add_to_cart_btn);
        Button addToFavButton = view.findViewById(R.id.add_to_fav_btn);
        EditText quantity = view.findViewById(R.id.qty_edit_text);

        if (getArguments() != null) {
            String petName = getArguments().getString(ARG_PET_NAME);
            int petImage = getArguments().getInt(ARG_PET_IMAGE);
            String petPrice = getArguments().getString(ARG_PET_PRICE);
            String petDescription = getArguments().getString(ARG_PET_DESCRIPTION);
            String petCategory = getArguments().getString(ARG_PET_CATEGORY);

            petNameTextView.setText(petName);
            petImageView.setImageResource(petImage);
            petPriceTextView.setText(petPrice);
            petDescriptionTextView.setText(petDescription);
            petCategoryTextView.setText(petCategory);
        }

        addToFavButton.setOnClickListener(v -> {
            if (isUserLoggedIn()) {
                String petName = getArguments().getString(ARG_PET_NAME);
                int petImageId = getArguments().getInt(ARG_PET_IMAGE);
                String petCategory = getArguments().getString(ARG_PET_CATEGORY);

                Intent intent = new Intent(getActivity(), Favourites.class);
                intent.putExtra("pet_name", petName);
                intent.putExtra("pet_image", petImageId);
                intent.putExtra("pet_category", petCategory);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Please log in to add to favourites.", Toast.LENGTH_SHORT).show();
            }
        });

        addToCartButton.setOnClickListener(v -> {
            if (isUserLoggedIn()) {
                String petName = getArguments().getString(ARG_PET_NAME);
                String petPrice = getArguments().getString(ARG_PET_PRICE);
                String petDescription = getArguments().getString(ARG_PET_DESCRIPTION);
                int petImageId = getArguments().getInt(ARG_PET_IMAGE);
                String petCategory = getArguments().getString(ARG_PET_CATEGORY);

                String quantityText = quantity.getText().toString().trim();
                if (quantityText.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter the quantity!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int petQuantity;
                try {
                    petQuantity = Integer.parseInt(quantityText);
                    if (petQuantity <= 0) {
                        Toast.makeText(getActivity(), "Quantity must be greater than zero!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Invalid quantity entered!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getActivity(), ShoppingCart.class);
                intent.putExtra("pet_name", petName);
                intent.putExtra("pet_price", petPrice);
                intent.putExtra("pet_description", petDescription);
                intent.putExtra("pet_image", petImageId);
                intent.putExtra("pet_category", petCategory);
                intent.putExtra("pet_qty", petQuantity);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Please log in to add to cart.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private boolean isUserLoggedIn() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        return currentUser != null;
    }
}
