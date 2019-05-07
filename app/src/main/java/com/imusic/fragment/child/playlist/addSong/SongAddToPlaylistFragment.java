package com.imusic.fragment.child.playlist.addSong;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.my_music.song.SongViewModel;
import com.imusic.fragment.child.playlist.details.PlaylistDetailViewModel;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.models.Playlist;
import com.imusic.models.PlaylistSong;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongAddToPlaylistFragment extends BaseFragment {

    private ImageView mImvBack;
    private TextView mTvTitle;
    private RecyclerView mRcListSong;
    private SongAddToPlaylistAdapter mAdapter;
    private ArrayList<Song> mSongs;
    private SongViewModel mViewModel;
    private PlaylistDetailViewModel mDetailViewModel;
    private Playlist mPlaylist;

    public static SongAddToPlaylistFragment getInstance(Playlist playlist) {
        SongAddToPlaylistFragment fragment = new SongAddToPlaylistFragment();
        fragment.mPlaylist = playlist;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_add_song;
    }

    @Override
    protected void initComponents() {
        mSongs = new ArrayList<>();
        mImvBack = mView.findViewById(R.id.btn_back_detail);
        mTvTitle = mView.findViewById(R.id.tv_add_song_to_playlist);
        mViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mDetailViewModel = ViewModelProviders.of(this).get(PlaylistDetailViewModel.class);
        mRcListSong = mView.findViewById(R.id.list_song);
        mRcListSong.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SongAddToPlaylistAdapter(mSongs, new SongAddToPlaylistAdapter.IOnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onAddSong(Song song) {
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
        });
        mRcListSong.setAdapter(mAdapter);

        mViewModel.getAllSongs().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(@Nullable List<Song> songs) {
                if (songs != null) {
                    mSongs = new ArrayList<>(songs);
                    mAdapter.setSongToPlaylist(mSongs);
                }
            }
        });

    }

    @Override
    protected void addListener() {
        mImvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragment() != null) {
                    ((BaseGroupFragment) getParentFragment()).onBackPressed();
                }
            }
        });
        mTvTitle.setText(getString(R.string.tv_add_song_to_playlist));
    }
}
