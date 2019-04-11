package com.imusic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.adapter.MyMusicAdapter;

public class MusicFragment extends Fragment {

    private int mPage;
    private String mTitle;
    private TextView mTvTitle;
    private View mImvBack;
    private RecyclerView mListMuisc;
    private MyMusicAdapter mMusicAdapter;

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
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.tv_my_music);
        mImvBack = view.findViewById(R.id.imv_back);
        mImvBack.setVisibility(View.GONE);
        mListMuisc = view.findViewById(R.id.list_music);
        mListMuisc.setLayoutManager(new LinearLayoutManager(getContext()));
        mMusicAdapter = new MyMusicAdapter();
        mListMuisc.setAdapter(mMusicAdapter);
        return view;
    }
}
