package com.imusic.fragment.child.my_music.albums;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Albums;

import java.util.List;

public class AlbumViewModel extends AndroidViewModel {

    private AlbumRepository mAlbumRepository;
    private LiveData<List<Albums>> mAllAlbums;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        mAlbumRepository = new AlbumRepository(application);
        mAllAlbums = mAlbumRepository.getAllAlbums();
    }

    LiveData<List<Albums>> getAllAlbums() {
        return mAllAlbums;
    }

    void deleteAllAlbum() {
        mAlbumRepository.deleteAll();
    }

    void insert(Albums albums) {
        mAlbumRepository.insert(albums);
    }
}
