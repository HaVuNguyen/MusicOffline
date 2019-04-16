package com.imusic.fragment.song;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.listeners.IOnClickSongListener;
import com.imusic.listeners.ItemTouchHelperAdapter;
import com.imusic.listeners.ItemTouchHelperViewHolder;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.Collections;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> implements ItemTouchHelperAdapter {
    private ArrayList<Song> mSongs = null;
    private View.OnClickListener mOnClickListener;
    private IOnClickSongListener mListener;

    public SongAdapter(ArrayList<Song> songs) {
        this.mSongs = songs;
    }

    public void setOnClickListener(IOnClickSongListener onClickListener) {
        this.mListener = onClickListener;
    }

    @NonNull
    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music, viewGroup, false);
        view.setOnClickListener(mOnClickListener);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongViewHolder holder, int i) {
        Song song = mSongs.get(i);
        holder.mImvMusic.setImageResource(R.drawable.ic_music_default);
        holder.mImvMenu.setImageResource(R.drawable.ic_menu);
        holder.mTvArtist.setText(song.getArtist());
        holder.mTvTitle.setText(song.getTitle());
        holder.itemView.setTag(song);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mSongs, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mSongs.remove(position);
        notifyItemRemoved(position);
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
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
            view = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClickSong(mSongs, getAdapterPosition());
                }
            });
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
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
