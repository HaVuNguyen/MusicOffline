package com.imusic;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.imusic.models.Song;

import java.io.IOException;
import java.util.ArrayList;

public class SongService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    MediaPlayer mPlayer;
    private ArrayList<Song> mSongs;
    private int mPosition;
    private final IBinder songBind = new SongBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        mPosition = 0;
        mPlayer = new MediaPlayer();
        initMediaPlayer();
    }


    private void initMediaPlayer() {
        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //set listener
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnCompletionListener(this);
    }

    public void setSongs(ArrayList<Song> songs) {
        this.mSongs = songs;
    }

    public ArrayList<Song> getSongs() {
        return mSongs;
    }

    public class SongBinder extends Binder {
        public SongService getService() {
            return SongService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return songBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mPlayer.stop();
        mPlayer.release();
        return false;
    }

    public void playSong() {
        mPlayer.reset();
        try {
            mPlayer.setDataSource(mSongs.get(mPosition).getSongPath());
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mPlayer.start();
    }

    public void setPositionSong(int songIndex) {
        mPosition = songIndex;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (mPlayer.getCurrentPosition() > 0) {
            mediaPlayer.reset();
            nextSong();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        mediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public int getPosition() {
        return mPosition;
    }

    public int getDuration() {
        return mPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void pauseSong() {
        mPlayer.pause();
    }

    public void previousSong() {
        mPosition--;
        if (mPosition < 0) {
            mPosition = mSongs.size() - 1;
        }
        playSong();
    }

    public void nextSong() {
        mPosition++;
        if (mPosition >= mSongs.size()) {
            mPosition = 0;
        }
        playSong();
    }

    public void seekTo(int index){
        mPlayer.seekTo(index);
    }

    public String getTitleSongPlaying() {
        return mSongs.get(mPosition).getTitle();
    }

    public long getCurrentDuration() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

//    public int getPosn() {
//        return mPlayer.getCurrentPosition();
//    }
}

