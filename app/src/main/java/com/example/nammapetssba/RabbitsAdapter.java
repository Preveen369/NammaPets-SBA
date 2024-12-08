package com.example.nammapetssba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RabbitsAdapter extends RecyclerView.Adapter<RabbitsAdapter.RabbitViewHolder> {

    private List<String> rabbitList;
    private List<Integer> rabbitImages; // List to store the images
    private List<String> rabbitPrices; // List to store the prices
    private List<String> rabbitDescriptions; // List to store the descriptions
    private OnItemClickListener onItemClickListener; // Listener for item clicks

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(String rabbitName, int rabbitImage, String rabbitPrice, String rabbitDescription);
    }

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public RabbitsAdapter(List<String> rabbitList, List<Integer> rabbitImages, List<String> rabbitPrices, List<String> rabbitDescriptions) {
        this.rabbitList = rabbitList;
        this.rabbitImages = rabbitImages;
        this.rabbitPrices = rabbitPrices;
        this.rabbitDescriptions = rabbitDescriptions;
    }

    @NonNull
    @Override
    public RabbitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rabbit_item, parent, false);
        return new RabbitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RabbitViewHolder holder, int position) {
        String rabbitName = rabbitList.get(position);
        int rabbitImage = rabbitImages.get(position);
        String rabbitPrice = rabbitPrices.get(position);
        String rabbitDescription = rabbitDescriptions.get(position); // Get the description for the current item

        holder.rabbitName.setText(rabbitName);
        holder.rabbitImage.setImageResource(rabbitImage); // Set the image for the current item
        holder.rabbitPrice.setText(rabbitPrice); // Set the price for the current item
        holder.rabbitDescription.setText(rabbitDescription); // Set the description for the current item


        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(rabbitName, rabbitImage, rabbitPrice, rabbitDescription); // Pass the description
            }
        });
    }

    @Override
    public int getItemCount() {
        return rabbitList.size();
    }

    public static class RabbitViewHolder extends RecyclerView.ViewHolder {
        TextView rabbitName;
        ImageView rabbitImage;
        TextView rabbitPrice;
        TextView rabbitDescription;

        public RabbitViewHolder(@NonNull View itemView) {
            super(itemView);
            rabbitName = itemView.findViewById(R.id.text_rabbit_name);
            rabbitImage = itemView.findViewById(R.id.image_rabbit);
            rabbitPrice = itemView.findViewById(R.id.text_rabbit_price);
            rabbitDescription = itemView.findViewById(R.id.text_rabbit_description);
        }
    }
}
