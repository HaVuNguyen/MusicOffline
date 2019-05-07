package com.imusic.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.imusic.models.PlaylistSong;

import java.util.List;

@Dao
public abstract class PlaylistSongDao {

    @Insert
    public abstract long insert(PlaylistSong playlistSong);

    @Query("SELECT id_song FROM playlist_song_table WHERE id_playlist=:playlistId")
    public abstract LiveData<List<Long>> getSongByPlaylistId(long playlistId);

    @Query("DELETE FROM playlist_song_table WHERE id_song=:idSong")
    public abstract void deleteSongById(long idSong);
}
