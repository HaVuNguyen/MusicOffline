package com.imusic.fragment.song;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Song;

import java.util.List;

public class SongViewModel extends AndroidViewModel {
    private SongRepository mSongRepository;
    private LiveData<List<Song>> mAllSongs;
    public SongViewModel(@NonNull Application application) {
        super(application);
        mSongRepository = new SongRepository(application);
        mAllSongs = mSongRepository.getAllSong();
    }

    LiveData<List<Song>> getAllSongs(){
        return mAllSongs;
    }

    void deleteALlSong(){
        mSongRepository.deleteAll();
    }
}
