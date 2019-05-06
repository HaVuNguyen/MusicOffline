package com.imusic.fragment.child.my_music.albums.detail;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.imusic.db.SRDatabase;
import com.imusic.db.dao.AlbumSongDao;
import com.imusic.models.AlbumSong;

import java.util.List;

class AlbumDetailRepository {

    //    private SongDao mSongDao;
//    private LiveData<List<Song>> mSongs;
//    private LiveData<List<Long>> mListIdSong;

    private AlbumSongDao mAlbumSongDao;

    AlbumDetailRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
//        mSongDao = database.mSongDao();
        mAlbumSongDao = database.mAlbumSongDao();
    }

    long insert(AlbumSong albumSong) {
        return mAlbumSongDao.insert(albumSong);
    }

    LiveData<List<Long>> getSongByAlbumId(long albumId) {
        return mAlbumSongDao.getSongByAlbumId(albumId);
    }

    List<AlbumSong> getSongTest() {
        return mAlbumSongDao.getSongTest();
    }

//    LiveData<List<Song>> getSong(long albumId) {
//        if (mSongs == null) {
//            mSongs = mSongDao.getSongByAlbumIdLiveData(albumId);
//        }
//        return mSongs;
//    }
//    LiveData<List<AlbumSong>> getSongByAlbum(long albumId) {
//        if (mListSongByAlbum == null) {
//            mListSongByAlbum = mAlbumSongDao.getSongByAlbum(albumId);
//        }
//        return mListSongByAlbum;
//    }
//    List<Long> getSongByAlbumId(long albumId) {
//        return mAlbumSongDao.getSongByAlbumId(albumId);
//    }
//
//    List<Long> getSongByAlbumId(long albumId) throws ExecutionException, InterruptedException {
//        return new getSongByAlbumIdSync(mAlbumSongDao).execute(albumId).get();
//    }
//
//    private static class getSongByAlbumIdSync extends AsyncTask<Long, Void, List<Long>> {
//
//        private AlbumSongDao mDaoSync;
//
//        getSongByAlbumIdSync(AlbumSongDao albumSongDao) {
//            mDaoSync = albumSongDao;
//        }
//
//        @Override
//        protected List<Long> doInBackground(Long... longs) {
//            return mDaoSync.getSongByAlbumId(longs[0]);
//        }
//    }
}
