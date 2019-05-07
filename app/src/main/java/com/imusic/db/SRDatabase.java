package com.imusic.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.imusic.db.dao.AlbumSongDao;
import com.imusic.db.dao.AlbumsDao;
import com.imusic.db.dao.ArtistDao;
import com.imusic.db.dao.ArtistSongDao;
import com.imusic.db.dao.PlaylistDao;
import com.imusic.db.dao.SongDao;
import com.imusic.models.AlbumSong;
import com.imusic.models.Albums;
import com.imusic.models.Artist;
import com.imusic.models.ArtistSong;
import com.imusic.models.Playlist;
import com.imusic.models.Song;

@Database(entities = {Song.class, Albums.class, Artist.class, Playlist.class, AlbumSong.class, ArtistSong.class}, version = 1, exportSchema = false)
public abstract class SRDatabase extends RoomDatabase {
    public abstract SongDao mSongDao();

    public abstract AlbumsDao mAlbumsDao();

    public abstract ArtistDao mArtistDao();

    public abstract PlaylistDao mPlaylistDao();

    public abstract AlbumSongDao mAlbumSongDao();

    public abstract ArtistSongDao mArtistSongDao();

    private static volatile SRDatabase INSTANCE;


    public static SRDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SRDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SRDatabase.class, "music_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static SRDatabase.Callback sCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SongDao mSongDao;

        PopulateDbAsync(SRDatabase db) {
            mSongDao = db.mSongDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mSongDao.deleteAllSong();
            return null;
        }
    }
}
