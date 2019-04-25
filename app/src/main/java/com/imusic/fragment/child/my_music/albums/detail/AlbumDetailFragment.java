package com.imusic.fragment.child.my_music.albums.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imusic.R;
import com.imusic.activities.PLayerActivity;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.models.Albums;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlbumDetailFragment extends BaseFragment {

    private ImageView mImvBack;

    private AlbumDetailAdapter mAdapter;
    private Albums mAlbums;
    private TextView mTvTitleAlbums;
    private ImageView mImvAlbums;
    private RecyclerView mRcAlbumDetail;
    private AlbumDetailViewModel mViewModel;
    private ArrayList<Song> mSongs;

    public static AlbumDetailFragment getInstance(Albums albums) {
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        fragment.mAlbums = albums;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_album_detail;
    }

    @Override
    protected void initComponents() {
        mSongs = new ArrayList<>();
        getSongByAlbum();
        mTvTitleAlbums = mView.findViewById(R.id.tv_name_album);
        mImvAlbums = mView.findViewById(R.id.imv_albums_art);
        mImvBack = mView.findViewById(R.id.btn_back_album);

        mTvTitleAlbums.setText(mAlbums.getName());

        Glide.with(mContext)
                .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.bg_album_default))
                .load(mAlbums.getAlbumArt())
                .into(mImvAlbums);

        mAdapter = new AlbumDetailAdapter(mSongs, new AlbumDetailAdapter.IOnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(Song song) {
                Toast.makeText(mContext, "Play music", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).startActivity(PLayerActivity.getInstance(getActivity()));
            }
        });
        mRcAlbumDetail = mView.findViewById(R.id.list_album_detail);
        mRcAlbumDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcAlbumDetail.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(AlbumDetailViewModel.class);
        mViewModel.getSongs(mAlbums.getId()).observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(@Nullable List<Song> songs) {
                if (songs != null) {
                    mSongs = new ArrayList<>(songs);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getSongByAlbum() {
        Cursor mediaCursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (mediaCursor != null && mediaCursor.moveToFirst()) {
            int titleColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = mediaCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int albumId = mediaCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

            do {
                int thisId = mediaCursor.getInt(idColumn);
                int thisalbumId = mediaCursor.getInt(albumId);
                String thisTitle = mediaCursor.getString(titleColumn);
                if (mAlbums.getId() == thisalbumId) {
                    mSongs.add(new Song(thisId, thisTitle, thisalbumId));
                }
            }
            while (mediaCursor.moveToNext());

        }
    }

    @Override
    protected void addListener() {
        mImvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
        mImvAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mAlbums.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
