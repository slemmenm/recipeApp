package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

    private int position;
    @Override
    protected void onResume() {
        super.onResume();

        position = this.getIntent().getIntExtra("id_position", -1);
        Log.d("listener2", "onResume" + position);
    }
}