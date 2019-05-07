package com.imusic.fragment.child.my_music.artists.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.ArtistSong;
import com.imusic.models.Song;

import java.util.List;

public class ArtistDetailViewModel extends AndroidViewModel {
    private ArtistDetailRepository mRepository;

    public ArtistDetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ArtistDetailRepository(application);
    }

    LiveData<List<Long>> getSongIdByArtistId(long artistId) {
        return mRepository.getSongIdByArtistId(artistId);
    }

    public long insert(ArtistSong artistSong) {
        return mRepository.insert(artistSong);
    }
}
