package com.example.cookbook;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CookbookRoomDao {
    @Query("SELECT * FROM cookbookroom")
    List<CookbookRoom> getEntries();

    @Insert
    void insert(CookbookRoom entry);

    @Delete
    void delete(CookbookRoom entry);
}
