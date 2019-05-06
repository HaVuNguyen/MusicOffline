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
import android.widget.TextView;

import com.imusic.R;
import com.imusic.SharedPreferenceHelper;
import com.imusic.activities.PLayerActivity;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.my_music.albums.AlbumViewModel;
import com.imusic.fragment.child.my_music.albums.detail.AlbumDetailViewModel;
import com.imusic.listeners.IOnClickSongListener;
import com.imusic.listeners.OnStartDragListener;
import com.imusic.listeners.SimpleItemTouchHelperCallback;
import com.imusic.models.AlbumSong;
import com.imusic.models.Albums;
import com.imusic.models.Song;
import com.imusic.ultils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SongFragment extends BaseFragment implements IOnClickSongListener, OnStartDragListener {

    private TextView mTvNoData;
    private SongViewModel mSongViewModel;
    private AlbumViewModel mAlbumViewModel;
    private AlbumDetailViewModel mADViewModel;
    private SongAdapter mSongAdapter;
    private ArrayList<Song> mSongs;
    //    private EditText mEdSearch;
    private View mViewSearch;
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
//        mEdSearch = mView.findViewById(R.id.ed_search);
        mViewSearch = mView.findViewById(R.id.layout_search);
        mSongAdapter = new SongAdapter(mSongs, this);
        RecyclerView listMusic = mView.findViewById(R.id.list_music);
        listMusic.setLayoutManager(new LinearLayoutManager(getContext()));
        listMusic.setAdapter(mSongAdapter);
        mSongAdapter.setOnClickListener(this);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mAlbumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        mADViewModel = ViewModelProviders.of(this).get(AlbumDetailViewModel.class);

        getSongList();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mSongAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(listMusic);

    }

    @SuppressLint({"StaticFieldLeak", "WrongThread", "Recycle", "NewApi"})
    private void getSongList() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (!SongFragment.this.mContext.getSharedPreferences(SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(Constant.SYNC_SONG, false) || mSongViewModel.count() == 0 || mAlbumViewModel.count() == 0) {
                    Cursor songCursor = Objects.requireNonNull(SongFragment.this.getContext()).getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                            null, null, null);

                    if (songCursor != null && songCursor.moveToFirst()) {
                        int titleColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                        int artistColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                        do {
                            String mNameSong = songCursor.getString(titleColumn);
                            String mSinger = songCursor.getString(artistColumn);

                            Song song = new Song();
                            song.setTitle(mNameSong);
                            song.setArtist(mSinger);
                            mSongViewModel.insert(song);
                        } while (songCursor.moveToNext());
                    }

                    Cursor albumCursor = SongFragment.this.mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null);
                    if (albumCursor != null && albumCursor.moveToFirst()) {
                        int idAlbumColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
                        int nameSongColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                        int imgAlbumColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                        do {
                            String nameAlbum = albumCursor.getString(nameSongColumn);
                            String artAlbum = albumCursor.getString(imgAlbumColumn);
                            int thisIdAlbum = albumCursor.getInt(idAlbumColumn);

                            Albums albums = new Albums();
                            albums.setName(nameAlbum);
                            albums.setAlbumArt(artAlbum);
                            mAlbumViewModel.insert(albums);
                            long albumId = mAlbumViewModel.insert(albums);
//                             lấy id song từ title song =>  + id album => add vô bảng trung gian
                            Cursor songAlbumCursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
                            if (songAlbumCursor != null && songAlbumCursor.moveToFirst()) {
                                int titleColumn = songAlbumCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                                int idAlbum = songAlbumCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                                do {
                                    String thisTitle = songAlbumCursor.getString(titleColumn);
                                    int thisIdAlbumSong = songAlbumCursor.getInt(idAlbum);
                                    if (thisIdAlbum == thisIdAlbumSong) {
                                        List<Long> listIdSong = mSongViewModel.getIdByTitle(thisTitle);
                                        if (listIdSong.size() > 0) {
                                            long songId = listIdSong.get(0);
                                            AlbumSong albumSong = new AlbumSong();
                                            albumSong.setSongId(songId);
                                            albumSong.setAlbumId(albumId);
                                            mADViewModel.insert(albumSong);
//                                            long l = mADViewModel.insert(albumSong);
//                                            Log.d("TEST", l + "");
                                        }
                                    }
                                }
                                while (songAlbumCursor.moveToNext());
                            }
                        } while (albumCursor.moveToNext());
                    }
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
                                mViewSearch.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.GONE);
                                mViewSearch.setVisibility(View.VISIBLE);
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

    @SuppressLint("NewApi")
    @Override
    public void onItemClickSong(ArrayList<Song> songs, int position) {
        Objects.requireNonNull(getActivity()).startActivity(PLayerActivity.getInstance(getActivity()));
//        getActivity().startService(SongService.getInstance(getContext().getApplicationContext()));
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
