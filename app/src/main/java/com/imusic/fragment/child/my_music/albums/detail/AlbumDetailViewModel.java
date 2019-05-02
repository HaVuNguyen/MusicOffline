package com.imusic.fragment.child.my_music.albums.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Song;

import java.util.List;

public class AlbumDetailViewModel extends AndroidViewModel {

    private AlbumDetailRepository mAlbumDetailRepository;

    public AlbumDetailViewModel(@NonNull Application application) {
        super(application);
        mAlbumDetailRepository = new AlbumDetailRepository(application);
    }

    LiveData<List<Song>> getSongs(long albumId) {
        return mAlbumDetailRepository.getSong(albumId);
    }
}
