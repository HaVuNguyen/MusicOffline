package com.imusic.fragment.child.my_music.artists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.models.Artist;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private ArrayList<Artist> mArtists;
    private IOnClickArtist mIOnClickArtist;

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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Artist artist = mArtists.get(i);
        holder.mTvArtistName.setText(artist.getName());
        holder.mImvArtist.setImageResource(R.drawable.ic_singer);
        holder.mTvCountSong.setText(artist.getCount());
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImvArtist;
        private TextView mTvArtistName;
        private TextView mTvCountSong;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
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

    void setArtists(ArrayList<Artist> artists) {
        mArtists = artists;
        notifyDataSetChanged();
    }
}
