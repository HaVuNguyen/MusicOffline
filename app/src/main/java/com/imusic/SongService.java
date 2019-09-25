package com.imusic;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.imusic.callbacks.IMusicCallBack;
import com.imusic.callbacks.IMusicPlayerCallback;
import com.imusic.models.Song;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SongService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    MediaPlayer mPlayer;
    private ArrayList<Song> mSongs;
    private int mPosition;
    private final IBinder songBind = new SongBinder();
    ArrayList<IMusicCallBack> mIMusicCallBacks;
    ArrayList<IMusicPlayerCallback> mIMusicPlayerCallbacks;

    @Override
    public void onCreate() {
        super.onCreate();
        mPosition = 0;
        mPlayer = new MediaPlayer();
        initMediaPlayer();
        mIMusicCallBacks = new ArrayList<>();
        mIMusicPlayerCallbacks = new ArrayList<>();
    }

    public void setIMusicCallBacks(IMusicCallBack callBack) {
        mIMusicCallBacks.add(callBack); // lưu call back truyền vào từ tất cả activity
    }

    public void setIMusicPlayerCallbacks(IMusicPlayerCallback callback) {
        mIMusicPlayerCallbacks.add(callback);
    }

    public void removeCallBack(IMusicPlayerCallback callback) {
        mIMusicPlayerCallbacks.remove(callback);
    }

    public void removeCallback(IMusicCallBack callBack) {
        mIMusicCallBacks.remove(callBack);
    }

    @SuppressLint("PrivateApi")
    private void initMediaPlayer() {
        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
            try {
                Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
                Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
                Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
                Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");

                Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

                Object subtitleInstance = constructor.newInstance(this, null, null);

                Field f = cSubtitleController.getDeclaredField("mHandler");

                f.setAccessible(true);
                try {
                    f.set(subtitleInstance, new Handler());
                } catch (IllegalAccessException ignored) {
                } finally {
                    f.setAccessible(false);
                }

                Method setsubtitleanchor = mPlayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

                setsubtitleanchor.invoke(mPlayer, subtitleInstance, null);
                //Log.e("", "subtitle is setted :p");
            } catch (Exception e) {
            }
        }

        //set listener
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnCompletionListener(this);
    }

    public void setSongs(ArrayList<Song> songs) {
        this.mSongs = songs;
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
        for (IMusicCallBack item : mIMusicCallBacks) {
            item.onCurrentSong(mSongs.get(mPosition), mPosition);
        }
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
        for (IMusicCallBack item : mIMusicCallBacks) {
            item.onCurrentSong(mSongs.get(mPosition), mPosition);
        }
        for (IMusicPlayerCallback item : mIMusicPlayerCallbacks) {
            item.onSongPlayer(mSongs, mPosition, mSongs.get(mPosition));
        }
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
        for (IMusicCallBack item : mIMusicCallBacks) {
            item.onPlayOrPause(false);
        }
        for (IMusicPlayerCallback item : mIMusicPlayerCallbacks) {
            item.onPLay(false);
        }
    }

    public void resume() {
        mPlayer.start();
    }

    public void previousSong() {
        mPosition--;
        if (mPosition < 0) {
            mPosition = mSongs.size() - 1;
        }
        playSong();
    }


    public void seekTo(int index) {
        mPlayer.seekTo(index);
    }

    public void nextSong() {
        mPosition++;
        if (mPosition >= mSongs.size()) {
            mPosition = 0;
        }
        playSong();
    }
//
//    public Song getSongPlaying() {
//        return mSongs.get(mPosition);
//    }
//
//    public String getTitleSongPlaying() {
//        return mSongs.get(mPosition).getTitle();
//    }

    public long getCurrentDuration() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }
}
