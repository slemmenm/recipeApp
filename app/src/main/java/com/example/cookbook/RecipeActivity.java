package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "debugging Room";
    private static final String ROOM_DB = "room.db";
    private EditText title;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        title = findViewById(R.id.recipe_view_title);
        description = findViewById(R.id.recipe_view_description);
        /*
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.d("listener2", "before");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d("listener2", "on");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("listener2", "after");

            }
        });*/
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    // to save data to Room
    @Override
    protected void onPause() {
        super.onPause();

        writeDatabaseWithRoom();

    }

    // load data from Room
    private int position;
    @Override
    protected void onStart() {
        super.onStart();

        position = this.getIntent().getIntExtra("id_position", -1);
        Log.d("listener2", "onStart" + position);
    }

    // write to Room
    private void writeDatabaseWithRoom() {
        Runnable write = new Runnable() {
            @Override
            public void run() {
                CookbookRoom recipe = new CookbookRoom();
                recipe.id = position;
                recipe.title = title.getText().toString();
                recipe.description = description.getText().toString();

                CookbookRoomDatabase db = Room.databaseBuilder(RecipeActivity.this, CookbookRoomDatabase.class, ROOM_DB).build();
                db.cookbookRoomDao().insert(recipe);
                db.close();
            }
        };

        new Thread(write).start();
    }

    // read from Room
    private void readDatabaseWithRoom() {
        Runnable read = new Runnable() {
            @Override
            public void run() {
                CookbookRoomDatabase db = Room.databaseBuilder(RecipeActivity.this, CookbookRoomDatabase.class, ROOM_DB).build();

                List<CookbookRoom> entries = db.cookbookRoomDao().getRecipes();
                for (CookbookRoom recipe : entries) {
                    Log.d(DEBUG_TAG, "DB CookbookRoom | " + recipe.id + " | " + recipe.title);
                }

                db.close();
            }
        };

        new Thread(read).start();
    }

}