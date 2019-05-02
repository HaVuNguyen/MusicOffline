package com.imusic.fragment.child.my_music.albums;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.SharedPreferenceHelper;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.my_music.albums.detail.AlbumDetailFragment;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.models.Albums;
import com.imusic.ultils.Constant;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends BaseFragment {

    private RecyclerView mRcAlbums;
    private AlbumViewModel mAlbumViewModel;
    private AlbumAdapter mAlbumAdapter;
    private ArrayList<Albums> mAlbums;
    private TextView mTvNoData;

    public AlbumsFragment() {
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_albums;
    }

    @Override
    protected void initComponents() {
        mAlbums = new ArrayList<>();
        mTvNoData = mView.findViewById(R.id.tv_no_album);
        mAlbumAdapter = new AlbumAdapter(mAlbums);
        mRcAlbums = mView.findViewById(R.id.list_albums);
        mRcAlbums.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRcAlbums.setAdapter(mAlbumAdapter);
        mAlbumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);

        getAlbumList();
        mAlbumAdapter.setIOnClickSongListener(new AlbumAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(Albums albums, int position) {
                Toast.makeText(mContext, albums.getName(), Toast.LENGTH_SHORT).show();
                ((BaseGroupFragment) getParentFragment().getParentFragment()).addFragmentNotReloadContent(AlbumDetailFragment.getInstance(albums));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void getAlbumList() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (!AlbumsFragment.this.mContext.getSharedPreferences(SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(Constant.SYNC_ALBUM, false) || mAlbumViewModel.count() == 0) {
                    Cursor albumCursor = AlbumsFragment.this.mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null);
                    if (albumCursor != null && albumCursor.moveToFirst()) {
                        int nameSongColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                        int imgAlbumColumn = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                        do {
                            String nameAlbum = albumCursor.getString(nameSongColumn);
                            String artAlbum = albumCursor.getString(imgAlbumColumn);
                            Albums albums = new Albums();
                            albums.setName(nameAlbum);
                            albums.setAlbumArt(artAlbum);

                            mAlbumViewModel.insert(albums);
                        } while (albumCursor.moveToNext());
                    }
                    albumCursor.close();
                    AlbumsFragment.this.mContext.getSharedPreferences(SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(Constant.SYNC_ALBUM, true).apply();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mAlbumViewModel.getAllAlbums().observe(AlbumsFragment.this, new Observer<List<Albums>>() {
                    @Override
                    public void onChanged(@Nullable List<Albums> albums) {
                        if (albums != null) {
                            mAlbums = new ArrayList<>(albums);
                            mAlbumAdapter.setAlbums(mAlbums);

                            if (mAlbums.size() == 0) {
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
}
