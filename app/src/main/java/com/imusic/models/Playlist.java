package com.imusic.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "playlist_table")
public class Playlist implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id = 0;

    @ColumnInfo(name = "name_playlist")
    private String mTitlePlaylist = "";

    public Playlist(int id, String titlePlaylist) {
        this.id = id;
        mTitlePlaylist = titlePlaylist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitlePlaylist() {
        return mTitlePlaylist;
    }

    public void setTitlePlaylist(String titlePlaylist) {
        mTitlePlaylist = titlePlaylist;
    }
}
