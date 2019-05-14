package com.imusic.fragment.child.playlist.details;

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
import com.imusic.fragment.child.playlist.PlaylistViewModel;
import com.imusic.fragment.child.playlist.addSong.AddSongToPlaylistActivity;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.models.Playlist;
import com.imusic.models.PlaylistSong;
import com.imusic.models.Song;
import com.imusic.ultils.Constant;
import com.imusic.view.AddEditPlaylistDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaylistDetailFragment extends BaseFragment {

    private Playlist mPlaylist;
    private ImageView mImvBack, mImvAddSong;
    private TextView mTvNamePlaylist;
    private TextView mTvNoData;
    private ArrayList<Song> mListSong;
    private RecyclerView mRcSong;
    private PlaylistDetailAdapter mAdapter;
    private PlaylistDetailViewModel mViewModel;
    private ArrayList<PlaylistSong> mListIdSong;
    private SongViewModel mSongViewModel;
    private PlaylistViewModel mPlaylistViewModel;

    public static PlaylistDetailFragment getInstance(Playlist playlist) {
        PlaylistDetailFragment fragment = new PlaylistDetailFragment();
        fragment.mPlaylist = playlist;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_playlist_detail;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initComponents() {
        mImvBack = mView.findViewById(R.id.btn_back_playlist);
        mImvAddSong = mView.findViewById(R.id.btn_add_song_to_playlist);
        mTvNamePlaylist = mView.findViewById(R.id.tv_name_playlist);
        mTvNamePlaylist.setText(mPlaylist.getTitle());
        mTvNoData = mView.findViewById(R.id.tv_no_song_to_playlist);
        mListSong = new ArrayList<>();
        mListIdSong = new ArrayList<>();
        mRcSong = mView.findViewById(R.id.list_song_to_playlist);
        mRcSong.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PlaylistDetailAdapter(mListSong, new PlaylistDetailAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(Song song,int position) {
                Intent intent = new Intent(mContext,PLayerActivity.class);
                intent.putExtra(Constant.LIST_SONG,mListSong);
                intent.putExtra(Constant.POSITION_SONG,position);
                mContext.startActivity(intent);
            }

            @Override
            public void onDeleteItem(Song song) {
                /*phải xóa theo id trong bảng trung gian vì id này chỉ xuất hiện 1 lần*/
                mViewModel.deleteSongById(song.getPlaylistSongId());
                mAdapter.notifyDataSetChanged();
            }
        });
        mRcSong.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this).get(PlaylistDetailViewModel.class);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mPlaylistViewModel = ViewModelProviders.of(this).get(PlaylistViewModel.class);
        mViewModel.getIdSongByIdPlaylist(mPlaylist.getId()).observe(this, new Observer<List<PlaylistSong>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onChanged(@Nullable List<PlaylistSong> longs) {
                if (longs != null) {
                    mListIdSong = new ArrayList<>(longs);
                    mListSong.clear();
                    new AsyncTask<PlaylistSong, Void, List<PlaylistSong>>() {
                        @Override
                        protected List<PlaylistSong> doInBackground(PlaylistSong... longs) {
                            for (PlaylistSong iteam : mListIdSong) {
                                List<Song> listSongById = mSongViewModel.getSongById(iteam.getSongId());
                                if (listSongById.size() > 0) {
                                    /*có id song -> lưu vào table song -> id song playlist*/
                                    Song idSongPlaylist = listSongById.get(0);
                                    idSongPlaylist.setPlaylistSongId(iteam.getId());
                                    mListSong.add(idSongPlaylist);
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(List<PlaylistSong> playlistSongs) {
                            super.onPostExecute(playlistSongs);
                            mAdapter.notifyDataSetChanged();
                            if (mListSong.size() == 0) {
                                mTvNoData.setVisibility(View.VISIBLE);
                            } else {
                                mTvNoData.setVisibility(View.GONE);
                            }
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
                if (getParentFragment() != null) {
                    ((BaseGroupFragment) getParentFragment()).onBackPressed();
                }
            }
        });

        mImvAddSong.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddSongToPlaylistActivity.class);
                intent.putExtra(Constant.TYPE_ADD_SONG, true);
                intent.putExtra(Constant.TYPE_PLAYLIST, mPlaylist);
                mContext.startActivity(intent);
            }
        });

        mTvNamePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlaylist.getFromUsers() == 1) {
                    AddEditPlaylistDialog dialog = new AddEditPlaylistDialog(mContext, mPlaylist, new AddEditPlaylistDialog.IOnSubmitListener() {
                        @Override
                        public void submit(String name) {
                            mPlaylist.setTitle(name);
                            mPlaylistViewModel.updatePlaylist(mPlaylist);
                            mTvNamePlaylist.setText(mPlaylist.getTitle());
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}
