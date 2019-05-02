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

    @ColumnInfo(name = "album_id")
    private long album_id = 0;

    @ColumnInfo(name = "album_name")
    private String album_name;

    @ColumnInfo(name = "artist_id")
    private long artist_id = 0;

    @ColumnInfo(name = "artist_name")
    private String artist_name;


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

    @Ignore
    public Song(long id, String title, long album_id) {
        this.id = id;
        this.title = title;
        this.album_id = album_id;
    }

    @Ignore
    public Song(String title, String artist_name, long artist_id) {
        this.title = title;
        this.artist_name = artist_name;
        this.artist_id = artist_id;
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

    public long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(long album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public long getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(long artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }
}
