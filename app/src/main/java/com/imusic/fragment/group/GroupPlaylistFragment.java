package com.imusic.fragment.group;

import com.imusic.fragment.child.playlist.PlaylistFragment;

public class GroupPlaylistFragment extends BaseGroupFragment {
    @Override
    public void showRootFragment() {
        replaceFragment(new PlaylistFragment());
    }
}
