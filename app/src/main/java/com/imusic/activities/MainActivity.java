package com.imusic.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;

import com.imusic.R;
import com.imusic.fragment.albums.AlbumsFragment;
import com.imusic.fragment.artists.ArtistFragment;
import com.imusic.fragment.song.SongFragment;

import java.util.Objects;

public class MainActivity extends BaseActivity {
    private View mTabHome, mTabAlbum, mTabArtist, mCurrentTab;
    private Fragment mCurrentFragment = new Fragment();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents() {
        mTabHome = findViewById(R.id.tab_my_music);
        mTabAlbum = findViewById(R.id.tab_albums);
        mTabArtist = findViewById(R.id.tab_artist);

        setTitle(getString(R.string.tv_my_music));
        mCurrentTab = mTabHome;
        mCurrentTab.setSelected(true);

        mCurrentFragment = new SongFragment();
        setNewPage(mCurrentFragment);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main, mCurrentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        initRightMenu();
    }

    @Override
    protected void addListener() {
//        hiddenNavLeft();
//        showNavRight(R.drawable.ic_menu, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleMenuRight();
//            }
//        });

        hiddenNavRight();
        showNavLeft(R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenuRight();
            }
        });
        mTabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle(getString(R.string.tv_my_music));
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabHome;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new SongFragment();
                setNewPage(mCurrentFragment);
                replaceFragment(mCurrentFragment);
            }
        });
        mTabAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle(getString(R.string.tv_albums));
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabAlbum;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new AlbumsFragment();
                setNewPage(mCurrentFragment);
                replaceFragment(mCurrentFragment);
            }
        });
        mTabArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle(getString(R.string.tv_artist));
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabArtist;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new ArtistFragment();
                setNewPage(mCurrentFragment);
                replaceFragment(mCurrentFragment);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
