package com.imusic.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "playlist_table")
public class Playlist {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id = 0;

    @ColumnInfo(name = "title")
    private String title = "";

    @ColumnInfo(name = "from_users")
    private int fromUsers = 0;

    public Playlist(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFromUsers() {
        return fromUsers;
    }

    public void setFromUsers(int fromUsers) {
        this.fromUsers = fromUsers;
    }
}
