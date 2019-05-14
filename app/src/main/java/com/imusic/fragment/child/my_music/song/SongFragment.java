package com.imusic.fragment.child.my_music.song;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
import com.imusic.SongService;
import com.imusic.activities.MainActivity;
import com.imusic.activities.PLayerActivity;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.my_music.albums.AlbumViewModel;
import com.imusic.fragment.child.my_music.albums.detail.AlbumDetailViewModel;
import com.imusic.fragment.child.my_music.artists.ArtistViewModel;
import com.imusic.fragment.child.my_music.artists.details.ArtistDetailViewModel;
import com.imusic.fragment.child.playlist.details.PlaylistDetailViewModel;
import com.imusic.listeners.IOnClickSongListener;
import com.imusic.listeners.OnStartDragListener;
import com.imusic.listeners.SimpleItemTouchHelperCallback;
import com.imusic.models.AlbumSong;
import com.imusic.models.Albums;
import com.imusic.models.Artist;
import com.imusic.models.ArtistSong;
import com.imusic.models.Playlist;
import com.imusic.models.PlaylistSong;
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
    private ArtistViewModel mArtistViewModel;
    private ArtistDetailViewModel mArtistDetailViewModel;
    private SongAdapter mSongAdapter;
    private ArrayList<Song> mSongs;
    private View mViewSearch;
    private ItemTouchHelper mItemTouchHelper;
    private PlaylistDetailViewModel mDetailViewModel;
    private Playlist mPlaylist;
    private RecyclerView mListMusic;
    private SongService mService;

    @Override
    protected int initLayout() {
        return R.layout.fragment_song;
    }

    @Override
    protected void initComponents() {
        mSongs = new ArrayList<>();
        mTvNoData = mView.findViewById(R.id.tv_no_data);
        mViewSearch = mView.findViewById(R.id.layout_search);
        mSongViewModel = ViewModelProviders.of(this).get(SongViewModel.class);
        mAlbumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        mADViewModel = ViewModelProviders.of(this).get(AlbumDetailViewModel.class);
        mArtistViewModel = ViewModelProviders.of(this).get(ArtistViewModel.class);
        mArtistDetailViewModel = ViewModelProviders.of(this).get(ArtistDetailViewModel.class);
        mDetailViewModel = ViewModelProviders.of(this).get(PlaylistDetailViewModel.class);

        final boolean isAdd = getActivity().getIntent().getBooleanExtra(Constant.TYPE_ADD_SONG, false);
        mPlaylist = (Playlist) getActivity().getIntent().getSerializableExtra(Constant.TYPE_PLAYLIST);
        mSongAdapter = new SongAdapter(mSongs, isAdd, this, new SongAdapter.IOnAddClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onAddItem(Song song) {
                if (isAdd) {
                    final long idSong = song.getId();
                    final long idPlaylist = mPlaylist.getId();
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            PlaylistSong playlistSong = new PlaylistSong();
                            playlistSong.setSongId(idSong);
                            playlistSong.setPlaylistId(idPlaylist);
                            mDetailViewModel.insert(playlistSong);
                            return null;
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
        mListMusic = mView.findViewById(R.id.list_music);
        mListMusic.setLayoutManager(new LinearLayoutManager(getContext()));
        mListMusic.setAdapter(mSongAdapter);
        mSongAdapter.setOnClickListener(this);
        getSongList();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mSongAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mListMusic);
    }

    @SuppressLint({"StaticFieldLeak", "NewApi", "Recycle"})
    private void getSongList() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (!SongFragment.this.mContext.getSharedPreferences(SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(Constant.SYNC_SONG, false)) {
                    Cursor songCursor = Objects.requireNonNull(SongFragment.this.getContext()).getContentResolver()
                            .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                                    null, null, null);

                    if (songCursor != null && songCursor.moveToFirst()) {
                        int titleColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                        int artistColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                        int pathColumn = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                        do {
                            String mNameSong = songCursor.getString(titleColumn);
                            String mSinger = songCursor.getString(artistColumn);
                            String mPath = songCursor.getString(pathColumn);

                            Song song = new Song();
                            song.setTitle(mNameSong);
                            song.setArtist(mSinger);
                            song.setSongPath(mPath);
                            mSongViewModel.insert(song);
                        } while (songCursor.moveToNext());
                    }

                    getAlbum();
                    getArtist();
                    SongFragment.this.mContext.getSharedPreferences
                            (SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                            .edit().putBoolean(Constant.SYNC_SONG, true).apply();
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

    @SuppressLint({"StaticFieldLeak", "NewApi", "Recycle"})
    private void getAlbum() {
        Cursor albumCursor = SongFragment.this.mContext.getContentResolver()
                .query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
                        null, null, null);
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
                long albumId = mAlbumViewModel.insert(albums);
                // lấy title song =>  + id album => add vô bảng trung gian
                Cursor songAlbumCursor = mContext.getContentResolver()
                        .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                                null, null, null);
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
                            }
                        }
                    }
                    while (songAlbumCursor.moveToNext());
                }
//                mADViewModel.getSongTest();
//                songAlbumCursor.close();
            } while (albumCursor.moveToNext());
        }
//        mAlbumViewModel.getAlbumTest();
//        albumCursor.close();
    }

    @SuppressLint({"StaticFieldLeak", "NewApi", "Recycle"})
    private void getArtist() {
        Cursor artistCursor = SongFragment.this.mContext.getContentResolver()
                .query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null,
                        null, null, null);
        if (artistCursor != null && artistCursor.moveToFirst()) {
            int idArtistColumn = artistCursor.getColumnIndex(MediaStore.Audio.Artists._ID);
            int nameArtistColumn = artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
            int countArtistColumn = artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
            do {
                String nameArtist = artistCursor.getString(nameArtistColumn);
                String countArtist = artistCursor.getString(countArtistColumn);
                int thisIdArtist = artistCursor.getInt(idArtistColumn);

                Artist artist = new Artist();
                artist.setName(nameArtist);
                artist.setCount(countArtist);
                long artistId = mArtistViewModel.insert(artist);
                // lấy title song =>  + id artist => add vô bảng trung gian
                Cursor songArtistCursor = mContext.getContentResolver()
                        .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                                null, null, null);
                if (songArtistCursor != null && songArtistCursor.moveToFirst()) {
                    int titleColumn = songArtistCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                    int idArtist = songArtistCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
                    do {
                        String thisTitle = songArtistCursor.getString(titleColumn);
                        int thisIdArtistSong = songArtistCursor.getInt(idArtist);
                        if (thisIdArtist == thisIdArtistSong) {
                            List<Long> listIdSong = mSongViewModel.getIdByTitle(thisTitle);
                            if (listIdSong.size() > 0) {
                                long songId = listIdSong.get(0);
                                ArtistSong artistSong = new ArtistSong();
                                artistSong.setIdSong(songId);
                                artistSong.setIdArtist(artistId);
                                mArtistDetailViewModel.insert(artistSong);
                            }
                        }
                    } while (songArtistCursor.moveToNext());
                }

            } while (artistCursor.moveToNext());
        }
    }

    @Override
    protected void addListener() {

    }

    @SuppressLint("NewApi")
    @Override
    public void onItemClickSong(ArrayList<Song> songs, int position) {
        Intent intent = new Intent(mContext,PLayerActivity.class);
        intent.putExtra(Constant.LIST_SONG,mSongs);
        intent.putExtra(Constant.POSITION_SONG,position);
        mContext.startActivity(intent);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
