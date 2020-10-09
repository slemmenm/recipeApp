package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.UserManager;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        // Creates a vertical GridLayoutManager
        //TODO: check where to use gridLayoutManger, adapter, etc. -> define it outside onCreate()
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<Recipe> data = RecipeManager.getRecipes();
        RecipeAdapter adapter = new RecipeAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}