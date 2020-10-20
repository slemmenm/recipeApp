package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.View;

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
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("listener2", "position" + position);
                //Intent intent = new Intent(RecyclerViewActivity.this, RecipeActivity.class);
                //RecyclerViewActivity.this.startActivity(intent);
            }
        };
        RecipeAdapter adapter = new RecipeAdapter(data, listener);
        recyclerView.setAdapter(adapter);
    }
}