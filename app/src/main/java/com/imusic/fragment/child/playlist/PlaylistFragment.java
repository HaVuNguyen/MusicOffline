package com.imusic.fragment.child.playlist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.playlist.details.PlaylistDetailFragment;
import com.imusic.fragment.group.BaseGroupFragment;
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
        mViewModel = ViewModelProviders.of(this).get(PlaylistViewModel.class);
        mRcPlaylist = mView.findViewById(R.id.rc_list_playlist);
        mAdapter = new PlaylistAdapter(mPlaylists, new PlaylistAdapter.IOnClickListener() {
            @Override
            public void onItemClick(Playlist playlist) {
                ((BaseGroupFragment) getParentFragment()).addFragmentNotReloadContent(PlaylistDetailFragment.getInstance(playlist));
            }

            @Override
            public void onDeleteItem(Playlist playlist) {
                mViewModel.deletePlaylist(playlist);
                Toast.makeText(mContext, getString(R.string.tv_delete_playlist_successfully), Toast.LENGTH_SHORT).show();
            }
        });
        mRcPlaylist.setLayoutManager(new LinearLayoutManager(mContext));
        mRcPlaylist.setAdapter(mAdapter);
        mViewModel.getAllPlaylist().observe(PlaylistFragment.this, new Observer<List<Playlist>>() {
            @Override
            public void onChanged(@Nullable List<Playlist> playlists) {
                if (playlists != null) {
                    mPlaylists = new ArrayList<>(playlists);
                    mAdapter.setPlaylists(mPlaylists);
                }
            }
        });
        initNavigation();
    }

    @Override
    protected void addListener() {
        showNavLeft(R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseGroupFragment) getParentFragment()).openMenu();
            }
        });

        showNavRight(R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditPlaylistDialog dialog = new AddEditPlaylistDialog(mContext, null, new AddEditPlaylistDialog.IOnSubmitListener() {
                    @Override
                    public void submit(String name) {
//                        Toast.makeText(mContext, mContext.getString(R.string.tv_create_playlist_success), Toast.LENGTH_SHORT).show();
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
