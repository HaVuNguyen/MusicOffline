package com.imusic.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.imusic.models.Albums;

import java.util.List;

@Dao
public abstract class AlbumsDao {
    @Insert
    public abstract Long insert(Albums albums);

    @Query("SELECT COUNT(id) FROM album_table")
    public abstract int count();

    @Query("DELETE FROM album_table")
    public abstract void deleteAll();

    @Query("SELECT * FROM album_table ORDER BY album_name")
    public abstract LiveData<List<Albums>> getAllAlbums();

//    @Query("SELECT COUNT(*) FROM album_table")
//    public abstract int getCount();
//
//    @Query("SELECT * FROM album_table WHERE id=:albumId")
//    public abstract void getAlbumId(int albumId);
}
