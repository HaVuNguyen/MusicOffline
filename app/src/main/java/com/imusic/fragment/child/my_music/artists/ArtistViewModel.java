package com.imusic.fragment.child.my_music.artists;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Artist;

import java.util.List;

public class ArtistViewModel extends AndroidViewModel {

    private ArtistRepository mRepository;
    private LiveData<List<Artist>> mAllArtist;

    public ArtistViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ArtistRepository(application);
        mAllArtist = mRepository.getAllArtist();
    }

    LiveData<List<Artist>> getAllArtist() {
        return mAllArtist;
    }

    void deleteAllArtist() {
        mRepository.deleteAll();
    }

    void insert(Artist artist) {
        mRepository.insert(artist);
    }

    int count() {
        return mRepository.count();
    }
}
