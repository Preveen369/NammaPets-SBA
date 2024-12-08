package com.example.nammapetssba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PugsAdapter extends RecyclerView.Adapter<PugsAdapter.PugViewHolder> {

    private List<String> pugList;
    private List<Integer> pugImages; // List to store the images
    private List<String> pugPrices; // List to store the prices
    private List<String> pugDescriptions; // List to store the descriptions
    private OnItemClickListener onItemClickListener; // Listener for item clicks

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String pugName, int pugImage, String pugPrice, String pugDescription);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Constructor that now takes pugDescriptions as a parameter
    public PugsAdapter(List<String> pugList, List<Integer> pugImages, List<String> pugPrices, List<String> pugDescriptions) {
        this.pugList = pugList;
        this.pugImages = pugImages;
        this.pugPrices = pugPrices;
        this.pugDescriptions = pugDescriptions; // Initialize descriptions list
    }

    @NonNull
    @Override
    public PugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pug_item, parent, false);
        return new PugViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PugViewHolder holder, int position) {
        String pugName = pugList.get(position);
        int pugImage = pugImages.get(position);
        String pugPrice = pugPrices.get(position);
        String pugDescription = pugDescriptions.get(position); // Get the description

        holder.pugName.setText(pugName);
        holder.pugImage.setImageResource(pugImage); // Set the image for the current item
        holder.pugPrice.setText(pugPrice); // Set the price for the current item
        holder.pugDescription.setText(pugDescription); // Set the description for the current item


        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(pugName, pugImage, pugPrice, pugDescription); // Pass description
            }
        });
    }

    @Override
    public int getItemCount() {
        return pugList.size();
    }

    public static class PugViewHolder extends RecyclerView.ViewHolder {
        TextView pugName;
        ImageView pugImage;
        TextView pugPrice;
        TextView pugDescription;

        public PugViewHolder(@NonNull View itemView) {
            super(itemView);
            pugName = itemView.findViewById(R.id.text_pug_name);
            pugImage = itemView.findViewById(R.id.image_pug);
            pugPrice = itemView.findViewById(R.id.text_pug_price);
            pugDescription = itemView.findViewById(R.id.text_pug_description);
        }
    }
}
