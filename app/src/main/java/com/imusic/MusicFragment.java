package com.imusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MusicFragment extends Fragment {

    private int mPage;
    private String mTitle;

    public MusicFragment() {
    }

    public static MusicFragment newInstance(int page, String title) {
        MusicFragment musicFragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        musicFragment.setArguments(args);
        return musicFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);
        return rootView;
    }
}
