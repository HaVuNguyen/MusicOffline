package com.imusic.fragment.child.playlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Playlist;

import java.util.List;

public class PlaylistViewModel extends AndroidViewModel {

    private PlaylistRepository mRepository;

    public PlaylistViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PlaylistRepository(application);
    }

    public void insert(Playlist playlist) {
        mRepository.insert(playlist);
    }

    LiveData<List<Playlist>> getAllPlaylist() {
        return mRepository.getAllPlaylist();
    }

    public void delete() {
        mRepository.deleteAll();
    }

    void deletePlaylist(Playlist playlist) {
        mRepository.deleteByPlaylist(playlist);
    }

    public void updatePlaylist(Playlist playlist) {
        mRepository.updatePlaylist(playlist);
    }
}
