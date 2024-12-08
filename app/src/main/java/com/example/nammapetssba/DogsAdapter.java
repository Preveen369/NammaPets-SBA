package com.example.nammapetssba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogViewHolder> {

    private List<String> dogList;
    private List<Integer> dogImages; // List to store the images
    private List<String> dogPrices; // List to store the prices
    private List<String> dogDescriptions; // List to store the descriptions
    private OnItemClickListener onItemClickListener; // Listener for item clicks

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String dogName, int dogImage, String dogPrice, String dogDescription);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Updated constructor to accept descriptions
    public DogsAdapter(List<String> dogList, List<Integer> dogImages, List<String> dogPrices, List<String> dogDescriptions) {
        this.dogList = dogList;
        this.dogImages = dogImages;
        this.dogPrices = dogPrices;
        this.dogDescriptions = dogDescriptions;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_item, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        String dogName = dogList.get(position);
        int dogImage = dogImages.get(position);
        String dogPrice = dogPrices.get(position);
        String dogDescription = dogDescriptions.get(position); // Get description for the dog

        holder.dogName.setText(dogName);
        holder.dogImage.setImageResource(dogImage); // Set the image for the current item
        holder.dogPrice.setText(dogPrice); // Set the price for the current item
        holder.dogDescription.setText(dogDescription); // Set the price for the current item


        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(dogName, dogImage, dogPrice, dogDescription); // Pass the description
            }
        });
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public static class DogViewHolder extends RecyclerView.ViewHolder {
        TextView dogName;
        ImageView dogImage;
        TextView dogPrice;
        TextView dogDescription;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.text_dog_name);
            dogImage = itemView.findViewById(R.id.image_dog);
            dogPrice = itemView.findViewById(R.id.text_dog_price);
            dogDescription = itemView.findViewById(R.id.text_dog_description);

        }
    }
}
