package com.example.cookbook;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CookbookRoomDao {
    @Query("SELECT * FROM cookbookroom")
    List<CookbookRoom> getRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CookbookRoom recipe);

    @Delete
    void delete(CookbookRoom recipe);
}
