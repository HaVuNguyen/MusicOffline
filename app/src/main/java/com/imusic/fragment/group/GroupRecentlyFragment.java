package com.imusic.fragment.group;

import com.imusic.fragment.child.recently.RecentlyFragment;

public class GroupRecentlyFragment extends BaseGroupFragment {
    @Override
    public void showRootFragment() {
        replaceFragment(new RecentlyFragment());
    }
}
