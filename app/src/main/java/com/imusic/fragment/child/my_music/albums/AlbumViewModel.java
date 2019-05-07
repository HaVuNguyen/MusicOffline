package com.imusic.fragment.child.my_music.albums;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Albums;

import java.util.List;

public class AlbumViewModel extends AndroidViewModel {

    private AlbumRepository mRepository;
    private LiveData<List<Albums>> mAllAlbums;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AlbumRepository(application);
        mAllAlbums = mRepository.getAllAlbums();
    }

    LiveData<List<Albums>> getAllAlbums() {
        return mAllAlbums;
    }

    public long insert(Albums albums) {
        return mRepository.insert(albums);
    }

    public int count() {
        return mRepository.count();
    }
}
