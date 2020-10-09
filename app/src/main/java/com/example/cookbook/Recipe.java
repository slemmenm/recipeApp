package com.example.cookbook;

public class Recipe {
    private String title;
    private String description;

    // image

    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }
}
