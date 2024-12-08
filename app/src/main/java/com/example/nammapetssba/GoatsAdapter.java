package com.example.nammapetssba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GoatsAdapter extends RecyclerView.Adapter<GoatsAdapter.GoatViewHolder> {

    private List<String> goatList;
    private List<Integer> goatImages; // List to store the images
    private List<String> goatPrices; // List to store the prices
    private List<String> goatDescriptions; // List to store the descriptions
    private OnItemClickListener onItemClickListener; // Listener for item clicks

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String goatName, int goatImage, String goatPrice, String goatDescription);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Constructor that now takes goatDescriptions as a parameter
    public GoatsAdapter(List<String> goatList, List<Integer> goatImages, List<String> goatPrices, List<String> goatDescriptions) {
        this.goatList = goatList;
        this.goatImages = goatImages;
        this.goatPrices = goatPrices;
        this.goatDescriptions = goatDescriptions; // Initialize descriptions list
    }

    @NonNull
    @Override
    public GoatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goat_item, parent, false);
        return new GoatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoatViewHolder holder, int position) {
        String goatName = goatList.get(position);
        int goatImage = goatImages.get(position);
        String goatPrice = goatPrices.get(position);
        String goatDescription = goatDescriptions.get(position); // Get the description

        holder.goatName.setText(goatName);
        holder.goatImage.setImageResource(goatImage); // Set the image for the current item
        holder.goatPrice.setText(goatPrice); // Set the price for the current item
        holder.goatDescription.setText(goatDescription); // Set the description for the current item


        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(goatName, goatImage, goatPrice, goatDescription); // Pass description
            }
        });
    }

    @Override
    public int getItemCount() {
        return goatList.size();
    }

    public static class GoatViewHolder extends RecyclerView.ViewHolder {
        TextView goatName;
        ImageView goatImage;
        TextView goatPrice;
        TextView goatDescription;

        public GoatViewHolder(@NonNull View itemView) {
            super(itemView);
            goatName = itemView.findViewById(R.id.text_goat_name);
            goatImage = itemView.findViewById(R.id.image_goat);
            goatPrice = itemView.findViewById(R.id.text_goat_price);
            goatDescription = itemView.findViewById(R.id.text_goat_description);
        }
    }
}
