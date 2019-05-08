package com.imusic.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.imusic.models.Favorite;

import java.util.List;

@Dao
public abstract class FavoriteDao {
    @Insert
    public abstract long insert(Favorite favorite);

    @Query("DELETE FROM favorite_table WHERE id_song=:idSong")
    public abstract void deleteSong(long idSong);

    @Query("SELECT id_song FROM favorite_table")
    public abstract LiveData<List<Long>> getIdSong();
}
