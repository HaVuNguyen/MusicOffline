package com.imusic.fragment.child.my_music.artists.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.models.Artist;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ArtistDetailsFragment extends BaseFragment {

    private TextView mTvTitleArtist;
    private ImageView mImvBack, mImvArtist;
    private RecyclerView mListSongArtist;
    private Artist mArtist;

    private ArtistDetailAdapter mAdapter;
    private ArtistDetailViewModel mViewModel;

    @Override
    protected int initLayout() {
        return R.layout.fragment_artist_detail;
    }

    @Override
    protected void initComponents() {
        mImvBack = mView.findViewById(R.id.btn_back_artist);
        mImvArtist = mView.findViewById(R.id.imv_artist_art);
        mTvTitleArtist = mView.findViewById(R.id.tv_name_artists);

        mListSongArtist = mView.findViewById(R.id.rc_artist_detail);
        mListSongArtist.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ArtistDetailAdapter(new ArrayList<Song>(), new ArtistDetailAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
                Toast.makeText(mContext, "Playmusic", Toast.LENGTH_SHORT).show();
            }
        });
        mListSongArtist.setAdapter(mAdapter);

        mArtist = new Artist();
        mViewModel = ViewModelProviders.of(this).get(ArtistDetailViewModel.class);
        mViewModel.getListLiveDataSongArtist(mArtist.getId()).observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(@Nullable List<Song> songs) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void addListener() {
        mTvTitleArtist.setText(getString(R.string.tv_artist));
        mImvArtist.setImageResource(R.drawable.ic_artist_default);
        mImvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackToPreFragment();
            }
        });
    }
}
