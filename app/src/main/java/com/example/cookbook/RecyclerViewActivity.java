package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "debuggingRoom";
    private static final String ROOM_DB = "room.db";

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
        readAllRecipesFromRoom(data);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("listener2", "position" + position);
                Intent intent = new Intent(RecyclerViewActivity.this, RecipeActivity.class);
                intent.putExtra("id_position", position);
                RecyclerViewActivity.this.startActivity(intent);
            }
        };
        RecipeAdapter adapter = new RecipeAdapter(data, listener);
        recyclerView.setAdapter(adapter);
    }

    // to read data from Room
    // TODO or in onResume???
    @Override
    protected void onStart() {
        super.onStart();

    }

    // read from Room
    private void readAllRecipesFromRoom(ArrayList<Recipe> data) {
        Runnable read = new Runnable() {
            @Override
            public void run() {
                CookbookRoomDatabase db = Room.databaseBuilder(RecyclerViewActivity.this, CookbookRoomDatabase.class, ROOM_DB).build();

                List<CookbookRoom> entries = db.cookbookRoomDao().getRecipes();
                for (CookbookRoom recipe : entries) {
                    Recipe tmp = new Recipe(recipe.title, recipe.description);
                    data.set(recipe.id, tmp);
                    Log.d(DEBUG_TAG, "DB CookbookRoom | " + recipe.id + " | " + recipe.title);
                }

                db.close();
            }
        };

        new Thread(read).start();
    }
}