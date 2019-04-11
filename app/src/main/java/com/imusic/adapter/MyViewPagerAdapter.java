package com.imusic.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.imusic.fragment.FavoriteFragment;
import com.imusic.fragment.MusicFragment;
import com.imusic.fragment.PlaylistFragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGER = 3;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MusicFragment.newInstance(0, "MUSIC");

            case 1:
                return PlaylistFragment.newInstance(1, "PLAYLIST");

            case 2:
                return FavoriteFragment.newInstance(2, "FAVORITE");

            default:
                return MusicFragment.newInstance(0, "MUSIC");
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGER;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "MUSIC";
            case 1:
                return "PLAYLIST";
            case 2:
                return "FAVORITE";
            default:
                return "MUSIC";
        }
    }
}
