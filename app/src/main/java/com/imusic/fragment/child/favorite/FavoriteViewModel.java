package com.imusic.fragment.child.favorite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Favorite;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository mRepository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FavoriteRepository(application);
    }

    public long insert(Favorite favorite) {
        return mRepository.insert(favorite);
    }

    void deleteSongById(long idSong) {
        mRepository.deleteSongById(idSong);
    }

    LiveData<List<Long>> getIdSong() {
        return mRepository.getIdSong();
    }
}
