package com.imusic.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "artist_song_table")
public class ArtistSong {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id = 0;

    @ColumnInfo(name = "song_id")
    private long idSong;

    @ColumnInfo(name = "artist_id")
    private long idArtist;

    @Ignore
    public ArtistSong() {
    }

    public ArtistSong(long idSong, long idArtist) {
        this.idSong = idSong;
        this.idArtist = idArtist;
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

    public long getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(long idArtist) {
        this.idArtist = idArtist;
    }
}
