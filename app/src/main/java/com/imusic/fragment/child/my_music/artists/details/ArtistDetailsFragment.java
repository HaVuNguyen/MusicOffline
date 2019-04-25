package com.imusic.fragment.child.my_music.artists.details;

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

import com.imusic.R;
import com.imusic.activities.PLayerActivity;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.models.Artist;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ArtistDetailsFragment extends BaseFragment {

    private TextView mTvTitleArtist;
    private ImageView mImvBack,mImvSinger,mImvAdd;
    private RecyclerView mListSongArtist;
    private Artist mArtist;
    private ArrayList<Song> mSongs;

    private ArtistDetailAdapter mAdapter;
    private ArtistDetailViewModel mViewModel;

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
        mSongs = new ArrayList<>();
        getSongByArtist();
        mImvBack = mView.findViewById(R.id.btn_back_artist);
        mImvSinger = mView.findViewById(R.id.imv_singer);
        mImvAdd = mImvBack.findViewById(R.id.imv_add_artist);
        mTvTitleArtist = mView.findViewById(R.id.tv_name_artists);
        mListSongArtist = mView.findViewById(R.id.rc_artist_detail);
        mListSongArtist.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ArtistDetailAdapter(mSongs, new ArtistDetailAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
                Toast.makeText(mContext, "Play music", Toast.LENGTH_SHORT).show();
                getActivity().startActivity(PLayerActivity.getInstance(getActivity()));
            }
        });
        mListSongArtist.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this).get(ArtistDetailViewModel.class);
        mViewModel.getListLiveDataSongArtist(mArtist.getId()).observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(@Nullable List<Song> songs) {
                if (songs != null) {
                    mSongs = new ArrayList<>(songs);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void getSongByArtist() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=0";
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, selection, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistIdColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                int id = cursor.getInt(idColumn);
                String title = cursor.getString(titleColumn);
                int idArtist = cursor.getInt(artistIdColumn);
                String artist = cursor.getString(artistColumn);
                if (mArtist.getId() == idArtist) {
                    mSongs.add(new Song(title, artist, idArtist));
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    @Override
    protected void addListener() {
        mTvTitleArtist.setText(mArtist.getArtist_name());
        mImvSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mArtist.getArtist_name(), Toast.LENGTH_SHORT).show();
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
