package com.imusic.fragment.albums;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.activities.BaseActivity;
import com.imusic.fragment.BaseFragment;
import com.imusic.fragment.albums.detail.AlbumDetailFragment;
import com.imusic.models.Albums;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends BaseFragment {

    private RecyclerView mRcAlbums;
    private AlbumViewModel mAlbumViewModel;
    private AlbumAdapter mAlbumAdapter;
    private ArrayList<Albums> mAlbums;

    public AlbumsFragment() {
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_albums;
    }

    @Override
    protected void initComponents() {
        mAlbums = new ArrayList<>();
        mAlbumAdapter = new AlbumAdapter(mAlbums);
        mRcAlbums = mView.findViewById(R.id.list_albums);
        mRcAlbums.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRcAlbums.setAdapter(mAlbumAdapter);
        mAlbumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        mAlbumViewModel.getAllAlbums().observe(this, new Observer<List<Albums>>() {
            @Override
            public void onChanged(@Nullable List<Albums> albums) {
                if (albums != null) {
                    mAlbums = new ArrayList<>(albums);
                    mAlbumAdapter.notifyDataSetChanged();
                }
            }
        });
        mAlbumAdapter.setIOnClickSongListener(new AlbumAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(Albums albums, int position) {
                Toast.makeText(mContext, albums.getName(), Toast.LENGTH_SHORT).show();
                ((BaseActivity) mContext).addFragment(new AlbumDetailFragment());
            }
        });

        //get album
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int artColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            do {
                int id = cursor.getInt(idColumn);
                String nameAlbum = cursor.getString(titleColumn);
                String imageAlbum = cursor.getString(artColumn);
                mAlbums.add(new Albums(id, nameAlbum, imageAlbum));
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void addListener() {
    }


}
