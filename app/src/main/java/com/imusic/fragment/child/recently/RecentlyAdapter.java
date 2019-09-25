package com.imusic.fragment.child.recently;

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

public class RecentlyAdapter extends RecyclerView.Adapter<RecentlyAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Song> mData;
    private IOnClickItemListener mListener;

    public RecentlyAdapter(Context context, ArrayList<Song> data, IOnClickItemListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recently, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Song item = mData.get(position);
        holder.mTvTitle.setText(item.getTitle());
        holder.mTvArtist.setText(item.getArtist());
        holder.mImvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteItem(mData.get(position), position);
            }
        });

        holder.mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(mData.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvArtist;
        private ImageView mImvDelete;
        private View mBtnPlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvArtist = itemView.findViewById(R.id.tv_artist);
            mImvDelete = itemView.findViewById(R.id.imv_delete);
            mBtnPlay = itemView.findViewById(R.id.view_item);
        }
    }

    interface IOnClickItemListener {
        void onItemClick(Song song, int position);

        void onDeleteItem(Song song, int position);
    }
}
