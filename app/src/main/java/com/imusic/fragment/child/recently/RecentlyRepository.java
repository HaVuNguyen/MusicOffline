package com.imusic.fragment.child.recently;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.imusic.db.SRDatabase;
import com.imusic.db.dao.RecentlyDao;
import com.imusic.models.Recently;

import java.util.List;

class RecentlyRepository {
    private RecentlyDao mRecentlyDao;

    RecentlyRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mRecentlyDao = database.mRecentlyDao();
    }

    long insert(Recently recently) {
        return mRecentlyDao.insert(recently);
    }
//
//    void deleteSongById(long idSong) {
//        new deleteSongAsync(mRecentlyDao).execute(idSong);
//    }
//
//    private static class deleteSongAsync extends AsyncTask<Long, Void, Void> {
//
//        private RecentlyDao mRecentlyDaoSync;
//
//        deleteSongAsync(RecentlyDao favoriteDao) {
//            mRecentlyDaoSync = favoriteDao;
//        }
//
//        @Override
//        protected Void doInBackground(Long... longs) {
//            mRecentlyDaoSync.deleteAllSong(longs[0]);
//            return null;
//        }
//    }

    LiveData<List<Recently>> getIdSong() {
        return mRecentlyDao.getIdSong();
    }
}
