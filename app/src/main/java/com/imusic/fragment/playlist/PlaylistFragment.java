package com.imusic.fragment.playlist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.activities.BaseActivity;
import com.imusic.fragment.BaseFragment;
import com.imusic.models.Playlist;
import com.imusic.view.AddEditPlaylistDialog;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends BaseFragment {

    private PlaylistAdapter mAdapter;
    private PlaylistViewModel mViewModel;
    private RecyclerView mRcPlaylist;
    private ArrayList<Playlist> mPlaylists;

    @Override
    protected int initLayout() {
        return R.layout.fragment_playlist;
    }

    @Override
    protected void initComponents() {
        mPlaylists = new ArrayList<>();
        mRcPlaylist = mView.findViewById(R.id.rc_list_playlist);
        mAdapter = new PlaylistAdapter(mPlaylists, new PlaylistAdapter.IOnClickListener() {
            @Override
            public void onItemClick(Playlist playlist) {
                Toast.makeText(mContext, "Coming", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteItem(Playlist playlist) {
                Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
            }
        });
        mRcPlaylist.setLayoutManager(new LinearLayoutManager(mContext));
        mRcPlaylist.setAdapter(mAdapter);
        loadPlaylist();
        ((BaseActivity) mContext).initNavigation();
    }

    private void loadPlaylist() {
        mViewModel = ViewModelProviders.of(this).get(PlaylistViewModel.class);
        mViewModel.getAllPlaylist().observe(this, new Observer<List<Playlist>>() {
            @Override
            public void onChanged(@Nullable List<Playlist> playlists) {
                if (playlists != null) {
                    mPlaylists = new ArrayList<>(playlists);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void addListener() {

        ((BaseActivity) mContext).showNavRight(R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditPlaylistDialog dialog = new AddEditPlaylistDialog(mContext, null, new AddEditPlaylistDialog.IOnSubmitListener() {
                    @Override
                    public void submit(String name) {
                        Toast.makeText(mContext, "Create playlist successfully", Toast.LENGTH_SHORT).show();
                        Playlist playlist = new Playlist(name);
                        playlist.setFromUsers(1);
                        mViewModel.insert(playlist);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });
    }
}
