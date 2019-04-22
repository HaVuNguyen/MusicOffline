package com.imusic.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.imusic.models.Playlist;

import java.util.List;

@Dao
public abstract class PlaylistDao {
    @Insert
    public abstract void insert(Playlist playlist);

    @Query("DELETE FROM playlist_table")
    public abstract void deleteAll();

    @Update
    public abstract void update(Playlist playlist);

    @Query("DELETE FROM playlist_table WHERE id=:playlistId")
    public abstract void delete(long playlistId);

//    @Query("SELECT * FROM playlist_table WHERE id=:playlistId")
//    public abstract void getById(long playlistId);
//
//    @Query("SELECT * FROM playlist_table WHERE id=:playlistId")
//    public abstract LiveData<List<Playlist>> getByIdLiveData(int playlistId);

    @Query("SELECT * FROM playlist_table ORDER BY title ASC")
    public abstract LiveData<List<Playlist>> getAllPlaylist();
}
