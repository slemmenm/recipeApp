package com.example.cookbook;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CookbookRoom {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String titel;
    public String description;
}
