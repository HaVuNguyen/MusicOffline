package com.imusic.fragment.child.my_music;

import android.support.v4.app.Fragment;
import android.view.View;

import com.imusic.R;
import com.imusic.fragment.child.BaseFragment;
import com.imusic.fragment.child.my_music.albums.AlbumsFragment;
import com.imusic.fragment.child.my_music.artists.ArtistFragment;
import com.imusic.fragment.child.my_music.song.SongFragment;
import com.imusic.fragment.group.BaseGroupFragment;
import com.imusic.ultils.Constant;

public class MyMusicFragment extends BaseFragment {
    private View mTabHome, mTabAlbum, mTabArtist, mCurrentTab;
    private Fragment mCurrentFragment = new Fragment();
    boolean intent;

    public MyMusicFragment() {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my_music;
    }

    @Override
    protected void initComponents() {
        mTabHome = mView.findViewById(R.id.tab_my_music);
        mTabAlbum = mView.findViewById(R.id.tab_albums);
        mTabArtist = mView.findViewById(R.id.tab_artist);

        setTitle(getString(R.string.tv_song));
        mCurrentTab = mTabHome;
        mCurrentTab.setSelected(true);

        mCurrentFragment = new SongFragment();
        replaceFragment(mCurrentFragment);
        initNavigation();

        intent = getActivity().getIntent().getBooleanExtra(Constant.TYPE_ADD_SONG, false);
    }

    @Override
    protected void addListener() {
        if (intent) {
            hiddenNavRight();
            setTitle(getString(R.string.tv_add_song_to_playlist));
//            showNavLeft(R.drawable.ic_back, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getActivity().finish();
//                }
//            });
            showTitleNavRight("DONE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
            hiddenNavLeft();

        } else {
            setTitle(getString(R.string.tv_my_music));
            hiddenNavRight();
            hiddenTitleNavRight();
            showNavLeft(R.drawable.ic_menu, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assert getParentFragment() != null;
                    ((BaseGroupFragment) getParentFragment()).openMenu();
                }
            });
        }

        mTabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle(getString(R.string.tv_song));
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabHome;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new SongFragment();
                addFragment(mCurrentFragment);
            }
        });
        mTabAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle(getString(R.string.tv_albums));
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabAlbum;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new AlbumsFragment();
                addFragment(mCurrentFragment);
            }
        });
        mTabArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle(getString(R.string.tv_artist));
                if (mCurrentTab != null) {
                    mCurrentTab.setSelected(false);
                }
                mCurrentTab = mTabArtist;
                mCurrentTab.setSelected(true);
                mCurrentFragment = new ArtistFragment();
                addFragment(mCurrentFragment);
            }
        });
    }
}
