package com.imusic.fragment.child.my_music.artists;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.imusic.db.ArtistDao;
import com.imusic.db.SRDatabase;
import com.imusic.models.Artist;

import java.util.List;

class ArtistRepository {
    private ArtistDao mArtistDao;
    private LiveData<List<Artist>> mAllArtist;

    ArtistRepository(Application application) {
        SRDatabase database = SRDatabase.getDatabase(application);
        mArtistDao = database.mArtistDao();
        mAllArtist = mArtistDao.getAllArtist();
    }

    LiveData<List<Artist>> getAllArtist() {
        if (mAllArtist == null) {
            mAllArtist = mArtistDao.getAllArtist();
        }
        return mAllArtist;
    }

    void deleteAll() {
        new deleteAllArtist(mArtistDao).execute();
    }

    private static class deleteAllArtist extends AsyncTask<Artist, Void, Void> {

        private ArtistDao mArtistDao;

        deleteAllArtist(ArtistDao artistDao) {
            mArtistDao = artistDao;
        }

        @Override
        protected Void doInBackground(Artist... artists) {
            mArtistDao.deleteAll();
            return null;
        }
    }

    public void insert(Artist artist){
        mArtistDao.insert(artist);
    }

    int count(){
        return mArtistDao.getCount();
    }
}
