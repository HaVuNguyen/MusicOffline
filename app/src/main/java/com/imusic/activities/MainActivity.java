package com.imusic.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.imusic.R;
import com.imusic.fragment.group.GroupFavoriteFragment;
import com.imusic.fragment.group.GroupMyMusicFragment;
import com.imusic.fragment.group.GroupPlaylistFragment;
import com.imusic.fragment.group.GroupRecentlyFragment;
import com.imusic.menu.MenuAdapter;
import com.imusic.menu.MenuModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends BaseActivity {
    private RecyclerView mRcMenu;
    private MenuAdapter mMenuAdapter;
    private List<MenuModel> mMenuLists = new ArrayList<>();

    private SectionsPagerAdapter mSectionsPagerAdapter;

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private GroupMyMusicFragment mGroupMyMusicFragment;
        private GroupRecentlyFragment mGroupRecentlyFragment;
        private GroupPlaylistFragment mGroupPlaylistFragment;
        private GroupFavoriteFragment mGroupFavoriteFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
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
                        replaceFragment(new GroupMyMusicFragment());
                        break;
                    case MENU_RECENTLY:
                        replaceFragment(new GroupRecentlyFragment());
                        break;
                    case MENU_PLAYLIST:
                        replaceFragment(new GroupPlaylistFragment());
                        break;
                    case MENU_FAVORITE:
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
    }

    @Override
    protected void addListener() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
