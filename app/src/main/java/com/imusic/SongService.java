package com.imusic;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.imusic.models.Song;

import java.util.ArrayList;

public class SongService extends Service {

    MediaPlayer mPlayer;
    private ArrayList<Song> mSongs;
    private int songPosn;
    private String songTitle;
    private MediaPlayer mediaPlayer;

    public SongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSongs = new ArrayList<>();
        songPosn = 0;
        Song song = mSongs.get(songPosn);
        mPlayer = MediaPlayer.create(getApplicationContext(), song.getId());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }
}
