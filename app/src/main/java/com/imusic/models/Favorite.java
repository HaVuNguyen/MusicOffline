package com.imusic.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id = 0;

    @ColumnInfo(name = "id_song")
    private long idSong;

    @Ignore
    public Favorite() {
    }

    public Favorite(long id, long idSong) {
        this.id = id;
        this.idSong = idSong;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdSong() {
        return idSong;
    }

    public void setIdSong(long idSong) {
        this.idSong = idSong;
    }
}
