package com.imusic.fragment.song;

import android.content.Context;
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
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private ArrayList<Song> mSongs;
    private View.OnClickListener mOnClickListener;

    public SongAdapter( ArrayList<Song> songs) {
        this.mSongs = songs;
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music,viewGroup,false);
        view.setOnClickListener(mOnClickListener);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongViewHolder holder, int i) {
        Song song = mSongs.get(i);
        holder.mImvMusic.setImageResource(R.drawable.ic_my_music_selected);
        holder.mImvMenu.setImageResource(R.drawable.ic_menu);
        holder.mTvArtist.setText(song.getArtist());
        holder.mTvTitle.setText(song.getTitle());
        holder.itemView.setTag(song);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView mImvMusic;
        ImageView mImvMenu;
        TextView mTvTitle;
        TextView mTvArtist;
        View view;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            mImvMusic = itemView.findViewById(R.id.imv_my_music);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvArtist = itemView.findViewById(R.id.tv_artist);
            mImvMenu = itemView.findViewById(R.id.imv_menu);
            view =itemView;
        }
    }

    void setSongs(ArrayList<Song> songs) {
        mSongs = songs;
        notifyDataSetChanged();
    }

    public Song getSongAtPosition(int position) {
        return mSongs.get(position);
    }
}
