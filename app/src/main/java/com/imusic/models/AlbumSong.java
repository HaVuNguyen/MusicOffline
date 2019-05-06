package com.imusic.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "album_song_table")
public class AlbumSong {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private long id = 0;

    @ColumnInfo(name = "album_id")
    private long albumId;

    @ColumnInfo(name = "song_id")
    private long songId;

    @Ignore
    public AlbumSong() {
    }

    public AlbumSong(long albumId, long songId) {
        this.albumId = albumId;
        this.songId = songId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }
}
