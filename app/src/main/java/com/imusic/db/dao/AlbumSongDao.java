package com.imusic.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.imusic.models.AlbumSong;

import java.util.List;

@Dao
public abstract class AlbumSongDao {
    @Insert
    public abstract long insert(AlbumSong albumSong);

    @Query("SELECT * FROM album_song_table")
    public abstract List<AlbumSong> getSongTest();

    @Query("SELECT song_id FROM album_song_table WHERE album_id =:albumId")
    public abstract LiveData<List<Long>> getSongByAlbumId(long albumId);
}
