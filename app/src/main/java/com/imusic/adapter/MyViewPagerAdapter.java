package com.imusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.imusic.fragment.FavoriteFragment;
import com.imusic.fragment.PlaylistFragment;
import com.imusic.fragment.song.SongFragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGER = 3;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SongFragment.newInstance(0, "SONG");

            case 1:
                return PlaylistFragment.newInstance(1, "PLAYLIST");

            case 2:
                return FavoriteFragment.newInstance(2, "FAVORITE");

            default:
                return SongFragment.newInstance(0, "SONG");
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGER;
    }

}
