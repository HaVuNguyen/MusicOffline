package com.imusic.fragment.child.my_music.artists.details;

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

public class ArtistDetailAdapter extends RecyclerView.Adapter<ArtistDetailAdapter.ViewHolder> {

    protected ArrayList<Song> mSongs;
    private IOnItemClickListener mListener;

    ArtistDetailAdapter(ArrayList<Song> songs, IOnItemClickListener clickListener) {
        this.mSongs = songs;
        this.mListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_artist_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Song song = mSongs.get(position);
        viewHolder.mImvSong.setImageResource(R.drawable.ic_headphones);
        viewHolder.mTvNameSong.setText(song.getTitle());
        viewHolder.mTvNameSing.setText(song.getArtist());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNameSong, mTvNameSing;
        private ImageView mImvSong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImvSong = itemView.findViewById(R.id.imv_song_artist);
            mTvNameSong = itemView.findViewById(R.id.tv_name_song_artist);
            mTvNameSing = itemView.findViewById(R.id.tv_name_singer_artist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mSongs.get(getLayoutPosition()));
                }
            });
        }
    }

    interface IOnItemClickListener {
        void onItemClick(Song song);
    }
}
