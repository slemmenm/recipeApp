package com.example.cookbook;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CookbookRoom.class}, version = 1)
public abstract class CookbookRoomDatabase extends RoomDatabase {
    public abstract CookbookRoomDao cookbookRoomDao();
}
