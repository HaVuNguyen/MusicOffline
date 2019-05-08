package com.imusic.fragment.child.favorite.add_song;

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

public class AddSongToFavoriteAdapter extends RecyclerView.Adapter<AddSongToFavoriteAdapter.ViewHolder> {
    private ArrayList<Song> mSongs;
    private IOnClickListener mListener;

    public AddSongToFavoriteAdapter(ArrayList<Song> songs, IOnClickListener listener) {
        mSongs = songs;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_song, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Song song = mSongs.get(i);
        viewHolder.mTvNameSong.setText(song.getTitle());
        viewHolder.mTvNameArtist.setText(song.getArtist());
        viewHolder.mImvAdd.setSelected(false);
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvNameSong, mTvNameArtist;
        private ImageView mImvAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvNameSong = itemView.findViewById(R.id.tv_name_song_add);
            mTvNameArtist = itemView.findViewById(R.id.tv_name_artist_add);
            mImvAdd = itemView.findViewById(R.id.imv_add_song);
            mImvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = mSongs.get(getAdapterPosition());
                    mListener.onAddSong(song);
                    mImvAdd.setSelected(true);
                }
            });
        }
    }

    interface IOnClickListener {
        void onAddSong(Song song);
    }

    void setSongToFavorite(ArrayList<Song> songs) {
        mSongs = songs;
        notifyDataSetChanged();
    }
}
