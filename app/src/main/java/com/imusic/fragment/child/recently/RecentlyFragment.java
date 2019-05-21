package com.imusic.fragment.child.recently;

import android.view.View;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.activities.BaseActivity;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.group.BaseGroupFragment;

public class RecentlyFragment extends BaseFragment {
    @Override
    protected int initLayout() {
        return R.layout.fragment_recently;
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
                ((BaseGroupFragment) getParentFragment()).openMenu();
            }
        });
    }
}
