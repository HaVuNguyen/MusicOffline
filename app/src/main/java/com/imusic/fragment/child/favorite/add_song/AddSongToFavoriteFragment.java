package com.imusic.fragment.child.favorite.add_song;

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
import com.imusic.fragment.child.favorite.FavoriteViewModel;
import com.imusic.fragment.child.my_music.song.SongViewModel;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.models.Favorite;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class AddSongToFavoriteFragment extends BaseFragment {
    private TextView mTvTitle;
    private ImageView mImvBack;
    private RecyclerView mRcListSong;
    private AddSongToFavoriteAdapter mAdapter;
    private ArrayList<Song> mSongs;
    private SongViewModel mViewModel;
    private FavoriteViewModel mFavoriteViewModel;

    @Override
    protected int initLayout() {
        return R.layout.fragment_add_song;
    }

    @Override
    protected void initComponents() {
        mSongs = new ArrayList<>();
        mImvBack = mView.findViewById(R.id.btn_back_detail);
        mTvTitle = mView.findViewById(R.id.tv_add_song_to_playlist);
        mRcListSong = mView.findViewById(R.id.list_song);
        mViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mFavoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mRcListSong.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AddSongToFavoriteAdapter(mSongs, new AddSongToFavoriteAdapter.IOnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onAddSong(Song song) {
                final long idSong = song.getId();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Favorite favorite = new Favorite();
                        favorite.setIdSong(idSong);
                        mFavoriteViewModel.insert(favorite);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        mAdapter.notifyDataSetChanged();
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
                    mAdapter.setSongToFavorite(mSongs);
                }
            }
        });
    }

    @Override
    protected void addListener() {
        mTvTitle.setText(getString(R.string.tv_add_song_to_favorite));
        mImvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragment() != null) {
                    ((BaseGroupFragment) getParentFragment()).onBackPressed();
                }
            }
        });
    }
}
