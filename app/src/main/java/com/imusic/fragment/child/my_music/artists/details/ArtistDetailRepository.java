package com.imusic.fragment.child.my_music.artists.details;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.imusic.db.SRDatabase;
import com.imusic.db.dao.ArtistSongDao;
import com.imusic.db.dao.SongDao;
import com.imusic.models.ArtistSong;
import com.imusic.models.Song;

import java.util.List;

class ArtistDetailRepository {

    private SongDao mSongDao;
    private LiveData<List<Song>> mListLiveDataSong;
    private ArtistSongDao mArtistSongDao;
    private LiveData<List<Long>> mListSongArtist;

    ArtistDetailRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mSongDao = database.mSongDao();
        mArtistSongDao = database.mArtistSongDao();
    }

    LiveData<List<Long>> getSongIdByArtistId(long artistId) {
        mListSongArtist = mArtistSongDao.getSongIdByArtistId(artistId);
        return mListSongArtist;
    }

    public long insert(ArtistSong artistSong) {
        return mArtistSongDao.insert(artistSong);
    }
}
