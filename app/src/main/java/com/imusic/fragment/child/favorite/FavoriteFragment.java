package com.imusic.fragment.child.favorite;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.imusic.R;
import com.imusic.activities.PLayerActivity;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.favorite.add_song.AddSongToFavoriteFragment;
import com.imusic.fragment.child.my_music.song.SongViewModel;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteFragment extends BaseFragment {

    private RecyclerView mRcListSong;
    private ArrayList<Song> mSongs;
    private FavoriteViewModel mViewModel;
    private SongViewModel mSongViewModel;
    private FavoriteAdapter mAdapter;
    private ArrayList<Long> mListIdSong;

    @Override
    protected int initLayout() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initComponents() {
        mSongs = new ArrayList<>();
        mListIdSong = new ArrayList<>();
        mViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mRcListSong = mView.findViewById(R.id.rc_list_song_to_favorite);
        mRcListSong.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new FavoriteAdapter(mSongs, new FavoriteAdapter.IOnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(Song song) {
                Objects.requireNonNull(getActivity()).startActivity(PLayerActivity.getInstance(getActivity()));
            }

            @Override
            public void onDeleteItem(Song song) {
                mViewModel.deleteSongById(song.getId());
                mAdapter.notifyDataSetChanged();
            }
        });
        mRcListSong.setAdapter(mAdapter);
        mViewModel.getIdSong().observe(this, new Observer<List<Long>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onChanged(@Nullable List<Long> longs) {
                if (longs != null) {
                    mListIdSong = new ArrayList<>(longs);
                    mSongs.clear();
                    new AsyncTask<Long, Void, List<Long>>() {
                        @Override
                        protected List<Long> doInBackground(Long... longs) {
                            for (long id : mListIdSong) {
                                List<Song> listSong = mSongViewModel.getSongById(id);
                                if (listSong.size() > 0) {
                                    mSongs.add(listSong.get(0));
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(List<Long> longs) {
                            super.onPostExecute(longs);
                            mAdapter.notifyDataSetChanged();
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
        initNavigation();
    }

    @Override
    protected void addListener() {
        showNavLeft(R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragment() != null) {
                    ((BaseGroupFragment) getParentFragment()).openMenu();
                }
            }
        });
        showNavRight(R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragment() != null) {
                    ((BaseGroupFragment) getParentFragment()).addFragmentNotReloadContent(new AddSongToFavoriteFragment());
                }
            }
        });
    }
}
