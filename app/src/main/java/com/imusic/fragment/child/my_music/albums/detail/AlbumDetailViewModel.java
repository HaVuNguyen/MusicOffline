package com.imusic.fragment.child.my_music.albums.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.AlbumSong;

import java.util.List;

public class AlbumDetailViewModel extends AndroidViewModel {

    private AlbumDetailRepository mRepository;

    public AlbumDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AlbumDetailRepository(application);
    }

    public long insert(AlbumSong albumSong) {
        return mRepository.insert(albumSong);
    }

    LiveData<List<Long>> getSongByAlbumId(long albumId) {
        return mRepository.getSongByAlbumId(albumId);
    }
}
