package com.imusic.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.imusic.models.Song;

import java.util.List;

@Dao
public abstract class SongDao {
    @Insert
    public abstract long insert(Song song);

    @Query("SELECT COUNT(id) FROM song_table")
    public abstract int count();

    @Query("DELETE FROM song_table")
    public abstract void deleteAllSong();

    @Query("SELECT * FROM song_table ORDER BY title ASC")
    public abstract LiveData<List<Song>> getAllSong();

    @Query("SELECT * FROM song_table WHERE title LIKE :keyword")
    public abstract List<Song> searchSong(String keyword);

    @Query("SELECT id FROM song_table WHERE title =:title")
    public abstract List<Long> getIdSong(String title);

    @Query("SELECT * FROM song_table WHERE id =:songId")
    public abstract List<Song> getSongByID(long songId);

    @Query("SELECT * FROM song_table ORDER BY title ASC")
    public abstract List<Song> getAllSongTest();
}
