package com.imusic.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;

public class MyMusicAdapter extends RecyclerView.Adapter<MyMusicAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mImvMusic.setImageResource(R.drawable.ic_my_music);
        viewHolder.mImvMenu.setImageResource(R.drawable.ic_menu);
        viewHolder.mTvTitle.setText("Title");
        viewHolder.mTvArtist.setText("Artist");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImvMusic;
        ImageView mImvMenu;
        TextView mTvTitle;
        TextView mTvArtist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImvMusic = itemView.findViewById(R.id.imv_my_music);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvArtist = itemView.findViewById(R.id.tv_artist);
            mImvMenu = itemView.findViewById(R.id.imv_menu);
        }
    }
}
