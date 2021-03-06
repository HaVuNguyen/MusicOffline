package com.imusic.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imusic.R;
import com.imusic.SongService;
import com.imusic.callbacks.IMusicCallBack;
import com.imusic.callbacks.IMusicPlayerCallback;
import com.imusic.fragment.group.GroupFavoriteFragment;
import com.imusic.fragment.group.GroupMyMusicFragment;
import com.imusic.fragment.group.GroupPlaylistFragment;
import com.imusic.fragment.group.GroupRecentlyFragment;
import com.imusic.menu.MenuAdapter;
import com.imusic.menu.MenuModel;
import com.imusic.models.Song;
import com.imusic.ultils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends BaseActivity implements IMusicPlayerCallback {
    private RecyclerView mRcMenu;
    private MenuAdapter mMenuAdapter;
    private List<MenuModel> mMenuLists = new ArrayList<>();
    private static final int REQUEST_PERMISSIONS = 100;

    public SongService mService;
    private Intent playIntent;
    private ArrayList<Song> mSongs = new ArrayList<>();
    private TextView mTvNameSong;
    private TextView mTvNameSing;
    private ImageView mImvNext, mImvPlay, mImvPre, mImvSong;
    private ConstraintLayout mLayoutMiniPlayer;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder binder = (SongService.SongBinder) service;
            mService = binder.getService();
//                mService.setSongs(mSongs);
//                mService.setPositionSong(mPosition);
//            mService.playSong();
//            if (mSong != null) {
//                updateMiniPlayer(mSong);
//            }
            mService.setIMusicPlayerCallbacks(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    public void onSongPlayer(ArrayList<Song> songs, int position, Song song) {
        updateMiniPlayer(song,songs,position);
    }

    @Override
    public void onPLay(boolean isPlay) {
        if (isPlay){
            mLayoutMiniPlayer.setVisibility(View.VISIBLE);
        }else {
            mLayoutMiniPlayer.setVisibility(View.GONE);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private GroupMyMusicFragment mGroupMyMusicFragment;
        private GroupRecentlyFragment mGroupRecentlyFragment;
        private GroupPlaylistFragment mGroupPlaylistFragment;
        private GroupFavoriteFragment mGroupFavoriteFragment;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mGroupMyMusicFragment = new GroupMyMusicFragment();
            mGroupPlaylistFragment = new GroupPlaylistFragment();
            mGroupFavoriteFragment = new GroupFavoriteFragment();
            mGroupRecentlyFragment = new GroupRecentlyFragment();
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return mGroupMyMusicFragment;
            }
            if (position == 1) {
                return mGroupRecentlyFragment;
            }
            if (position == 2) {
                return mGroupPlaylistFragment;
            }
            return mGroupFavoriteFragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        public CharSequence getPageTitle(int position) {
            Locale locale = Locale.getDefault();
            switch (position) {
                case 0:
                    return MainActivity.this.getString(R.string.tv_my_music);
                case 1:
                    return MainActivity.this.getString(R.string.tv_recently_songs);
                case 2:
                    return MainActivity.this.getString(R.string.tv_playlist);
                case 3:
                    return MainActivity.this.getString(R.string.tv_favorite);
                default:
                    return null;
            }
        }
    }

    private void loadMenu() {
        mRcMenu = findViewById(R.id.recyclerview_menu);
        mRcMenu.setLayoutManager(new LinearLayoutManager(this));
        mMenuLists.clear();
        mMenuLists.add(new MenuModel(R.drawable.ic_music, getString(R.string.tv_my_music), MenuModel.MENU_TYPE.MENU_MY_MUSIC));
        mMenuLists.add(new MenuModel(R.drawable.ic_recently, getString(R.string.tv_recently_songs), MenuModel.MENU_TYPE.MENU_RECENTLY));
        mMenuLists.add(new MenuModel(R.drawable.ic_my_playlist, getString(R.string.tv_playlist), MenuModel.MENU_TYPE.MENU_PLAYLIST));
        mMenuLists.add(new MenuModel(R.drawable.ic_my_favorite, getString(R.string.tv_favorite), MenuModel.MENU_TYPE.MENU_FAVORITE));

        mMenuAdapter = new MenuAdapter(this, mMenuLists);
        mRcMenu.setAdapter(mMenuAdapter);
        mMenuAdapter.setIOnClickItemListener(new MenuAdapter.IOnClickItemListener() {
            @Override
            public void onItemClick(MenuModel.MENU_TYPE menuType) {
                switch (menuType) {
                    case MENU_MY_MUSIC:
                        setTitle(getString(R.string.tv_my_music));
                        replaceFragment(new GroupMyMusicFragment());
                        break;
                    case MENU_RECENTLY:
                        setTitle(getString(R.string.tv_recently_songs));
                        replaceFragment(new GroupRecentlyFragment());
                        break;
                    case MENU_PLAYLIST:
                        setTitle(getString(R.string.tv_playlist));
                        replaceFragment(new GroupPlaylistFragment());
                        break;
                    case MENU_FAVORITE:
                        setTitle(getString(R.string.tv_favorite));
                        replaceFragment(new GroupFavoriteFragment());
                        break;
                }
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawer(mLayoutSlideMenu);
                }
            }
        });
    }

    public void openMenu() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(mLayoutSlideMenu)) {
                mDrawerLayout.closeDrawer(mLayoutSlideMenu);
            } else {
                mDrawerLayout.openDrawer(mLayoutSlideMenu);
            }
        }
    }

    public void showNavLeft(int resId, View.OnClickListener listener) {
        showNavigation(mImvNavLeft, resId, listener);
    }

    public void showNavRight(int resId, View.OnClickListener listener) {
        showNavigation(mImvNavRight, resId, listener);
    }

    public void hiddenNavRight() {
        mImvNavRight.setVisibility(View.GONE);
    }

    public void hiddenNavLeft() {
        mImvNavLeft.setVisibility(View.GONE);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            replaceFragment(new GroupMyMusicFragment());
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents() {
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        loadMenu();
        replaceFragment(new GroupMyMusicFragment());
        mTvNameSong = findViewById(R.id.tv_name_song_playing_main);
        mTvNameSing = findViewById(R.id.tv_name_singer_playing_main);
        mImvSong = findViewById(R.id.imv_song_playing_main);
        mImvNext = findViewById(R.id.btn_next_playing_main);
        mImvPlay = findViewById(R.id.btn_play_playing_main);
        mImvPre = findViewById(R.id.btn_pre_playing_main);
        mLayoutMiniPlayer = findViewById(R.id.view_player);
        mLayoutMiniPlayer.setVisibility(View.GONE);
        mService = new SongService();

//        mSongs = (ArrayList<Song>) getIntent().getSerializableExtra(Constant.LIST_SONG);
//        mPosition = (int) getIntent().getSerializableExtra(Constant.POSITION_SONG);
    }

    private void updateMiniPlayer(final Song song, final ArrayList<Song> songs, final int position) {
        if (song != null) {
            mLayoutMiniPlayer.setVisibility(View.VISIBLE);
            Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.wave_gif)).load(R.drawable.wave_gif).into(mImvSong);
            mTvNameSong.setText(song.getTitle());
            mTvNameSing.setText(song.getArtist());

            mLayoutMiniPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PLayerActivity.class);
                    intent.putExtra(Constant.LIST_SONG,songs);
                    intent.putExtra(Constant.POSITION_SONG,position);
                    startActivity(intent);
                }
            });
        }else {
            mLayoutMiniPlayer.setVisibility(View.GONE);
        }
    }


    @Override
    protected void addListener() {
        mImvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying()) {
                    mImvPlay.setImageResource(R.drawable.ic_btn_play);
                    mService.pauseSong();
                } else {
                    mImvPlay.setImageResource(R.drawable.ic_btn_pause);
                    mService.playSong();
                }
            }
        });

        mImvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.nextSong();
            }
        });

        mImvPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.previousSong();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, SongService.class);
            bindService(playIntent, mConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        mService.removeCallBack(MainActivity.this);
        mService = null;
        super.onDestroy();
    }

    boolean isPlaying() {
        return mService.isPlaying();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
