package com.imusic.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.imusic.models.ArtistSong;

import java.util.List;

@Dao
public abstract class ArtistSongDao {
    @Insert
    public abstract long insert(ArtistSong artistSong);

    @Query("SELECT song_id FROM artist_song_table WHERE artist_id=:artistId")
    public abstract LiveData<List<Long>> getSongIdByArtistId(long artistId);
}
