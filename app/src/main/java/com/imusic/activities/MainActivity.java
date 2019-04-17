package com.imusic.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.fragment.favorite.FavoriteFragment;
import com.imusic.fragment.playlist.PlaylistFragment;
import com.imusic.fragment.song.SongFragment;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    private View mTabHome, mTabPlaylist, mTabFavorite, mCurentTab;
    private int mTypePage;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        this.mViewPager.setCurrentItem(tab.getPosition());
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SongFragment mSongFragment;
        private PlaylistFragment mPlaylistFragment;
        private FavoriteFragment mFavoriteFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mSongFragment = new SongFragment();
            mPlaylistFragment = new PlaylistFragment();
            mFavoriteFragment = new FavoriteFragment();
        }

        public Fragment getItem(int position) {
            if (position == 0) {
                return mSongFragment;
            }
            if (position == 1) {
                return mPlaylistFragment;
            }
            return mFavoriteFragment;
        }

        public int getCount() {
            return 3;
        }

        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return MainActivity.this.getString(R.string.tv_my_music).toUpperCase(l);
                case 1:
                    return MainActivity.this.getString(R.string.tv_playlist).toUpperCase(l);
                case 2:
                    return MainActivity.this.getString(R.string.tv_favorite).toUpperCase(l);
                default:
                    return null;
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        mTypePage = getIntent().getIntExtra("PAGE", -1);
        if (mTypePage == 2) {
            mTabPlaylist.performClick();
        } else if (mTypePage == 3) {
            mTabFavorite.performClick();
        } else {
            mTabHome.performClick();
        }
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
        mNavigationView = findViewById(R.id.nav_view);
        mTabHome = findViewById(R.id.tab_my_music);
        mTabPlaylist = findViewById(R.id.tab_playlist);
        mTabFavorite = findViewById(R.id.tab_favorite);
        mCurentTab = mTabHome;
        mCurentTab.setSelected(true);
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        this.mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void initListener() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mTabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurentTab != null) {
                    mCurentTab.setSelected(false);
                }
                mCurentTab = mTabHome;
                mCurentTab.setSelected(true);
                mViewPager.setCurrentItem(0);
            }
        });

        mTabPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurentTab != null) {
                    mCurentTab.setSelected(false);
                }
                mCurentTab = mTabPlaylist;
                mCurentTab.setSelected(true);
                mViewPager.setCurrentItem(1);
            }
        });

        mTabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurentTab != null) {
                    mCurentTab.setSelected(false);
                }
                mCurentTab = mTabFavorite;
                mCurentTab.setSelected(true);
                mViewPager.setCurrentItem(2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem());
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.setting:
                Toast.makeText(this, "Setting in Coming", Toast.LENGTH_SHORT).show();
                break;
            case R.id.instruction:
                Toast.makeText(this, "Instruction in Coming", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Toast.makeText(this, "About in Coming", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mSectionsPagerAdapter.notifyDataSetChanged();
    }
}
