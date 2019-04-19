package com.imusic.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.imusic.models.Artist;

import java.util.List;

@Dao
public abstract class ArtistDao {
    @Query("SELECT * FROM artist_table ORDER BY artist_name asc")
    public abstract LiveData<List<Artist>> getAllArtist();

    @Query("DELETE FROM artist_table")
    public abstract void deleteAll();

    @Query("SELECT COUNT(*) FROM artist_table")
    public abstract int getCount();
}
