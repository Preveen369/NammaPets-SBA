package com.example.nammapetssba;

import android.app.Activity;
import android.widget.ImageView;

public class MainUIManager {
    private ImageView img1, img2, img3, img4, viewAll, profile, home, uploads, cart, favourites;

    public MainUIManager(Activity activity) {
        img1 = activity.findViewById(R.id.cat1);
        img2 = activity.findViewById(R.id.cat2);
        img3 = activity.findViewById(R.id.cat3);
        img4 = activity.findViewById(R.id.cat4);
        viewAll = activity.findViewById(R.id.imageView5);
        profile = activity.findViewById(R.id.imageView15);
        home = activity.findViewById(R.id.imageView11);
        uploads = activity.findViewById(R.id.imageView13);
        cart = activity.findViewById(R.id.imageView12);
        favourites = activity.findViewById(R.id.imageView14);
    }

    // Provide getters for UI components
    public ImageView getViewAll() { return viewAll; }
    public ImageView getProfile() { return profile; }
    public ImageView getHome() { return home; }
    public ImageView getUploads() { return uploads; }
    public ImageView getCart() { return cart; }
    public ImageView getFavourites() { return favourites; }
}
