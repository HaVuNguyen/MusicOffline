package com.imusic.fragment.player;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.favorite.FavoriteViewModel;
import com.imusic.models.Favorite;
import com.imusic.models.Song;

import java.util.ArrayList;

public class PlayerFragment extends BaseFragment {

    //    private ArrayList<Song> mSongs;
    private int mPosition = -1;
    public TextView mTvNameSong;
    private ImageView mImvFavorite;
    private FavoriteViewModel mFavoriteViewModel;
    private Song mSong;

    @Override
    protected int initLayout() {
        return R.layout.fragment_player;
    }

    public void setTvNameSong(Song song, int position) {
        mSong = song;
        mPosition = position;
        if (mSong != null && mTvNameSong != null) {
            mTvNameSong.setText(mSong.getTitle());
        }
    }

    @Override
    protected void initComponents() {
        mTvNameSong = mView.findViewById(R.id.tv_title_playing);
        if (mSong != null) {
            mTvNameSong.setText(mSong.getTitle());
        }
        mImvFavorite = mView.findViewById(R.id.imv_favorite);
        mImvFavorite.setSelected(false);
        mFavoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
    }

    @Override
    protected void addListener() {
        mImvFavorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                mImvFavorite.setSelected(true);
                final long idSong = mSong.getId();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Favorite favorite = new Favorite();
                        favorite.setIdSong(idSong);
                        mFavoriteViewModel.insert(favorite);
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mImvFavorite.setSelected(false);
            }
        });
    }
}
