package com.imusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imusic.activities.MainActivity;

public abstract class BaseFragment extends Fragment {
    protected View mView;
    protected int mViewId;
    protected Context mContext;

    protected abstract int initLayout();

    protected abstract void initComponents();

    protected abstract void addListener();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = initLayout();
        if (layoutId != 0) {
            mViewId = layoutId;
        }
        mView = LayoutInflater.from(getActivity()).inflate(mViewId, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        addListener();
    }

    public void setTitleToActivity(String title){
        if(mContext instanceof MainActivity) {
            ((MainActivity) mContext).setTitle(title);
        }
    }

    public void onBackToPreFragment(){
        if(getParentFragment() != null){
            ((BaseGroupFragment)getParentFragment()).onBackPressed();
        }
    }
}
