package com.example.cookbook;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private RecyclerViewClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener listener;

        public TextView title;
        public ImageButton parentView;

        public ViewHolder(View parent, TextView title, ImageButton imageButton, RecyclerViewClickListener listener) {
            super(parent);
            this.listener = listener;
            this.title = title;
            this.parentView = imageButton;
            parentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    private ArrayList<Recipe> recipes;

    public RecipeAdapter(ArrayList<Recipe> recipes, RecyclerViewClickListener listener) {
        this.listener = listener;
        this.recipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                R.layout.recycler_view_tile,
                parent,
                false);

        TextView title = view.findViewById(R.id.text1);
        ImageButton imageButton = view.findViewById(R.id.recycler_view_imageButton);

        return new ViewHolder(view, title, imageButton, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = this.recipes.get(position);
        // https://sites.google.com/a/homebrewandtechnology.com/www/blog/recyclevewadaptershowwrongdatawhenscrolling
        holder.setIsRecyclable(false);
        holder.title.setText(recipe.getTitle());
        if(recipe.getImageUriString() != null) {
            holder.parentView.setImageURI(Uri.parse(recipe.getImageUriString()));
        }
    }

    @Override
    public int getItemCount() {
        return this.recipes.size();
    }
}

