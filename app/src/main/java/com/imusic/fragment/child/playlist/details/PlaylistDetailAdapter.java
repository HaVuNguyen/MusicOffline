package com.imusic.fragment.child.playlist.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.models.Song;

import java.util.ArrayList;

public class PlaylistDetailAdapter extends RecyclerView.Adapter<PlaylistDetailAdapter.ViewHolder> {

    private ArrayList<Song> mSongs;
    private IOnItemClickListener mListener;

    public PlaylistDetailAdapter(ArrayList<Song> songs, IOnItemClickListener listener) {
        mSongs = songs;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_to_playlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Song song = mSongs.get(position);
        holder.mTvNameSong.setText(song.getTitle());
        holder.mTvNameSing.setText(song.getArtist());
        holder.mImvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteItem(song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImvDelete;
        private TextView mTvNameSong;
        private TextView mTvNameSing;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvNameSong = itemView.findViewById(R.id.tv_title_song_to_playlist);
            mTvNameSing = itemView.findViewById(R.id.tv_artist_song_to_playlist);
            mImvDelete = itemView.findViewById(R.id.imv_delete_song_to_playlist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = mSongs.get(getAdapterPosition());
                    mListener.onItemClick(song,getLayoutPosition());
                }
            });
        }
    }

    interface IOnItemClickListener {
        void onItemClick(Song song,int position);

        void onDeleteItem(Song song);
    }
}
