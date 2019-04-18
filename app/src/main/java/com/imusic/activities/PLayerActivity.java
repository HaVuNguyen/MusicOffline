package com.imusic.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.imusic.R;

import java.util.Objects;

public class PLayerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImvPlay, mImvNext, mImvPre,mImvBack,mImvMenu;
    private TextView mTvTitlePlaying, mTvCurrentDuration, mTvDuration, mTvTitle;
    private Handler mHandler;
    private SeekBar mSeekBar;

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, PLayerActivity.class);
        return intent;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        mImvBack = findViewById(R.id.imv_back);
        mImvMenu = findViewById(R.id.imv_menu);
        mTvTitle = findViewById(R.id.tv_title);
        mImvPlay = findViewById(R.id.imv_play);
        mImvPre = findViewById(R.id.imv_pre);
        mImvNext = findViewById(R.id.imv_next);
        mTvTitlePlaying = findViewById(R.id.tv_title_playing);
        mTvCurrentDuration = findViewById(R.id.tv_current_duration);
        mTvDuration = findViewById(R.id.tv_duration);
        mSeekBar = findViewById(R.id.sb);
        mHandler = new Handler();
    }

    private void initListeners() {
        mTvTitle.setText(R.string.tv_playing);
        mImvPlay.setOnClickListener(this);
        mImvNext.setOnClickListener(this);
        mImvPre.setOnClickListener(this);
        mImvBack.setOnClickListener(this);
        mImvMenu.setVisibility(View.GONE);
        mSeekBar.setOnSeekBarChangeListener(mListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_back:
                finish();
                break;
            case R.id.imv_play:
                break;
            case R.id.imv_pre:
                break;
            case R.id.imv_next:
                break;
        }
    }

    private SeekBar.OnSeekBarChangeListener mListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
