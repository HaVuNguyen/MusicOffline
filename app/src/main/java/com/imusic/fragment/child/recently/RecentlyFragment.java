package com.imusic.fragment.child.recently;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.imusic.R;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.favorite.FavoriteViewModel;
import com.imusic.fragment.child.my_music.song.SongViewModel;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.models.Recently;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class RecentlyFragment extends BaseFragment {

    private RecyclerView mRcRecently;
    private ArrayList<Recently> mRecentlies;
    private RecentlyViewModel mRecentlyViewModel;
    private ArrayList<Song> mSongs;
    private SongViewModel mSongViewModel;
    private RecentlyAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_recently;
    }

    @Override
    protected void initComponents() {
        initNavigation();
        mSongs = new ArrayList<>();
        mRecentlies = new ArrayList<>();

        mRecentlyViewModel = ViewModelProviders.of(this).get(RecentlyViewModel.class);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mRcRecently = mView.findViewById(R.id.list_recently);
        mRcRecently.setLayoutManager(new LinearLayoutManager(mContext));

        mRecentlyViewModel.getIdSong().observe(this, new Observer<List<Recently>>() {
            @Override
            public void onChanged(@Nullable final List<Recently> recentlies) {
                if (recentlies != null) {
                    mRecentlies = new ArrayList<>(recentlies);
                    mSongs.clear();
                    new AsyncTask<Long, Void, List<Long>>() {
                        @Override
                        protected List<Long> doInBackground(Long... longs) {
                            for (Recently item : mRecentlies) {
                                List<Song> songList = mSongViewModel.getSongById(item.getIdSong());
                                if (songList.size() > 0) {
                                    Song idSong = songList.get(0);
                                    idSong.setFavoriteSongId(item.getId());
                                    mSongs.add(idSong);
                                }
                            }
                            return null;
                        }
                    };
                }
            }
        });

        mAdapter = new RecentlyAdapter(mContext, mSongs, new RecentlyAdapter.IOnClickItemListener() {
            @Override
            public void onItemClick(Song song, int position) {

            }

            @Override
            public void onDeleteItem(Song song, int position) {

            }
        });

        mRcRecently.setAdapter(mAdapter);
    }

    @Override
    protected void addListener() {
        showNavLeft(R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseGroupFragment) getParentFragment()).openMenu();
            }
        });
        hiddenNavRight();
        hiddenTitleNavRight();
        setTitle(getString(R.string.tv_recently_songs));
    }
}
