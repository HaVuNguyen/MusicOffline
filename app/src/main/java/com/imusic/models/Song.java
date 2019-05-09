package com.imusic.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "song_table")
public class Song implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id = 0;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "duration")
    private int duration = 0;

    @ColumnInfo(name = "song_path")
    private String song_path = null;

    @ColumnInfo(name = "artist")
    private String artist;

    @Ignore
    private boolean isAdded = false;

    @Ignore
    private long playlistSongId;

    @Ignore
    public Song(long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    @Ignore
    public Song(long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Ignore
    public Song(String title) {
        this.title = title;
    }

    public Song() {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSong_path() {
        return song_path;
    }

    public void setSong_path(String song_path) {
        this.song_path = song_path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public long getPlaylistSongId() {
        return playlistSongId;
    }

    public void setPlaylistSongId(long playlistSongId) {
        this.playlistSongId = playlistSongId;
    }
}
