package com.imusic.fragment.albums;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imusic.R;
import com.imusic.models.Albums;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private ArrayList<Albums> mAlbums;
    private View.OnClickListener mListener;
    private IOnItemClickListener mIOnClickSongListener;

    public AlbumAdapter(ArrayList<Albums> albums) {
        this.mAlbums = albums;
    }

    public void setIOnClickSongListener(IOnItemClickListener listener) {
        this.mIOnClickSongListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_albums, viewGroup, false);
        view.setOnClickListener(mListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Albums albums = mAlbums.get(i);
        viewHolder.mTvTitleAlbum.setText(albums.getName());
        if (!TextUtils.isEmpty(albums.getAlbumArt())) {
            if (albums.getAlbumArt().startsWith("http")) {
                Glide.with(viewHolder.view.getContext())
                        .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.bg_album_default))
                        .load(albums.getAlbumArt())
                        .into(viewHolder.mImvAlbums);
            } else {
                Glide.with(viewHolder.view.getContext())
                        .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.bg_album_default))
                        .load(albums.getAlbumArt())
                        .into(viewHolder.mImvAlbums);
            }
        } else {
            Glide.with(viewHolder.view.getContext())
                    .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.bg_album_default))
                    .load(albums.getAlbumArt())
                    .into(viewHolder.mImvAlbums);
        }
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitleAlbum;
        private ImageView mImvAlbums;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitleAlbum = itemView.findViewById(R.id.tv_title_albums);
            mImvAlbums = itemView.findViewById(R.id.imv_albums);
            view = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIOnClickSongListener.onItemClick(mAlbums.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    interface IOnItemClickListener {
        void onItemClick(Albums albums, int position);
    }
}
