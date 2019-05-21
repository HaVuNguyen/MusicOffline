package com.imusic.callbacks;

import com.imusic.models.Song;

public interface IMusicCallBack {
    void onCurrentSong(Song song, int position);

    void onPlayOrPause(boolean isPlay);
}
