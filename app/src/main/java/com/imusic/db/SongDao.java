package com.imusic.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.imusic.models.Song;

import java.util.List;

@Dao
public abstract class SongDao {

//    @Query("DELETE  FROM song_table WHERE song_id = id")
//    abstract void deleteById(int song_id);

    @Query("DELETE FROM song_table")
    public abstract void deleteAllSong();

    @Query("SELECT * FROM song_table ORDER BY title ASC")
    public abstract LiveData<List<Song>> getAllSong();

    @Query("SELECT * FROM song_table WHERE title LIKE :keyword")
    public abstract List<Song> searchSong(String keyword);
}
