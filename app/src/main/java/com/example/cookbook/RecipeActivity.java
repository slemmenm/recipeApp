package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class RecipeActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "debuggingRoom";
    private static final String ROOM_DB = "room.db";
    private EditText title;
    private EditText description;
    private String tmpTitle;
    private String tmpDescription;

    private static final String FILE_TYPE = "image/*";
    private static final int OPEN_DOCUMENT_CODE = 1;
    ImageView imageView;
    private Uri imageUri;
    private String imageUriString;
    private Boolean imageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        title = findViewById(R.id.recipe_view_title);
        description = findViewById(R.id.recipe_view_description);

        imageView = findViewById(R.id.recipe_image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

    }

    // retrieve data for this recipe
    @Override
    protected void onResume() {
        super.onResume();


        // save tmp title and description, if image is (re)selected afterwards
        if(tmpTitle != null) {
            this.title.setText(tmpTitle);
        }
        else {
            final String title = this.getIntent().getStringExtra("title");
            this.title.setText(title);
        }

        if(tmpDescription != null) {
            this.description.setText(tmpDescription);
        }
        else {
            final String description = this.getIntent().getStringExtra("description");
            this.description.setText(description);
        }

        // prevent resetting image in this function onResume after image is changed through image picker
        final String imageUriString = this.getIntent().getStringExtra("imageUriString");
        if(imageUriString != null && !imageChanged) {
            this.imageUriString = imageUriString;
            imageUri = Uri.parse(imageUriString);
            this.imageView.setImageURI(Uri.parse(imageUriString));
        }
    }

    // to save data to Room
    @Override
    protected void onPause() {
        super.onPause();

        writeOneRecipeToRoom();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putExtra("title", this.title.getText().toString());
        intent.putExtra("description", this.description.getText().toString());
        intent.putExtra("imageUriString", this.imageUriString);
        setResult(RESULT_OK, intent);
        imageChanged = false;
        finish();
    }

    // load data from Room
    private int position;

    @Override
    protected void onStart() {
        super.onStart();

        position = this.getIntent().getIntExtra("id_position", -1);
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
                if(imageUri != null) {
                    recipe.imageUriString = imageUri.toString();
                }

                CookbookRoomDatabase db = Room.databaseBuilder(RecipeActivity.this, CookbookRoomDatabase.class, ROOM_DB).build();
                db.cookbookRoomDao().insert(recipe);
                db.close();
            }
        };

        new Thread(write).start();
    }

    private void selectImage() {
        tmpTitle = title.getText().toString();
        tmpDescription = description.getText().toString();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(FILE_TYPE);

        startActivityForResult(intent, OPEN_DOCUMENT_CODE);
    }

    @Override
    public void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if(req == OPEN_DOCUMENT_CODE && res == Activity.RESULT_OK) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            imageUriString= imageUri.toString();
            imageChanged = true;
        }
    }

}