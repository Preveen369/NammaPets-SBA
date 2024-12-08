package com.example.nammapetssba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BirdsAdapter extends RecyclerView.Adapter<BirdsAdapter.BirdViewHolder> {

    private List<String> birdList;
    private List<Integer> birdImages; // List to store the images
    private List<String> birdPrices; // List to store the prices
    private List<String> birdDescriptions; // List to store the descriptions
    private OnItemClickListener onItemClickListener; // Listener for item clicks

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String birdName, int birdImage, String birdPrice, String birdDescription);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public BirdsAdapter(List<String> birdList, List<Integer> birdImages, List<String> birdPrices, List<String> birdDescriptions) {
        this.birdList = birdList;
        this.birdImages = birdImages;
        this.birdPrices = birdPrices;
        this.birdDescriptions = birdDescriptions;
    }

    @NonNull
    @Override
    public BirdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bird_item, parent, false);
        return new BirdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirdViewHolder holder, int position) {
        String birdName = birdList.get(position);
        int birdImage = birdImages.get(position);
        String birdPrice = birdPrices.get(position);
        String birdDescription = birdDescriptions.get(position);

        holder.birdName.setText(birdName);
        holder.birdImage.setImageResource(birdImage); // Set the image for the current item
        holder.birdPrice.setText(birdPrice); // Set the price for the current item
        holder.birdDescription.setText(birdDescription); // Set the description for the current item

        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(birdName, birdImage, birdPrice, birdDescription);
            }
        });
    }

    @Override
    public int getItemCount() {
        return birdList.size();
    }

    public static class BirdViewHolder extends RecyclerView.ViewHolder {
        TextView birdName;
        ImageView birdImage;
        TextView birdPrice;
        TextView birdDescription;

        public BirdViewHolder(@NonNull View itemView) {
            super(itemView);
            birdName = itemView.findViewById(R.id.text_bird_name);
            birdImage = itemView.findViewById(R.id.image_bird);
            birdPrice = itemView.findViewById(R.id.text_bird_price);
            birdDescription = itemView.findViewById(R.id.text_bird_description);
        }
    }
}
