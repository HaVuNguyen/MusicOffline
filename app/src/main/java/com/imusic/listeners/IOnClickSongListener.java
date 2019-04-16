package com.imusic.listeners;

import com.imusic.models.Song;

import java.util.ArrayList;
import java.util.List;

public interface IOnClickSongListener {

    void onItemClickSong(ArrayList<Song> songs, int position);
}
