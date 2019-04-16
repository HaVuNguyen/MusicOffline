package com.imusic.fragment.song;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
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
import com.imusic.SongService;
import com.imusic.activities.PLayerActivity;
import com.imusic.db.SRDatabase;
import com.imusic.listeners.IOnClickSongListener;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends Fragment implements IOnClickSongListener {

    private int mPage;
    private String mTitle;
    private TextView mTvTitle;
    private TextView mTvNoData;
    private View mImvBack;
    private RecyclerView mListMuisc;
    private SongViewModel mSongViewModel;
    private SongAdapter mSongAdapter;
    private ArrayList<Song> mSongs;
    private EditText mEdSearch;
    private PLayerActivity mPLayerActivity;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        setView(view);
        getSongList();
        setUpRecyclerView(view);
        return view;
    }

    private void setView(View view) {
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvNoData = view.findViewById(R.id.tv_no_data);
        mTvTitle.setText(R.string.tv_my_music);
        mImvBack = view.findViewById(R.id.imv_back);
        mImvBack.setVisibility(View.GONE);
        mEdSearch = view.findViewById(R.id.ed_search);
    }

    private void setUpRecyclerView(View view) {
        mSongAdapter = new SongAdapter(mSongs);
        mListMuisc = view.findViewById(R.id.list_music);
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

    private void searchSongs(){
        SRDatabase database = SRDatabase.getDatabase(getContext().getApplicationContext());
        List<Song> arrs = database.mSongDao().searchSong("%" + mEdSearch.getText().toString() + "%");
        mSongs.clear();
        for (Song item :arrs) {
            mSongs.add(item);
        }

        mSongAdapter.notifyDataSetChanged();

        if (mSongs.size() > 0){
            mTvNoData.setVisibility(View.GONE);
        }else {
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClickSong(ArrayList<Song> songs, int position) {
        getActivity().startActivity(PLayerActivity.getInstance(getActivity()));
    }
}
