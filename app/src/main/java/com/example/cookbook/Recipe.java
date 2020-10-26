package com.example.cookbook;

import android.net.Uri;

public class Recipe {
    private String title;
    private String description;
    private String imageUriString;

    // image

    public Recipe(String title, String description, String imageUriString) {
        this.title = title;
        this.description = description;
        this.imageUriString = imageUriString;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    String getImageUriString() {
        return imageUriString;
    }
}
