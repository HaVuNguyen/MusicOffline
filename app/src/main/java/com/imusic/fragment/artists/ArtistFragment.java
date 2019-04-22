package com.imusic.fragment.artists;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.activities.BaseActivity;
import com.imusic.fragment.BaseFragment;
import com.imusic.fragment.artists.details.ArtistFragmentDetails;
import com.imusic.models.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistFragment extends BaseFragment {

    private ArrayList<Artist> mArtists;
    private RecyclerView mRcArtists;
    private ArtistViewModel mArtistViewModel;
    private ArtistAdapter mArtistAdapter;

    public ArtistFragment() {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_artist;
    }

    @Override
    protected void initComponents() {
        mArtists = new ArrayList<>();
        mArtistAdapter = new ArtistAdapter(mArtists);
        mRcArtists = mView.findViewById(R.id.list_artist);
        mRcArtists.setLayoutManager(new LinearLayoutManager(mContext));
        mRcArtists.setAdapter(mArtistAdapter);
        mArtistViewModel = ViewModelProviders.of(this).get(ArtistViewModel.class);
        mArtistViewModel.getAllArtist().observe(this, new Observer<List<Artist>>() {
            @Override
            public void onChanged(@Nullable List<Artist> artists) {
                if (artists != null) {
                    mArtists = new ArrayList<>(artists);
                    mArtistAdapter.notifyDataSetChanged();
                }
            }
        });
        mArtistAdapter.setIOnClickArtist(new ArtistAdapter.IOnClickArtist() {
            @Override
            public void onItemClick(Artist artist, int position) {
                Toast.makeText(mContext, artist.getArtist_name(), Toast.LENGTH_SHORT).show();
                ((BaseActivity) mContext).addFragment(new ArtistFragmentDetails());
            }
        });
        //get artist

        Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);
            int countColumn = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
            do {
                int id = cursor.getInt(idColumn);
                String nameAlbum = cursor.getString(titleColumn);
                String count = cursor.getString(countColumn);
                mArtists.add(new Artist(id, nameAlbum, count));
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void addListener() {

    }
}
