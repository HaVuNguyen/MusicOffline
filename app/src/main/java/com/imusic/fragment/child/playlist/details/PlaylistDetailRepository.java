package com.imusic.fragment.child.playlist.details;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.imusic.db.SRDatabase;
import com.imusic.db.dao.PlaylistSongDao;
import com.imusic.models.PlaylistSong;

import java.util.List;

public class PlaylistDetailRepository {

    private PlaylistSongDao mDao;

    PlaylistDetailRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mDao = database.mPlaylistSongDao();
    }

    public long insert(PlaylistSong playlistSong) {
        return mDao.insert(playlistSong);
    }

    LiveData<List<Long>> getIdSongByIdPlaylist(long playlistId) {
        return mDao.getSongByPlaylistId(playlistId);
    }

    void deleteSongById(long idSong) {
        new deleteSongByIdSync(mDao).execute(idSong);
    }

    private static class deleteSongByIdSync extends AsyncTask<Long, Void, Void> {

        private PlaylistSongDao mPlaylistSongDaoSync;

        deleteSongByIdSync(PlaylistSongDao playlistSongDao) {
            mPlaylistSongDaoSync = playlistSongDao;
        }

        @Override
        protected Void doInBackground(Long... longs) {
            mPlaylistSongDaoSync.deleteSongById(longs[0]);
            return null;
        }
    }
}
