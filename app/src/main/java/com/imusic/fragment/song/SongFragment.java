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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.activities.PLayerActivity;
import com.imusic.db.SRDatabase;
import com.imusic.listeners.IOnClickSongListener;
import com.imusic.listeners.OnStartDragListener;
import com.imusic.listeners.SimpleItemTouchHelperCallback;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends Fragment implements IOnClickSongListener,OnStartDragListener {

    private TextView mTvTitle;
    private TextView mTvNoData;
    private View mImvBack;
    private RecyclerView mListMuisc;
    private SongViewModel mSongViewModel;
    private SongAdapter mSongAdapter;
    private ArrayList<Song> mSongs;
    private EditText mEdSearch;
    private ItemTouchHelper mItemTouchHelper;

    public SongFragment() {
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
        mSongAdapter = new SongAdapter(mSongs,this);
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

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mSongAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListMuisc);
    }

    public void getSongList() {
        mSongs = new ArrayList<>();
        ContentResolver musicResolver = getContext().getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
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
    public void onItemClickSong(ArrayList<Song> songs, int position) {
        getActivity().startActivity(PLayerActivity.getInstance(getActivity()));
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
//
//    private void searchSongs(){
//        SRDatabase database = SRDatabase.getDatabase(getContext().getApplicationContext());
//        List<Song> arrs = database.mSongDao().searchSong("%" + mEdSearch.getText().toString() + "%");
//        mSongs.clear();
//        for (Song item :arrs) {
//            mSongs.add(item);
//        }
//
//        mSongAdapter.notifyDataSetChanged();
//
//        if (mSongs.size() > 0){
//            mTvNoData.setVisibility(View.GONE);
//        }else {
//            mTvNoData.setVisibility(View.VISIBLE);
//        }
//    }
}
