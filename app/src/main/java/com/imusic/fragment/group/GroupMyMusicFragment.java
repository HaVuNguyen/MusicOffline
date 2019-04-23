package com.imusic.fragment.group;

import com.imusic.fragment.child.my_music.MyMusicFragment;

public class GroupMyMusicFragment extends BaseGroupFragment {
    @Override
    public void showRootFragment() {
        replaceFragment(new MyMusicFragment());
    }
}
