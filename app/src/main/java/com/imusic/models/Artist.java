package com.imusic.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "artist_table")
public class Artist {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id = 0;

    @ColumnInfo(name = "artist_name")
    private String artist_name = "";

    @ColumnInfo(name = "artist_image")
    private String artist_image = null;

    @ColumnInfo(name = "count_song")
    private String count_song = "";

    @Ignore
    public Artist() {
    }

    public Artist(int id, String artist_name, String count_song) {
        this.id = id;
        this.artist_name = artist_name;
        this.count_song = count_song;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getArtist_image() {
        return artist_image;
    }

    public void setArtist_image(String artist_image) {
        this.artist_image = artist_image;
    }

    public String getCount_song() {
        return count_song;
    }

    public void setCount_song(String count_song) {
        this.count_song = count_song;
    }
}
