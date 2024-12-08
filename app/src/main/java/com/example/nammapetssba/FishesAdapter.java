package com.example.nammapetssba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FishesAdapter extends RecyclerView.Adapter<FishesAdapter.FishViewHolder> {

    private List<String> fishList;
    private List<Integer> fishImages; // List to store the images
    private List<String> fishPrices; // List to store the prices
    private List<String> fishDescriptions; // List to store the descriptions
    private OnItemClickListener onItemClickListener; // Listener for item clicks

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String fishName, int fishImage, String fishPrice, String fishDescription);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Modified constructor to include descriptions
    public FishesAdapter(List<String> fishList, List<Integer> fishImages, List<String> fishPrices, List<String> fishDescriptions) {
        this.fishList = fishList;
        this.fishImages = fishImages;
        this.fishPrices = fishPrices;
        this.fishDescriptions = fishDescriptions;
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_item, parent, false);
        return new FishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        String fishName = fishList.get(position);
        int fishImage = fishImages.get(position);
        String fishPrice = fishPrices.get(position);
        String fishDescription = fishDescriptions.get(position);

        holder.fishName.setText(fishName);
        holder.fishImage.setImageResource(fishImage); // Set the image for the current item
        holder.fishPrice.setText(fishPrice); // Set the price for the current item
        holder.fishDescription.setText(fishDescription); // Set the price for the current item

        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(fishName, fishImage, fishPrice, fishDescription);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    public static class FishViewHolder extends RecyclerView.ViewHolder {
        TextView fishName;
        ImageView fishImage;
        TextView fishPrice;
        TextView fishDescription;

        public FishViewHolder(@NonNull View itemView) {
            super(itemView);
            fishName = itemView.findViewById(R.id.text_fish_name);
            fishImage = itemView.findViewById(R.id.image_fish);
            fishPrice = itemView.findViewById(R.id.text_fish_price);
            fishDescription = itemView.findViewById(R.id.text_fish_description);
        }
    }
}
