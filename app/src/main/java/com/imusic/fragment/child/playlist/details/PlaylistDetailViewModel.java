package com.imusic.fragment.child.playlist.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.PlaylistSong;

import java.util.List;

public class PlaylistDetailViewModel extends AndroidViewModel {
    private PlaylistDetailRepository mRepository;

    public PlaylistDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PlaylistDetailRepository(application);
    }

    public long insert(PlaylistSong playlistSong) {
        return mRepository.insert(playlistSong);
    }

    LiveData<List<Long>> getIdSongByIdPlaylist(long playlistId) {
        return mRepository.getIdSongByIdPlaylist(playlistId);
    }

    void deleteSongById(long idSong) {
        mRepository.deleteSongById(idSong);
    }
}
