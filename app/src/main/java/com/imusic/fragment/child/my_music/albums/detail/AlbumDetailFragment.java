package com.imusic.fragment.child.my_music.albums.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.imusic.fragment.child.my_music.song.SongViewModel;
import com.imusic.fragment.child.playlist.details.PlaylistDetailViewModel;
import com.imusic.models.Albums;
import com.imusic.models.Playlist;
import com.imusic.models.PlaylistSong;
import com.imusic.models.Song;
import com.imusic.ultils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlbumDetailFragment extends BaseFragment {

    private ImageView mImvBack;

    private AlbumDetailAdapter mAdapter;
    private Albums mAlbums;
    private TextView mTvTitleAlbums;
    private ImageView mImvAlbums;
    private AlbumDetailViewModel mViewModel;
    private ArrayList<Song> mListSongs;
    private ArrayList<Long> mListSongByAlbumId;
    private SongViewModel mSongViewModel;
    private Playlist mPlaylist;
    private PlaylistDetailViewModel mDetailViewModel;

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
        mListSongs = new ArrayList<>();
        mListSongByAlbumId = new ArrayList<>();
        mTvTitleAlbums = mView.findViewById(R.id.tv_name_album);
        mImvAlbums = mView.findViewById(R.id.imv_albums_art);
        mImvBack = mView.findViewById(R.id.btn_back_album);
        mTvTitleAlbums.setText(mAlbums.getName());
        Glide.with(mContext)
                .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.bg_album_default))
                .load(mAlbums.getAlbumArt())
                .into(mImvAlbums);

        final boolean isAdd = getActivity().getIntent().getBooleanExtra(Constant.TYPE_ADD_SONG, false);
        mPlaylist = (Playlist) getActivity().getIntent().getSerializableExtra(Constant.TYPE_PLAYLIST);

        mDetailViewModel = ViewModelProviders.of(this).get(PlaylistDetailViewModel.class);
        mAdapter = new AlbumDetailAdapter(mListSongs, isAdd, new AlbumDetailAdapter.IOnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(Song song,int position) {
                Intent intent = new Intent(mContext,PLayerActivity.class);
                intent.putExtra(Constant.LIST_SONG,mListSongs);
                intent.putExtra(Constant.POSITION_SONG,position);
                mContext.startActivity(intent);
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onAddItem(Song song) {
                if (isAdd) {
                    final long idSong = song.getId();
                    final long idPlaylist = mPlaylist.getId();
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            PlaylistSong playlistSong = new PlaylistSong();
                            playlistSong.setSongId(idSong);
                            playlistSong.setPlaylistId(idPlaylist);
                            mDetailViewModel.insert(playlistSong);
                            return null;
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
        RecyclerView rcAlbumDetail = mView.findViewById(R.id.list_album_detail);
        rcAlbumDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        rcAlbumDetail.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(AlbumDetailViewModel.class);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);

        mViewModel.getSongByAlbumId(mAlbums.getId()).observe(this, new Observer<List<Long>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onChanged(@Nullable List<Long> longs) {
                if (longs != null) {
                    mListSongByAlbumId = new ArrayList<>(longs);
                    mListSongs.clear();
                    new AsyncTask<Long, Void, List<Song>>() {
                        @Override
                        protected List<Song> doInBackground(Long... longs) {
                            for (long id : mListSongByAlbumId) {
                                List<Song> listSongById = mSongViewModel.getSongById(id);
                                if (listSongById.size() > 0) {
                                    mListSongs.add(listSongById.get(0));
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(List<Song> songs) {
                            super.onPostExecute(songs);
                            mAdapter.notifyDataSetChanged();
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });

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

            }
        });
    }
}
