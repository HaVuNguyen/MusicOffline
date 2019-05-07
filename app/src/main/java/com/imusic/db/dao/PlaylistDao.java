package com.imusic.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.imusic.models.Playlist;

import java.util.List;

@Dao
public abstract class PlaylistDao {
    @Insert
    public abstract long insert(Playlist playlist);

    @Query("DELETE FROM playlist_table")
    public abstract void deleteAll();

    @Update
    public abstract void update(Playlist playlist);

    @Query("DELETE FROM playlist_table WHERE title=:playlistId")
    public abstract void delete(long playlistId);

    @Query("SELECT * FROM playlist_table ORDER BY title ASC")
    public abstract LiveData<List<Playlist>> getAllPlaylist();

    @Delete
    public abstract void deleteByPlaylist(Playlist playlist);
}
