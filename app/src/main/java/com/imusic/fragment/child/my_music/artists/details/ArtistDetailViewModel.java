package com.imusic.fragment.child.my_music.artists.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Song;

import java.util.List;

public class ArtistDetailViewModel extends AndroidViewModel {
    private ArtistDetailRepository mRepository;

    public ArtistDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ArtistDetailRepository(application);
    }

    LiveData<List<Song>> getListLiveDataSongArtist(int artistId) {
        return mRepository.getListLiveDataArtistSong(artistId);
    }
}
