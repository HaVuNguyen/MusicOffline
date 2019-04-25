package com.imusic.fragment.child.my_music.artists.details;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.imusic.db.SRDatabase;
import com.imusic.db.SongDao;
import com.imusic.models.Song;

import java.util.List;

class ArtistDetailRepository {

    private SongDao mSongDao;
    private LiveData<List<Song>> mListLiveDataSong;

    ArtistDetailRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mSongDao = database.mSongDao();
    }

    LiveData<List<Song>> getListLiveDataArtistSong(int artist_id) {
        mListLiveDataSong = mSongDao.getSongByArtistIdLiveData(artist_id);
        return mListLiveDataSong;
    }
}
