package com.imusic.callbacks;

import com.imusic.models.Song;

import java.util.ArrayList;

public interface IMusicPlayerCallback {

    void onSongPlayer(ArrayList<Song> songs, int position,Song song);

    void onPLay(boolean isPlay);
}
