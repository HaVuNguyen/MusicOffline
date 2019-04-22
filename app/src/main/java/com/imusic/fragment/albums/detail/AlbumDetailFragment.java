package com.imusic.fragment.albums.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imusic.R;
import com.imusic.fragment.BaseFragment;
import com.imusic.models.Albums;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailFragment extends BaseFragment {

    private Albums mAlbums;
    private TextView mTvTitleAlbums;
    private ImageView mImvAlbums;
    private RecyclerView mRcAlbumDetail;

    private AlbumDetailAdapter mAdapter;
    private AlbumDetailViewModel mViewModel;

    @Override
    protected int initLayout() {
        return R.layout.fragment_album_detail;
    }

    @Override
    protected void initComponents() {
        mAlbums = new Albums();
        mTvTitleAlbums = mView.findViewById(R.id.tv_name_album);
        mImvAlbums = mView.findViewById(R.id.imv_albums_art);

        mTvTitleAlbums.setText(mAlbums.getName());
        Glide.with(mContext)
                .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.bg_album_default))
                .load(mAlbums.getAlbumArt())
                .into(mImvAlbums);

        mAdapter = new AlbumDetailAdapter(new ArrayList<Song>(), new AlbumDetailAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
//                mAdapter.notifyDataSetChanged();
            }
        });
        mRcAlbumDetail = mView.findViewById(R.id.list_album_detail);
        mRcAlbumDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcAlbumDetail.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(AlbumDetailViewModel.class);
        mViewModel.getListLiveDataSong(mAlbums.getId()).observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(@Nullable List<Song> songs) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void addListener() {

    }
}
