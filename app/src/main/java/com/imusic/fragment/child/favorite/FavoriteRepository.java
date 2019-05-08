package com.imusic.fragment.child.favorite;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.imusic.db.SRDatabase;
import com.imusic.db.dao.FavoriteDao;
import com.imusic.models.Favorite;

import java.util.List;

class FavoriteRepository {
    private FavoriteDao mFavoriteDao;

    FavoriteRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mFavoriteDao = database.mFavoriteDao();
    }

    long insert(Favorite favorite) {
        return mFavoriteDao.insert(favorite);
    }

    void deleteSongById(long idSong) {
        new deleteSongAsync(mFavoriteDao).execute(idSong);
    }

    private static class deleteSongAsync extends AsyncTask<Long, Void, Void> {

        private FavoriteDao mFavoriteDaoSync;

        deleteSongAsync(FavoriteDao favoriteDao) {
            mFavoriteDaoSync = favoriteDao;
        }

        @Override
        protected Void doInBackground(Long... longs) {
            mFavoriteDaoSync.deleteSong(longs[0]);
            return null;
        }
    }

    LiveData<List<Long>> getIdSong() {
        return mFavoriteDao.getIdSong();
    }
}
