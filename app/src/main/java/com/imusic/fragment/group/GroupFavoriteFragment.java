package com.imusic.fragment.group;

import com.imusic.fragment.child.favorite.FavoriteFragment;

public class GroupFavoriteFragment extends BaseGroupFragment {
    @Override
    public void showRootFragment() {
        replaceFragment(new FavoriteFragment());
    }
}
