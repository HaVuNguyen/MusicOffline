package com.imusic.fragment.child.my_music.albums.detail;

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

public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumDetailAdapter.ViewHolder> {

    private ArrayList<Song> mSongs;
    private IOnItemClickListener mListener;
    private boolean isAdd = false;

    AlbumDetailAdapter(ArrayList<Song> data, boolean TYPE, IOnItemClickListener iOnItemClickListener) {
        this.mSongs = data;
        this.isAdd = TYPE;
        this.mListener = iOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Song song = mSongs.get(position);
        holder.mTvNameAlbumDetail.setText(song.getTitle());
        holder.mImvAlbumDetail.setImageResource(R.drawable.bg_album_default);
        if (!isAdd) {
            holder.mImvAdd.setVisibility(View.GONE);
        } else {
            holder.mImvAdd.setImageResource(R.drawable.bg_add);
            holder.mImvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onAddItem(song);
                    holder.mImvAdd.setSelected(true);
                }
            });
            holder.mImvAdd.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImvAlbumDetail;
        private TextView mTvNameAlbumDetail;
        private ImageView mImvAdd;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            mImvAlbumDetail = itemView.findViewById(R.id.imv_albums_detail);
            mTvNameAlbumDetail = itemView.findViewById(R.id.tv_name_album_detail);
            mImvAdd = itemView.findViewById(R.id.imv_add);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = mSongs.get(getLayoutPosition());
                    mListener.onItemClick(song,getLayoutPosition());
                }
            });
        }
    }

    interface IOnItemClickListener {
        void onItemClick(Song song,int position);

        void onAddItem(Song song);
    }
}
