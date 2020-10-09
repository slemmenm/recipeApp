package com.example.cookbook;

import java.util.ArrayList;

public final class RecipeManager {
    private static ArrayList<Recipe> recipes;

    static {
        RecipeManager.recipes = new ArrayList<Recipe>();

        for (int i = 1; i <= 100; i++) {
            RecipeManager.recipes.add(new Recipe("TitleNr. " + i, "default description"));
        }
    }

    public static ArrayList<Recipe> getRecipes() {
        return RecipeManager.recipes;
    }
}
