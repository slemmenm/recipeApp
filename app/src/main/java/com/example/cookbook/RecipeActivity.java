package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "debuggingRoom";
    private static final String ROOM_DB = "room.db";
    private EditText title;
    private EditText description;

    private static final String FILE_TYPE = "image/*";
    private static final int OPEN_DOCUMENT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        title = findViewById(R.id.recipe_view_title);
        description = findViewById(R.id.recipe_view_description);
        Log.d("listener2", "onCreate Recipe");

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

        // retrieve data for this recipe
        final String title = this.getIntent().getStringExtra("title");
        this.title.setText(title);

        final String description = this.getIntent().getStringExtra("description");
        this.description.setText(description);

    }

    // to save data to Room
    @Override
    protected void onPause() {
        super.onPause();

        writeOneRecipeToRoom();
        Log.d("listener2", "onPause Recipe");


    }
    @Override
    public void onBackPressed() {
        // your code.
        Log.d("listener2", "back pressed");
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("title", this.title.getText().toString());
        intent.putExtra("description", this.description.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    // load data from Room
    private int position;

    @Override
    protected void onStart() {
        super.onStart();

        position = this.getIntent().getIntExtra("id_position", -1);
        Log.d("listener2", "onStart" + position);

        readOneRecipeFromRoom(position);
    }

    // write one recipe to Room
    private void writeOneRecipeToRoom() {
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

    // read one recipe from Room
    // TODO if possible and display it
    private void readOneRecipeFromRoom(int id) {
        Runnable read = new Runnable() {
            @Override
            public void run() {
                CookbookRoomDatabase db = Room.databaseBuilder(RecipeActivity.this, CookbookRoomDatabase.class, ROOM_DB).build();

                // read only if row with id = id exists, db not prepopulate
                if(db.cookbookRoomDao().recipeExists(id)) {
                    CookbookRoom recipe = db.cookbookRoomDao().getOneRecipe(id);
                    Log.d("listener2", recipe.id + " title:  " + recipe.title + "  description: " + recipe.description);
                    //title.setText(recipe.title);
                    //description.setText(recipe.description);
                }
                db.close();
            }
        };
        //runOnUiThread(read);
        new Thread(read).start();
    }

/*    // read from Room
    private void readAllRecipesFromRoom() {
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
    }*/

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(FILE_TYPE);

        startActivityForResult(intent, OPEN_DOCUMENT_CODE);
    }

    @Override
    public void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if(res == Activity.RESULT_OK) {
            Uri uri = data.getData();


        }

    }


}