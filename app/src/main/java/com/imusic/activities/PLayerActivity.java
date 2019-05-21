package com.imusic.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.SongService;
import com.imusic.callbacks.IMusicCallBack;
import com.imusic.callbacks.IOnInitFragmentCallBack;
import com.imusic.fragment.child.my_music.song.SongFragment;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.fragment.player.PlayerFragment;
import com.imusic.models.Song;
import com.imusic.ultils.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class PLayerActivity extends BaseActivity implements View.OnClickListener, IMusicCallBack {
    private ImageView mImvPlay, mImvNext, mImvPre;
    private Handler mHandler;
    public SongService mService;
    private static final String TIME_FORMAT = "mm:ss";
    private static final String TIME_DEFAULT = "00:00";
    private ArrayList<Song> mSongs;
    private int mPosition;
    private ViewPager mViewPager;

    private TextView mTvDuration, mTvCurrentDuration;
    private SeekBar mSeekBar;

    private SectionPagePlayerAdapter mSectionPagePlayerAdapter;

    @Override
    public void onCurrentSong(Song song, int position) {
        mSectionPagePlayerAdapter.mPlayerFragment.setTvNameSong(song, position);
        mSectionPagePlayerAdapter.mSongFragment.setSongPosition(song,position);
    }

    @Override
    public void onPlayOrPause(boolean isPlay) {
        mSectionPagePlayerAdapter.mSongFragment.setIsPlayOrPause(isPlay);
    }

    public class SectionPagePlayerAdapter extends FragmentPagerAdapter {
        private PlayerFragment mPlayerFragment;
        private SongFragment mSongFragment;

        SectionPagePlayerAdapter(FragmentManager fm, IOnInitFragmentCallBack callBack) {
            super(fm);
            mPlayerFragment = new PlayerFragment();
            mSongFragment = new SongFragment(callBack);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mPlayerFragment;
            }
            return mSongFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Locale locale = Locale.getDefault();
            switch (position) {
                case 0:
                    return PLayerActivity.this.getResources().getString(R.string.tv_playing).toUpperCase(locale);
                case 1:
                    return PLayerActivity.this.getResources().getString(R.string.tv_list_song).toUpperCase(locale);
                default:
                    return null;
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder songBinder = (SongService.SongBinder) service;
            mService = songBinder.getService();
            mService.setSongs(mSongs);
            mService.setPositionSong(mPosition);
            mService.setIMusicCallBacks(PLayerActivity.this);
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
    protected void onStop() {
        super.onStop();
        removeCallbacks();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_player;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BaseGroupFragment baseGroupFragment = (BaseGroupFragment) mSectionPagePlayerAdapter.getItem(mViewPager.getCurrentItem());
        if (!baseGroupFragment.onBackPressed()) {
            super.onBackPressed();
        }

    }

    @Override
    protected void initComponents() {
        mImvPlay = findViewById(R.id.imv_play);
        mImvPre = findViewById(R.id.imv_pre);
        mImvNext = findViewById(R.id.imv_next);
        mHandler = new Handler();
        initNavigation();
        Intent intent = new Intent(this, SongService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        mSongs = (ArrayList<Song>) getIntent().getSerializableExtra(Constant.LIST_SONG);
        mPosition = (int) getIntent().getSerializableExtra(Constant.POSITION_SONG);

        mViewPager = findViewById(R.id.view_pager_player);
        mSectionPagePlayerAdapter = new SectionPagePlayerAdapter(getSupportFragmentManager(), new IOnInitFragmentCallBack() {
            @Override
            public void onInitFragment(boolean isInit) {
                if (isInit) {
                    mSectionPagePlayerAdapter.mSongFragment.setListSongPosition(mSongs,mPosition);
                }
            }
        });
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mSectionPagePlayerAdapter);

        mTvCurrentDuration = findViewById(R.id.tv_current_duration);
        mTvDuration = findViewById(R.id.tv_duration);
        mSeekBar = findViewById(R.id.sb);
    }

    @Override
    protected void addListener() {
        setTitle(getString(R.string.tv_playing));
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
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });
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
                    mService.resume(); //
                }
                break;
            case R.id.imv_pre:
                mService.previousSong();
                controlPlaying();
                break;
            case R.id.imv_next:
                mService.nextSong();
                controlPlaying();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        if (mService != null) {
            mService.removeCallback(PLayerActivity.this);
        }
    }

    private void controlPlaying() {
        if (!mService.isPlaying()) {
            mImvPlay.setImageResource(R.drawable.ic_btn_pause);
        } else {
            mImvPlay.setImageResource(R.drawable.ic_btn_play);
        }
    }

    public void update() {
        mHandler.postDelayed(mTimeCounter, 1000);
    }

    public void removeCallbacks() {
        mHandler.removeCallbacks(mTimeCounter);
    }

    private Runnable mTimeCounter = new Runnable() {
        @Override
        public void run() {
            if (mService != null) {
//                if (!mTvNameSong.equals(mService.getTitleSongPlaying())) {
////                    mTvNameSong.setText(mService.getTitleSongPlaying());
//                    mTvDuration.setText(convertToTime(mService.getDuration()));
//                    mTvCurrentDuration.setText(TIME_DEFAULT);
//                }
                if (mService.isPlaying()) {
                    long currentPercent = 100 * mService.getCurrentDuration() / mService.getDuration();
                    mSeekBar.setProgress((int) currentPercent);
                    mTvDuration.setText(convertToTime(mService.getDuration()));
                    mTvCurrentDuration.setText(convertToTime(mService.getCurrentDuration()));
                }
            }
            update();
        }
    };

    private String convertToTime(long duration) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        return sdf.format(duration);
    }

    private boolean isPlaying() {
        return mService.isPlaying();
    }
}
