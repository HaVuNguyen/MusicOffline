package com.imusic.fragment.child.my_music.song;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.SharedPreferenceHelper;
import com.imusic.activities.PLayerActivity;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.listeners.IOnClickSongListener;
import com.imusic.listeners.OnStartDragListener;
import com.imusic.listeners.SimpleItemTouchHelperCallback;
import com.imusic.models.Song;
import com.imusic.ultils.Constant;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends BaseFragment implements IOnClickSongListener, OnStartDragListener {

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
        mSongAdapter = new SongAdapter(mSongs, this);
        mListMuisc = mView.findViewById(R.id.list_music);
        mListMuisc.setLayoutManager(new LinearLayoutManager(getContext()));
        mListMuisc.setAdapter(mSongAdapter);
        mSongAdapter.setOnClickListener(this);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);

        getSongList();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mSongAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListMuisc);
    }

    @SuppressLint("StaticFieldLeak")
    private void getSongList() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (!SongFragment.this.mContext.getSharedPreferences(SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(Constant.SYNC_SONG, false) || mSongViewModel.count() == 0) {
                    Cursor songCursor = SongFragment.this.getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

                    if (songCursor != null && songCursor.moveToFirst()) {
                        int titleColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                        int idColumn = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
                        int artistColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                        do {
                            long id = songCursor.getLong(idColumn);
                            String mNameSong = songCursor.getString(titleColumn);
                            String mSinger = songCursor.getString(artistColumn);

                            Song song = new Song();
                            song.setTitle(mNameSong);
                            song.setArtist(mSinger);
                            mSongViewModel.insert(song);
                        } while (songCursor.moveToNext());
                    }
                    songCursor.close();
                    SongFragment.this.mContext.getSharedPreferences(SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(Constant.SYNC_SONG, true).apply();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mSongViewModel.getAllSongs().observe(SongFragment.this, new Observer<List<Song>>() {
                    @Override
                    public void onChanged(@Nullable List<Song> songs) {
                        if (songs != null) {
                            mSongs = new ArrayList<>(songs);
                            mSongAdapter.setSongs(mSongs);

                            if (mSongs.size() == 0) {
                                mTvNoData.setVisibility(View.VISIBLE);
                            } else {
                                mTvNoData.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void addListener() {

    }

    @Override
    public void onItemClickSong(ArrayList<Song> songs, int position) {
        getActivity().startActivity(PLayerActivity.getInstance(getActivity()));
//        getActivity().startService(SongService.getInstance(getContext().getApplicationContext()));
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
