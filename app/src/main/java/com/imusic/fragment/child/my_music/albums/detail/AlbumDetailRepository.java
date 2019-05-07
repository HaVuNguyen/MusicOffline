package com.imusic.fragment.child.my_music.albums.detail;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.imusic.db.SRDatabase;
import com.imusic.db.dao.AlbumSongDao;
import com.imusic.models.AlbumSong;

import java.util.List;

class AlbumDetailRepository {

    private AlbumSongDao mAlbumSongDao;

    AlbumDetailRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mAlbumSongDao = database.mAlbumSongDao();
    }

    long insert(AlbumSong albumSong) {
        return mAlbumSongDao.insert(albumSong);
    }

    LiveData<List<Long>> getSongByAlbumId(long albumId) {
        return mAlbumSongDao.getSongByAlbumId(albumId);
    }
}
