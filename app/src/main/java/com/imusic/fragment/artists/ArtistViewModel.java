package com.imusic.fragment.artists;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Artist;

import java.util.List;

public class ArtistViewModel extends AndroidViewModel {

    private ArtistRepository mArtistRepository;
    private LiveData<List<Artist>> mAllArtist;

    public ArtistViewModel(@NonNull Application application) {
        super(application);
        mArtistRepository = new ArtistRepository(application);
        mAllArtist = mArtistRepository.getAllArtist();
    }

    LiveData<List<Artist>> getAllArtist() {
        return mAllArtist;
    }

    void deleteAllArtist() {
        mArtistRepository.deleteAll();
    }
}
