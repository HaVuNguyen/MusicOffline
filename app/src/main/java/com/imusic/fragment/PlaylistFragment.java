package com.imusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imusic.R;

public class PlaylistFragment extends Fragment {

    private int mPage;
    private String mTitle;

    public PlaylistFragment() {
    }

    public static PlaylistFragment newInstance(int page, String title) {
        PlaylistFragment playlistFragment = new PlaylistFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        playlistFragment.setArguments(args);
        return playlistFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("page", 0);
        mTitle = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
        return rootView;
    }
}
