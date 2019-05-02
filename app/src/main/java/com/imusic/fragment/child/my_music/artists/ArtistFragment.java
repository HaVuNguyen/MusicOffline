package com.imusic.fragment.child.my_music.artists;

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
import android.view.View;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.SharedPreferenceHelper;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.my_music.artists.details.ArtistDetailsFragment;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.models.Artist;
import com.imusic.ultils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ArtistFragment extends BaseFragment {

    private ArrayList<Artist> mArtists;
    private RecyclerView mRcArtists;
    private ArtistViewModel mArtistViewModel;
    private ArtistAdapter mArtistAdapter;
    private TextView mTvNoData;

    public ArtistFragment() {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_artist;
    }

    @Override
    protected void initComponents() {
        mArtists = new ArrayList<>();
        mTvNoData = mView.findViewById(R.id.tv_no_artist);
        mArtistAdapter = new ArtistAdapter(mArtists);
        mRcArtists = mView.findViewById(R.id.list_artist);
        mRcArtists.setLayoutManager(new LinearLayoutManager(mContext));
        mRcArtists.setAdapter(mArtistAdapter);
        mArtistViewModel = ViewModelProviders.of(this).get(ArtistViewModel.class);
        getArtistList();

        mArtistAdapter.setIOnClickArtist(new ArtistAdapter.IOnClickArtist() {
            @Override
            public void onItemClick(Artist artist, int position) {
                ((BaseGroupFragment) getParentFragment().getParentFragment()).addFragmentNotReloadContent(ArtistDetailsFragment.getInstance(artist));
            }
        });
        //get artist


    }

    @SuppressLint("StaticFieldLeak")
    private void getArtistList() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (!ArtistFragment.this.mContext.getSharedPreferences(SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(Constant.SYNC_ARTIST, false) || mArtistViewModel.count() == 0) {
                    Cursor cursor = ArtistFragment.this.mContext.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
                        int countColumn = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
                        do {
                            String nameAlbum = cursor.getString(titleColumn);
                            String count = cursor.getString(countColumn);
                            Artist artist = new Artist();
                            artist.setArtist_name(nameAlbum);
                            artist.setCount_song(count);
                            mArtistViewModel.insert(artist);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    ArtistFragment.this.mContext.getSharedPreferences(SharedPreferenceHelper.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(Constant.SYNC_ARTIST, true).apply();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mArtistViewModel.getAllArtist().observe(ArtistFragment.this, new Observer<List<Artist>>() {
                    @Override
                    public void onChanged(@Nullable List<Artist> artists) {
                        if (artists != null) {
                            mArtists = new ArrayList<>(artists);
                            mArtistAdapter.setArtists(mArtists);
                            if (mArtists.size() == 0) {
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
