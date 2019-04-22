package com.imusic.fragment.favorite;

import android.view.View;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.activities.BaseActivity;
import com.imusic.fragment.BaseFragment;

public class FavoriteFragment extends BaseFragment {
    @Override
    protected int initLayout() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initComponents() {
        ((BaseActivity) mContext).initNavigation();
    }

    @Override
    protected void addListener() {
        ((BaseActivity) mContext).showNavRight(R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Coming", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
