package com.imusic.fragment.child.my_music.albums;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.imusic.db.SRDatabase;
import com.imusic.db.dao.AlbumsDao;
import com.imusic.models.Albums;

import java.util.List;

class AlbumRepository {
    private AlbumsDao mAlbumsDao;
    private LiveData<List<Albums>> mAllAlbums;

    AlbumRepository(Application application) {
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

    long insert(Albums albums) {
        return mAlbumsDao.insert(albums);
    }

    int count() {
        return mAlbumsDao.count();
    }
}
