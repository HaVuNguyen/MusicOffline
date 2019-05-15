package com.imusic.fragment.child.playlist.addSong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.imusic.R;
import com.imusic.activities.BaseActivity;
import com.imusic.fragment.group.GroupMyMusicFragment;
import com.imusic.ultils.Constant;

import java.util.Objects;

public class AddSongToPlaylistActivity extends BaseActivity {

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, AddSongToPlaylistActivity.class);
        return intent;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_add_song_to_playlist;
    }

    @Override
    protected void initComponents() {

        boolean intent = getIntent().getBooleanExtra(Constant.TYPE_ADD_SONG, false);
        if (intent) {
            replaceFragment(new GroupMyMusicFragment());
        }
    }

    @Override
    protected void addListener() {

    }
}
