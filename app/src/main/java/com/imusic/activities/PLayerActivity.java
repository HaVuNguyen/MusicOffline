package com.imusic.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.SongService;
import com.imusic.models.Song;
import com.imusic.ultils.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class PLayerActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImvPlay, mImvNext, mImvPre, mImvMenu;
    private TextView mTvTitlePlaying, mTvCurrentDuration, mTvDuration, mTvTitle;
    private Handler mHandler;
    private SeekBar mSeekBar;
    public SongService mService;
    private static final String TIME_FORMAT = "mm:ss";
    private static final String TIME_DEFAULT = "00:00";
    private ArrayList<Song> mSongs;
    private int mPosition;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder songBinder = (SongService.SongBinder) service;
            mService = songBinder.getService();
            mService.setSongs(mSongs);
            mService.setPositionSong(mPosition);
            mService.playSong();
            controlPlaying();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeCallbacks();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_player;
    }

    @Override
    protected void initComponents() {
        mImvMenu = findViewById(R.id.imv_right);
        mTvTitle = findViewById(R.id.tv_title);
        mImvPlay = findViewById(R.id.imv_play);
        mImvPre = findViewById(R.id.imv_pre);
        mImvNext = findViewById(R.id.imv_next);
        mTvTitlePlaying = findViewById(R.id.tv_title_playing);
        mTvCurrentDuration = findViewById(R.id.tv_current_duration);
        mTvDuration = findViewById(R.id.tv_duration);
        mSeekBar = findViewById(R.id.sb);
        mHandler = new Handler();
        initNavigation();
        Intent intent = new Intent(this, SongService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        mSongs = (ArrayList<Song>) getIntent().getSerializableExtra(Constant.LIST_SONG);
        mPosition = (int) getIntent().getSerializableExtra(Constant.POSITION_SONG);
    }

    @Override
    protected void addListener() {
        mTvTitle.setText(R.string.tv_playing);
        hiddenNavRight();
        showNavLeft(R.drawable.ic_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mImvPlay.setOnClickListener(this);
        mImvNext.setOnClickListener(this);
        mImvPre.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(mListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_play:
                if (isPlaying()) {
                    mImvPlay.setImageResource(R.drawable.ic_btn_play);
                    mService.pauseSong();
                } else {
                    mImvPlay.setImageResource(R.drawable.ic_btn_pause);
                    mService.playSong();
                }
                break;
            case R.id.imv_pre:
                mService.previousSong();
                break;
            case R.id.imv_next:
                mService.nextSong();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    private void controlPlaying() {
        if (!mService.isPlaying()) {
            mImvPlay.setImageResource(R.drawable.ic_btn_pause);
        } else {
            mImvPlay.setImageResource(R.drawable.ic_btn_play);
        }
    }

    private void update() {
        mHandler.postDelayed(mTimeCounter, 1000);
    }

    private void removeCallbacks() {
        mHandler.removeCallbacks(mTimeCounter);
    }

    private Runnable mTimeCounter = new Runnable() {
        @Override
        public void run() {
            if (mService != null) {
                if (!mTvTitlePlaying.getText().toString().equals(mService.getTitleSongPlaying())) {
                    mTvTitlePlaying.setText(mService.getTitleSongPlaying());
                    mTvDuration.setText(convertToTime(mService.getDuration()));
                    mTvCurrentDuration.setText(TIME_DEFAULT);
                }
                if (mService.isPlaying()) {
                    long currentPercent = 100 * mService.getCurrentDuration() / mService.getDuration();
                    mSeekBar.setProgress((int) currentPercent);
                    mTvCurrentDuration.setText(convertToTime(mService.getCurrentDuration()));
                }
            }
            update();
        }
    };

    private String convertToTime(long duration) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        String time = sdf.format(duration);
        return time;
    }

    private SeekBar.OnSeekBarChangeListener mListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int percent = seekBar.getProgress();
            int currentSeekBarTo = percent * mService.getDuration() / 100;
            mService.seekTo(currentSeekBarTo);
        }
    };

    private boolean isPlaying() {
        return mService.isPlaying();
    }
}
