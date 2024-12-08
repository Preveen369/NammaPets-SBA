package com.example.nammapetssba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CowsAdapter extends RecyclerView.Adapter<CowsAdapter.CowViewHolder> {

    private List<String> cowList; // List of cow names
    private List<Integer> cowImages; // List of cow images
    private List<String> cowPrices; // List of cow prices
    private List<String> cowDescriptions; // List of cow descriptions
    private OnItemClickListener onItemClickListener; // Listener for item clicks

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String cowName, int cowImage, String cowPrice, String cowDescription);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CowsAdapter(List<String> cowList, List<Integer> cowImages, List<String> cowPrices, List<String> cowDescriptions) {
        this.cowList = cowList;
        this.cowImages = cowImages;
        this.cowPrices = cowPrices;
        this.cowDescriptions = cowDescriptions;
    }

    @NonNull
    @Override
    public CowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cow_item, parent, false);
        return new CowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CowViewHolder holder, int position) {
        String cowName = cowList.get(position);
        int cowImage = cowImages.get(position);
        String cowPrice = cowPrices.get(position);
        String cowDescription = cowDescriptions.get(position);

        holder.cowName.setText(cowName);
        holder.cowImage.setImageResource(cowImage); // Set the image for the current item
        holder.cowPrice.setText(cowPrice); // Set the price for the current item
        holder.cowDescription.setText(cowDescription); // Set the description for the current item


        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(cowName, cowImage, cowPrice, cowDescription);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cowList.size();
    }

    public static class CowViewHolder extends RecyclerView.ViewHolder {
        TextView cowName;
        ImageView cowImage;
        TextView cowPrice;
        TextView cowDescription;

        public CowViewHolder(@NonNull View itemView) {
            super(itemView);
            cowName = itemView.findViewById(R.id.text_cow_name);
            cowImage = itemView.findViewById(R.id.image_cow);
            cowPrice = itemView.findViewById(R.id.text_cow_price);
            cowDescription = itemView.findViewById(R.id.text_cow_description);
        }
    }
}
