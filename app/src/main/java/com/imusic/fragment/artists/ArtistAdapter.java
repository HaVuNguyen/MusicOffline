package com.imusic.fragment.artists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imusic.R;
import com.imusic.models.Artist;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private ArrayList<Artist> mArtists;
    private IOnClickArtist mIOnClickArtist;
    private View.OnClickListener mListener;

    ArtistAdapter(ArrayList<Artist> artists) {
        this.mArtists = artists;
    }

    void setIOnClickArtist(IOnClickArtist IOnClickArtist) {
        mIOnClickArtist = IOnClickArtist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup holder, int i) {
        View view = LayoutInflater.from(holder.getContext()).inflate(R.layout.item_atists, holder, false);
        view.setOnClickListener(mListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Artist artist = mArtists.get(i);
        holder.mTvArtistName.setText(artist.getArtist_name());
        Glide.with(holder.itemView.getContext())
                .applyDefaultRequestOptions
                        (RequestOptions.placeholderOf(R.drawable.ic_singer))
                .load(artist.getArtist_image()).into(holder.mImvArtist);
        holder.mTvCountSong.setText(artist.getCount_song());
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private ImageView mImvArtist;
        private TextView mTvArtistName;
        private TextView mTvCountSong;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mImvArtist = itemView.findViewById(R.id.imv_artist);
            mTvArtistName = itemView.findViewById(R.id.tv_title_artist);
            mTvCountSong = itemView.findViewById(R.id.tv_count_artist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIOnClickArtist.onItemClick(mArtists.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    interface IOnClickArtist {
        void onItemClick(Artist artist, int position);
    }
}
