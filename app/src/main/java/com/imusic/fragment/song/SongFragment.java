package com.imusic.fragment.song;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.activities.PLayerActivity;
import com.imusic.db.SRDatabase;
import com.imusic.fragment.BaseFragment;
import com.imusic.listeners.IOnClickSongListener;
import com.imusic.listeners.OnStartDragListener;
import com.imusic.listeners.SimpleItemTouchHelperCallback;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends BaseFragment implements IOnClickSongListener,OnStartDragListener {

    private TextView mTvNoData;
    private RecyclerView mListMuisc;
    private SongViewModel mSongViewModel;
    private SongAdapter mSongAdapter;
    private ArrayList<Song> mSongs;
    private EditText mEdSearch;
    private ItemTouchHelper mItemTouchHelper;

    public SongFragment() {
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_song;
    }

    @Override
    protected void initComponents() {


        mSongs = new ArrayList<>();
        mTvNoData = mView.findViewById(R.id.tv_no_data);
        mEdSearch = mView.findViewById(R.id.ed_search);
        mSongAdapter = new SongAdapter(mSongs,this);
        mListMuisc = mView.findViewById(R.id.list_music);
        mListMuisc.setLayoutManager(new LinearLayoutManager(getContext()));
        mListMuisc.setAdapter(mSongAdapter);
        mSongAdapter.setOnClickListener(this);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mSongViewModel.getAllSongs().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(@Nullable List<Song> songs) {
                if (songs != null) {
                    mSongs = new ArrayList<>(songs);
                    mSongAdapter.notifyDataSetChanged();
                }
            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mSongAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListMuisc);
        ContentResolver musicResolver = getContext().getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        //get song list
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                int song = musicCursor.getInt(idColumn);
                String mNameSong = musicCursor.getString(titleColumn);
                String mSinger = musicCursor.getString(artistColumn);
                String mPath = musicCursor.getString(pathColumn);
                mSongs.add(new Song(song, mNameSong,mSinger));
            } while (musicCursor.moveToNext());
        }
    }

    @Override
    protected void addListener() {

    }

    @Override
    public void onItemClickSong(ArrayList<Song> songs, int position) {
        getActivity().startActivity(PLayerActivity.getInstance(getActivity()));
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
