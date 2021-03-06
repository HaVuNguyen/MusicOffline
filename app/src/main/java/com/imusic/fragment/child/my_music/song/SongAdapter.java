package com.imusic.fragment.child.my_music.song;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.listeners.IOnClickSongListener;
import com.imusic.listeners.ItemTouchHelperAdapter;
import com.imusic.listeners.ItemTouchHelperViewHolder;
import com.imusic.listeners.OnStartDragListener;
import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.Collections;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>
        implements ItemTouchHelperAdapter {
    private ArrayList<Song> mSongs;
    private IOnClickSongListener mListener;
    private IOnAddClickListener mOnAddClickListener;
    private boolean isAdd = false;
    private boolean isPlayOrPause = false;
    private int mPosition = -1;
    private Song mSong;

    SongAdapter(ArrayList<Song> songs, boolean TYPE_IS_ADD, IOnAddClickListener onAddClickListener) {
        this.mSongs = songs;
        isAdd = TYPE_IS_ADD;
        mOnAddClickListener = onAddClickListener;
    }

    public void setOnClickListener(IOnClickSongListener onClickListener) {
        this.mListener = onClickListener;
    }

    @NonNull
    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_song, viewGroup, false);
        return new SongViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final SongAdapter.SongViewHolder holder, int position) {
        final Song song = mSongs.get(position);
        holder.mImvMusic.setImageResource(R.drawable.ic_headphones);
        holder.mTvArtist.setText(song.getArtist());
        holder.mTvTitle.setText(song.getTitle());
        holder.itemView.setTag(song);

        if (!isAdd) {
            holder.mImvMenu.setVisibility(View.GONE);
        } else {
            holder.mImvMenu.setVisibility(View.VISIBLE);
            holder.mImvMenu.setImageResource(R.drawable.bg_add);

            if (song.isAdded()) {
                holder.mImvMenu.setSelected(true);
            } else {
                holder.mImvMenu.setSelected(false);
            }

            holder.mImvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddClickListener.onAddItem(song);
                    song.setAdded(true);
//                    if (!song.isAdded()){
//                        song.setAdded(true);
//                    }else {
//                        song.setAdded(false);
//                    }
                    notifyDataSetChanged();
                }
            });
        }
        if (position == mPosition) {
            holder.mTvTitle.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
            holder.mTvArtist.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        } else {
            holder.mTvTitle.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorBlack));
            holder.mTvArtist.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorBlack));
        }
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mSongs, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mSongs.remove(position);
        notifyItemRemoved(position);
    }

    public class SongViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {
        ImageView mImvMusic;
        ImageView mImvMenu;
        TextView mTvTitle;
        TextView mTvArtist;
        View view;

        SongViewHolder(@NonNull View itemView) {
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
            itemView.setBackgroundColor(Color.GRAY);
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

    void setListSongPosition(ArrayList<Song> songs, int position) {
        mSongs = songs;
        mPosition = position;
        notifyDataSetChanged();
    }

    void setPositionSong(Song song, int position) {
        mSong = song;
        mPosition = position;
        notifyDataSetChanged();
    }

    void setIsPlayOrPause(boolean isPLay) {
        isPlayOrPause = isPLay;
        notifyDataSetChanged();
    }

    interface IOnAddClickListener {
        void onAddItem(Song song);
    }
}
