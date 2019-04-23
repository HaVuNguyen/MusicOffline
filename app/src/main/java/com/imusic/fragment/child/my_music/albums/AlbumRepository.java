package com.imusic.fragment.child.my_music.albums;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.imusic.db.AlbumsDao;
import com.imusic.db.SRDatabase;
import com.imusic.models.Albums;

import java.util.List;

public class AlbumRepository {
    private AlbumsDao mAlbumsDao;
    private LiveData<List<Albums>> mAllAlbums;

    public AlbumRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mAlbumsDao = database.mAlbumsDao();
        mAllAlbums = mAlbumsDao.getAllAlbums();
    }

    LiveData<List<Albums>> getAllAlbums() {
        if (mAllAlbums == null) {
            mAllAlbums = mAlbumsDao.getAllAlbums();
        }
        return mAllAlbums;
    }

    void deleteAll() {
        new deleteAllAlbum(mAlbumsDao).execute();
    }

    private static class deleteAllAlbum extends AsyncTask<Albums, Void, Void> {

        private AlbumsDao mAlbumsDao;

        deleteAllAlbum(AlbumsDao albumsDao) {
            mAlbumsDao = albumsDao;
        }

        @Override
        protected Void doInBackground(Albums... albums) {
            mAlbumsDao.deleteAll();
            return null;
        }
    }

    void insert(Albums albums){
        new insert(mAlbumsDao).execute(albums);
    }

    private static class insert extends AsyncTask<Albums,Void,Void>{
        private AlbumsDao mAlbumsDao;

        insert(AlbumsDao albumsDao){
            mAlbumsDao = albumsDao;
        }

        @Override
        protected Void doInBackground(Albums... albums) {
            mAlbumsDao.insert(albums[0]);
            return null;
        }
    }
}
