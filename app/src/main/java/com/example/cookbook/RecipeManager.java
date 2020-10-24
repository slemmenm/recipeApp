package com.example.cookbook;

import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public final class RecipeManager {


    private static ArrayList<Recipe> recipes;

    static {
        RecipeManager.recipes = new ArrayList<Recipe>();

        for (int i = 1; i <= 100; i++) {
            RecipeManager.recipes.add(new Recipe("recipe title... ", "recipe description..."));
        }
    }

    public static ArrayList<Recipe> getRecipes() {
        return RecipeManager.recipes;
    }

}
