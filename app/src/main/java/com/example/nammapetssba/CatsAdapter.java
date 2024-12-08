package com.example.nammapetssba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CatsAdapter extends RecyclerView.Adapter<CatsAdapter.CatViewHolder> {

    private List<String> catList;
    private List<Integer> catImages; // List to store the images
    private List<String> catPrices; // List to store the prices
    private List<String> catDescriptions; // List to store the descriptions
    private OnItemClickListener onItemClickListener; // Listener for item clicks

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String catName, int catImage, String catPrice, String catDescription);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CatsAdapter(List<String> catList, List<Integer> catImages, List<String> catPrices, List<String> catDescriptions) {
        this.catList = catList;
        this.catImages = catImages;
        this.catPrices = catPrices;
        this.catDescriptions = catDescriptions;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        String catName = catList.get(position);
        int catImage = catImages.get(position);
        String catPrice = catPrices.get(position);
        String catDescription = catDescriptions.get(position);

        holder.catName.setText(catName);
        holder.catImage.setImageResource(catImage); // Set the image for the current item
        holder.catPrice.setText(catPrice); // Set the price for the current item
        holder.catDescription.setText(catDescription); // Set the description for the current item


        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(catName, catImage, catPrice, catDescription);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public static class CatViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        ImageView catImage;
        TextView catPrice;
        TextView catDescription;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.text_cat_name);
            catImage = itemView.findViewById(R.id.image_cat);
            catPrice = itemView.findViewById(R.id.text_cat_price);
            catDescription = itemView.findViewById(R.id.text_cat_description);
        }
    }
}
