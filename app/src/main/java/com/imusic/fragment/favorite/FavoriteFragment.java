package com.imusic.fragment.favorite;

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

public class FavoriteFragment extends Fragment {

    private TextView mTvTitle;
    private ImageView mImvAdd;
    private View mImvBack;

    public FavoriteFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        mTvTitle = view.findViewById(R.id.tv_title);
        mImvAdd = view.findViewById(R.id.imv_add_favorite);
        mImvBack = view.findViewById(R.id.imv_back);
    }

    private void initListener() {
        mTvTitle.setText(R.string.tv_favorite);
        mImvBack.setVisibility(View.GONE);
        mImvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
