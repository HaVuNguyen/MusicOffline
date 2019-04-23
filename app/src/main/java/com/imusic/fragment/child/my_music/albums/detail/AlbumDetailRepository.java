package com.imusic.fragment.child.my_music.albums.detail;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.imusic.db.SRDatabase;
import com.imusic.db.SongDao;
import com.imusic.models.Song;

import java.util.List;

public class AlbumDetailRepository {

    private SongDao mSongDao;
    private LiveData<List<Song>> mListLiveDataSong;

    public AlbumDetailRepository(Application application){
        SRDatabase database = SRDatabase.getDatabase(application);
        mSongDao = database.mSongDao();
    }

    public LiveData<List<Song>> getSong(int albumId){
        if (mListLiveDataSong == null){
            mListLiveDataSong = mSongDao.getSongByAlbumIdLiveData(albumId);
        }
        return mListLiveDataSong;
    }
}
