package com.imusic.fragment.child.favorite;

import android.view.View;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.group.BaseGroupFragment;

public class FavoriteFragment extends BaseFragment {
    @Override
    protected int initLayout() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initComponents() {
        initNavigation();
    }

    @Override
    protected void addListener() {
        showNavLeft(R.drawable.ic_menu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseGroupFragment)getParentFragment()).openMenu();
            }
        });
        showNavRight(R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Coming", Toast.LENGTH_SHORT).show();
            }
        });
    }
}