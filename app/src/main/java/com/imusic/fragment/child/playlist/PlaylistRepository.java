package com.imusic.fragment.child.playlist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.imusic.db.PlaylistDao;
import com.imusic.db.SRDatabase;
import com.imusic.models.Playlist;

import java.util.List;

public class PlaylistRepository {

    private LiveData<List<Playlist>> mListLiveDataPlaylist;
    private PlaylistDao mPlaylistDao;

    PlaylistRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mPlaylistDao = database.mPlaylistDao();
        mListLiveDataPlaylist = mPlaylistDao.getAllPlaylist();
    }

    LiveData<List<Playlist>> getAllPlaylist() {
        if (mListLiveDataPlaylist == null) {
            mListLiveDataPlaylist = mPlaylistDao.getAllPlaylist();
        }
        return mListLiveDataPlaylist;
    }

    void deleteAll() {
        new deleteAllPlaylistAsync(mPlaylistDao).execute();
    }

    private static class deleteAllPlaylistAsync extends AsyncTask<Playlist, Void, Void> {

        private PlaylistDao mPlaylistDaoAsync;

        deleteAllPlaylistAsync(PlaylistDao playlistDao) {
            mPlaylistDaoAsync = playlistDao;
        }

        @Override
        protected Void doInBackground(Playlist... playlists) {
            mPlaylistDaoAsync.deleteAll();
            return null;
        }
    }
//
//    void insertPlaylist(Playlist playlist) {
//        new insertPlaylistAsync(mPlaylistDao).execute(playlist);
//    }
//
//    private static class insertPlaylistAsync extends AsyncTask<Playlist, Void, Long> {
//
//        private PlaylistDao mPlaylistDaoAsync;
//
//        insertPlaylistAsync(PlaylistDao playlistDao) {
//            this.mPlaylistDaoAsync = playlistDao;
//        }
//
//        @Override
//        protected Long doInBackground(Playlist... playlists) {
//            Long id = mPlaylistDaoAsync.insert(playlists[0]);
//            return id;
//        }
//    }


    void uppdatePlaylist(Playlist playlist) {
        new updatePlaylistAysnc(mPlaylistDao).execute(playlist);
    }

    private static class updatePlaylistAysnc extends AsyncTask<Playlist, Void, Void> {

        PlaylistDao mPlaylistDao;

        updatePlaylistAysnc(PlaylistDao playlistDao) {
            this.mPlaylistDao = playlistDao;
        }

        @Override
        protected Void doInBackground(Playlist... playlists) {
            mPlaylistDao.update(playlists[0]);
            return null;
        }
    }

    void insert(Playlist playlist) {
        new insertAsyncTask(mPlaylistDao).execute(playlist);
    }

    private static class insertAsyncTask extends AsyncTask<Playlist, Void, Void> {

        private PlaylistDao mPlaylistDaoAsyncTask;

        insertAsyncTask(PlaylistDao dao) {
            this.mPlaylistDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(Playlist... playlists) {
            mPlaylistDaoAsyncTask.insert(playlists[0]);
            return null;
        }
    }
}
