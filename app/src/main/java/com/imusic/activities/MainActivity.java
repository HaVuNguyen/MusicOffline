package com.imusic.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.fragment.PlaylistFragment;
import com.imusic.fragment.albums.AlbumsFragment;
import com.imusic.fragment.artists.ArtistFragment;
import com.imusic.fragment.song.SongFragment;

import java.util.Objects;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private View mTabHome, mTabAlbum, mTabArtist, mCurrentTab;
    private int mTypePage;
    private Toolbar mToolbar;
    private Fragment mCurrentFragment = new Fragment();
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private View mViewTab;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        mTypePage = getIntent().getIntExtra("PAGE", -1);
//        if (mTypePage == 2) {
//            mTabAlbum.performClick();
//        } else if (mTypePage == 3) {
//            mTabArtist.performClick();
//        } else {
//            mTabHome.performClick();
//        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents() {
        mNavigationView = findViewById(R.id.nav_view);
        mViewTab = findViewById(R.id.view_tab);
        mToolbar = findViewById(R.id.tool_bar);
        mDrawer = findViewById(R.id.drawer_layout);

        mTabHome = findViewById(R.id.tab_my_music);
        mTabAlbum = findViewById(R.id.tab_albums);
        mTabArtist = findViewById(R.id.tab_artist);

        mCurrentTab = mTabHome;
        mCurrentTab.setSelected(true);

        mCurrentFragment = new SongFragment();
        setNewPage(mCurrentFragment);
    }

    @Override
    protected void addListener() {
        mTabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setTitle(getString(R.string.tv_my_music));
                mToolbar.setTitleTextColor(Color.WHITE);
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabHome;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new SongFragment();
                setNewPage(mCurrentFragment);
            }
        });

        mTabAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setTitle(getString(R.string.tv_albums));
                mToolbar.setTitleTextColor(Color.WHITE);
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabAlbum;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new AlbumsFragment();
                setNewPage(mCurrentFragment);
            }
        });

        mTabArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setTitle(getString(R.string.tv_artist));
                mToolbar.setTitleTextColor(Color.WHITE);
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabArtist;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new ArtistFragment();
                setNewPage(mCurrentFragment);
            }
        });
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar.setTitle(getString(R.string.tv_my_music));
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                mToolbar.setTitle(getString(R.string.tv_my_music));
                mToolbar.setTitleTextColor(Color.WHITE);
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabHome;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new SongFragment();
                setNewPage(mCurrentFragment);
                mViewTab.setVisibility(View.VISIBLE);
                break;

            case R.id.recently:
                Toast.makeText(this, getString(R.string.tv_coming), Toast.LENGTH_SHORT).show();
                mViewTab.setVisibility(View.GONE);
                break;
            case R.id.playlist:
                Toast.makeText(this, getString(R.string.tv_coming), Toast.LENGTH_SHORT).show();
                mToolbar.setTitle(getString(R.string.tv_playlist));
                mToolbar.setTitleTextColor(Color.WHITE);
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentFragment = new PlaylistFragment();
                setNewPage(mCurrentFragment);
                mViewTab.setVisibility(View.GONE);
                break;
            case R.id.favorite:
                Toast.makeText(this, getString(R.string.tv_coming), Toast.LENGTH_SHORT).show();
                mViewTab.setVisibility(View.GONE);
                break;
            case R.id.about:
                Toast.makeText(this, getString(R.string.tv_coming), Toast.LENGTH_SHORT).show();
                mViewTab.setVisibility(View.GONE);
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
