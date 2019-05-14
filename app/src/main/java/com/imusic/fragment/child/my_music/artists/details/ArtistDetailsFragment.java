package com.imusic.fragment.child.my_music.artists.details;

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

import com.imusic.R;
import com.imusic.activities.PLayerActivity;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.my_music.song.SongViewModel;
import com.imusic.fragment.child.playlist.details.PlaylistDetailViewModel;
import com.imusic.models.Artist;
import com.imusic.models.Playlist;
import com.imusic.models.PlaylistSong;
import com.imusic.models.Song;
import com.imusic.ultils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtistDetailsFragment extends BaseFragment {

    private TextView mTvTitleArtist;
    private ImageView mImvBack, mImvSinger;
    private RecyclerView mListSongArtist;
    private Artist mArtist;
    private ArrayList<Song> mListSongs;

    private ArtistDetailAdapter mAdapter;
    private ArtistDetailViewModel mViewModel;
    private SongViewModel mSongViewModel;
    private ArrayList<Long> mListSongByArtistId;
    private PlaylistDetailViewModel mPlaylistDetailViewModel;
    private Playlist mPlaylist;

    public static ArtistDetailsFragment getInstance(Artist artist) {
        ArtistDetailsFragment fragment = new ArtistDetailsFragment();
        fragment.mArtist = artist;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_artist_detail;
    }

    @Override
    protected void initComponents() {
        mListSongs = new ArrayList<>();
        mListSongByArtistId = new ArrayList<>();
        mImvBack = mView.findViewById(R.id.btn_back_artist);
        mImvSinger = mView.findViewById(R.id.imv_singer);
        mTvTitleArtist = mView.findViewById(R.id.tv_name_artists);
        mListSongArtist = mView.findViewById(R.id.rc_artist_detail);
        mListSongArtist.setLayoutManager(new LinearLayoutManager(mContext));

        mPlaylistDetailViewModel = ViewModelProviders.of(this).get(PlaylistDetailViewModel.class);
        final boolean isAdd = getActivity().getIntent().getBooleanExtra(Constant.TYPE_ADD_SONG, false);
        mPlaylist = (Playlist) getActivity().getIntent().getSerializableExtra(Constant.TYPE_PLAYLIST);

        mAdapter = new ArtistDetailAdapter(mListSongs, isAdd, new ArtistDetailAdapter.IOnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(Song song,int position) {
                Toast.makeText(mContext, "Play music", Toast.LENGTH_SHORT).show();
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
                            mPlaylistDetailViewModel.insert(playlistSong);
                            return null;
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
        mListSongArtist.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this).get(ArtistDetailViewModel.class);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mViewModel.getSongIdByArtistId(mArtist.getId()).observe(this, new Observer<List<Long>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onChanged(@Nullable List<Long> longs) {
                if (longs != null) {
                    mListSongByArtistId = new ArrayList<>(longs);
                    mListSongs.clear();
                    new AsyncTask<Long, Void, List<Song>>() {
                        @Override
                        protected List<Song> doInBackground(Long... longs) {
                            for (Long id : mListSongByArtistId) {
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
        mTvTitleArtist.setText(mArtist.getName());
        mImvSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mArtist.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mImvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }
}
