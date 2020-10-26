package com.example.cookbook;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CookbookRoom {
    @PrimaryKey
    public int id;

    @ColumnInfo
    public String title;
    public String description;
    public String imageUriString;
}
