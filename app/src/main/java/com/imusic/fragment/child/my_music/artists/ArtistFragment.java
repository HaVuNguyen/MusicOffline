package com.imusic.fragment.child.my_music.artists;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.my_music.artists.details.ArtistDetailsFragment;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.models.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistFragment extends BaseFragment {

    private ArrayList<Artist> mArtists;
    private ArtistAdapter mArtistAdapter;
    private TextView mTvNoData;

    public ArtistFragment() {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_artist;
    }

    @Override
    protected void initComponents() {
        mArtists = new ArrayList<>();
        mTvNoData = mView.findViewById(R.id.tv_no_artist);
        mArtistAdapter = new ArtistAdapter(mArtists);
        RecyclerView rcArtists = mView.findViewById(R.id.list_artist);
        rcArtists.setLayoutManager(new LinearLayoutManager(mContext));
        rcArtists.setAdapter(mArtistAdapter);
        ArtistViewModel artistViewModel = ViewModelProviders.of(this).get(ArtistViewModel.class);
        artistViewModel.getAllArtist().observe(this, new Observer<List<Artist>>() {
            @Override
            public void onChanged(@Nullable List<Artist> artists) {
                if (artists != null) {
                    mArtists = new ArrayList<>(artists);
                    mArtistAdapter.setArtists(mArtists);

                    if (mArtists.size() == 0) {
                        mTvNoData.setVisibility(View.VISIBLE);
                    } else {
                        mTvNoData.setVisibility(View.GONE);
                    }
                }
            }
        });

        mArtistAdapter.setIOnClickArtist(new ArtistAdapter.IOnClickArtist() {
            @Override
            public void onItemClick(Artist artist, int position) {
                if (getParentFragment() != null) {
                    if (getParentFragment().getParentFragment() != null) {
                        ((BaseGroupFragment) getParentFragment().getParentFragment()).addFragmentNotReloadContent(ArtistDetailsFragment.getInstance(artist));
                    }
                }
            }
        });
    }

    @Override
    protected void addListener() {

    }
}
