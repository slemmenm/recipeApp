package com.example.cookbook;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CookbookRoomDao {
    // get all recipes
    @Query("SELECT * FROM cookbookroom")
    List<CookbookRoom> getRecipes();

    // get one recipe
    @Query("SELECT * FROM cookbookroom where id = :id")
    CookbookRoom getOneRecipe(int id);

    // check if recipe exists
    @Query("SELECT EXISTS(SELECT * FROM cookbookroom WHERE id = :id)")
    Boolean recipeExists(int id);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CookbookRoom recipe);

    @Delete
    void delete(CookbookRoom recipe);
}
