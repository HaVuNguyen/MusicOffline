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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.adapter.MyMusicAdapter;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends Fragment {

    private int mPage;
    private String mTitle;
    private TextView mTvTitle;
    private View mImvBack;
    private RecyclerView mListMuisc;
    private SongViewModel mSongViewModel;
    private SongAdapter mSongAdapter;
    private ArrayList<Song> mSongs;

    public SongFragment() {
    }

    public static SongFragment newInstance(int page, String title) {
        SongFragment songFragment = new SongFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        songFragment.setArguments(args);
        return songFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("page", 0);
        mTitle = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        setView(view);
        getSongList();
        setUpRecyclerView(view);
        return view;
    }

    private void setView(View view) {
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.tv_my_music);
        mImvBack = view.findViewById(R.id.imv_back);
        mImvBack.setVisibility(View.GONE);
    }

    private void setUpRecyclerView(View view) {
        mSongAdapter = new SongAdapter(mSongs);
        mListMuisc = view.findViewById(R.id.list_music);
        mListMuisc.setLayoutManager(new LinearLayoutManager(getContext()));
        mListMuisc.setAdapter(mSongAdapter);
        mSongAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming", Toast.LENGTH_SHORT).show();
            }
        });
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
    }

    public void getSongList() {
        mSongs = new ArrayList<>();
        //query external audio
        ContentResolver musicResolver = getContext().getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        //iterate over results if valid
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            //add songs to list
            do {
                int song = musicCursor.getInt(idColumn);
                String mNameSong = musicCursor.getString(titleColumn);
                String mSinger = musicCursor.getString(artistColumn);
                String mPath = musicCursor.getString(pathColumn);
                mSongs.add(new Song(song, mNameSong,mSinger));
            } while (musicCursor.moveToNext());
        }
    }
}
