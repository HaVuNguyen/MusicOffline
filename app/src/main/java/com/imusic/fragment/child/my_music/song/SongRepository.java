package com.imusic.fragment.child.my_music.song;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.imusic.db.SRDatabase;
import com.imusic.db.SongDao;
import com.imusic.models.Song;

import java.util.List;

class SongRepository {
    private SongDao mSongDao;
    private LiveData<List<Song>> mAllSongs;

    SongRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mSongDao = database.mSongDao();
        mAllSongs = mSongDao.getAllSong();
    }

    LiveData<List<Song>> getAllSong() {
        if (mAllSongs == null) {
            mAllSongs = mSongDao.getAllSong();
        }
        return mAllSongs;
    }

    void deleteAll() {
        new deleteAllSong(mSongDao).execute();
    }

    private static class deleteAllSong extends AsyncTask<Void, Void, Void> {
        private SongDao mSongDao;

        deleteAllSong(SongDao dao) {
            mSongDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mSongDao.deleteAllSong();
            return null;
        }
    }

    void insert(Song song) {
        mSongDao.insert(song);
    }

    int count(){
        return mSongDao.count();
    }

}
