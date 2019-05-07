package com.imusic.fragment.child.my_music.song;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Song;

import java.util.List;

public class SongViewModel extends AndroidViewModel {
    private SongRepository mRepository;
    private LiveData<List<Song>> mAllSongs;

    public SongViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SongRepository(application);
        mAllSongs = mRepository.getAllSong();
    }

    public LiveData<List<Song>> getAllSongs() {
        return mAllSongs;
    }

    int count() {
        return mRepository.count();
    }

    long insert(Song song) {
        return mRepository.insert(song);
    }

    List<Long> getIdByTitle(String title) {
        return mRepository.getIdByTitle(title);
    }

    public List<Song> getSongById(long idSong) {
        return mRepository.getSongById(idSong);
    }
}
