package com.imusic.fragment.child.recently;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.imusic.models.Recently;

import java.util.List;

public class RecentlyViewModel extends AndroidViewModel {
    private RecentlyRepository mRepository;

    public RecentlyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecentlyRepository(application);
    }

    public long insert(Recently recently) {
        return mRepository.insert(recently);
    }

    public LiveData<List<Recently>> getIdSong() {
        return mRepository.getIdSong();
    }
}
