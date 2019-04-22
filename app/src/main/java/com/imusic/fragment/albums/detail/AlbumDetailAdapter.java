package com.imusic.fragment.albums.detail;

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
    private View.OnClickListener mOnClickListener;

    public AlbumDetailAdapter(ArrayList<Song> songs, IOnItemClickListener iOnItemClickListener) {
        this.mSongs = songs;
        this.mListener = iOnItemClickListener;
    }

    public void setOnClickListener(IOnItemClickListener onClickListener) {
        this.mListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_album_detail, viewGroup, false);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Song song = mSongs.get(i);
        holder.mTvNameAlbumDetail.setText(song.getTitle());
        holder.mImvAlbumDetail.setImageResource(R.drawable.bg_album_default);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImvAlbumDetail;
        private TextView mTvNameAlbumDetail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImvAlbumDetail = itemView.findViewById(R.id.imv_albums_detail);
            mTvNameAlbumDetail = itemView.findViewById(R.id.tv_name_album_detail);
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
