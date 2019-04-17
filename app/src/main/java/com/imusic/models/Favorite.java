package com.imusic.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "favorite_table",
        foreignKeys = @ForeignKey(entity = Song.class,
                parentColumns = "id",
                childColumns = "id_song", onDelete = CASCADE))
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id = 0;
    @ColumnInfo(name = "id_song")
    private int id_song = 0;
}
