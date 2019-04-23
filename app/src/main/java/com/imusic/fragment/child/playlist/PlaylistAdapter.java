package com.imusic.fragment.child.playlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.models.Playlist;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private ArrayList<Playlist> mPlaylists;
    private IOnClickListener mIOnClickListener;

    PlaylistAdapter(ArrayList<Playlist> playlists, IOnClickListener IOnClickListener) {
        this.mPlaylists = playlists;
        this.mIOnClickListener = IOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_playlist, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Playlist playlist = mPlaylists.get(i);
        holder.mTvNamePlaylist.setText(playlist.getTitle());
        holder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIOnClickListener.onDeleteItem(playlist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNamePlaylist;
        private TextView mTvDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvDelete = itemView.findViewById(R.id.tv_delete);
            mTvNamePlaylist = itemView.findViewById(R.id.tv_title_playlist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIOnClickListener.onItemClick(mPlaylists.get(getLayoutPosition()));
                }
            });

        }
    }

    interface IOnClickListener {
        void onItemClick(Playlist playlist);

        void onDeleteItem(Playlist playlist);
    }
}
