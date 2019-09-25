package com.imusic.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.imusic.models.Recently;

import java.util.List;

@Dao
public abstract class RecentlyDao {
    @Insert
    public abstract long insert(Recently recently);

    @Query("SELECT * FROM recently WHERE id_song LIMIT 20")
    public abstract LiveData<List<Recently>> getIdSong();
//
//    @Query("DELETE FROM recently WHERE id =: idSong")
//    public abstract void deleteSong(long idSong);

    @Query("DELETE FROM recently")
    public abstract void deleteAllSong();
}
