package com.imusic.fragment.playlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imusic.R;

public class PlaylistFragment extends Fragment {

    private TextView mTvTitle;
    private View mImvBack;
    private ImageView mImvAdd;

    public PlaylistFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View view) {
        mTvTitle = view.findViewById(R.id.tv_title);
        mImvAdd = view.findViewById(R.id.imv_add_playlist);
        mImvBack = view.findViewById(R.id.imv_back);
    }

    private void initListener() {
        mTvTitle.setText(R.string.tv_playlist);
        mImvBack.setVisibility(View.GONE);
        mImvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        initView(view);
        initListener();
        return view;
    }
}
