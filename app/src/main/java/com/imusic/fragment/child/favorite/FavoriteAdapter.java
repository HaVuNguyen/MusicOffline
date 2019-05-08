package com.imusic.fragment.child.favorite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.models.Favorite;
import com.imusic.models.Song;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private IOnClickListener mListener;
    private ArrayList<Song> mSongs;

    public FavoriteAdapter(ArrayList<Song> songs, IOnClickListener listener) {
        mSongs = songs;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_song_to_playlist, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Song song = mSongs.get(i);
        viewHolder.mImvFavorite.setImageResource(R.drawable.ic_my_favorite_selected);
        viewHolder.mTvNameSong.setText(song.getTitle());
        viewHolder.mTvNameSinger.setText(song.getArtist());
        viewHolder.mImvDelete.setImageResource(R.drawable.ic_delete);
        viewHolder.mImvDelete.setOnClickListener(new View.OnClickListener() {
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
        private ImageView mImvDelete, mImvFavorite;
        private TextView mTvNameSong, mTvNameSinger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImvDelete = itemView.findViewById(R.id.imv_delete_song_to_playlist);
            mImvFavorite = itemView.findViewById(R.id.imv_my_music_song_to_playlist);
            mTvNameSong = itemView.findViewById(R.id.tv_title_song_to_playlist);
            mTvNameSinger = itemView.findViewById(R.id.tv_artist_song_to_playlist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = mSongs.get(getAdapterPosition());
                    mListener.onItemClick(song);
                }
            });
        }
    }

    interface IOnClickListener {
        void onItemClick(Song song);

        void onDeleteItem(Song song);
    }
}
