package com.imusic.fragment.child.playlist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Playlist;

import java.util.List;

public class PlaylistViewModel extends AndroidViewModel {

    private PlaylistRepository mPlaylistRepository;

    public PlaylistViewModel(@NonNull Application application) {
        super(application);
        mPlaylistRepository = new PlaylistRepository(application);
    }

    public void insert(Playlist playlist) {
        mPlaylistRepository.insert(playlist);
    }

    public LiveData<List<Playlist>> getAllPlaylist() {
        return mPlaylistRepository.getAllPlaylist();
    }

    public void delete() {
        mPlaylistRepository.deleteAll();
    }
}
