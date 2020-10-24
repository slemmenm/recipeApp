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

    private ArrayList<Recipe> data;
    private RecyclerView recyclerView;
    private RecyclerViewClickListener listener;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recycler_view);

        // GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        // Creates a vertical GridLayoutManager
        //TODO: check where to use gridLayoutManger, adapter, etc. -> define it outside onCreate()
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        data = RecipeManager.getRecipes();

        // read all data once
        readAllRecipesFromRoom(data);

        // create listener once
        listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("listener2", "position" + position);
                Intent intent = new Intent(RecyclerViewActivity.this, RecipeActivity.class);
                intent.putExtra("id_position", position);
                intent.putExtra("title", data.get(position).getTitle());
                intent.putExtra("description", data.get(position).getDescription());
                RecyclerViewActivity.this.startActivityForResult(intent, 1);
            }
        };

        adapter = new RecipeAdapter(data, listener);
        recyclerView.setAdapter(adapter);
    }

    // to read data from Room
    // TODO or in onResume???
    @Override
    protected void onStart() {
        super.onStart();

    }

    // get title, description back from recipe, -> update data
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                int position = data.getIntExtra("position", -1);
                Log.d("listener2", "POSITION: " + position);
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");
                Log.d("listener2", "test....works");

                // update data

            }
        }
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