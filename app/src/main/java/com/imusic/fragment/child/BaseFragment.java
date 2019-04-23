package com.imusic.fragment.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imusic.R;
import com.imusic.activities.MainActivity;
import com.imusic.fragment.group.BaseGroupFragment;

public abstract class BaseFragment extends Fragment {
    public static final int MAIN_FRAME_ID = R.id.frm_main;
    protected View mView;
    protected int mViewId;
    protected Context mContext;
    protected DrawerLayout mDrawerLayout;
    protected View mLayoutSlideMenu;
    public ImageView mImvNavRight;
    public ImageView mImvNavLeft;
    private TextView mTvTitle;
    private ImageView mImvMenu, mImvBack;

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

    public void initNavigation() {
        mImvBack = getView().findViewById(R.id.imv_left);
        mImvMenu = getView().findViewById(R.id.imv_right);
        mDrawerLayout = getView().findViewById(R.id.drawer_layout);
        mLayoutSlideMenu = getView().findViewById(R.id.layout_left_menu);

        mTvTitle = getView().findViewById(R.id.tv_title);
        if (mTvTitle != null) {
            mImvNavLeft = getView().findViewById(R.id.imv_left);
            mImvNavRight = getView().findViewById(R.id.imv_right);
            mTvTitle.setSelected(true);
        }
    }

    public void setTitleToActivity(String title) {
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).setTitle(title);
        }
    }

    public void onBackToPreFragment() {
        if (getParentFragment() != null) {
            ((BaseGroupFragment) getParentFragment()).onBackPressed();
        }
    }

    public void setTitle(String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
    }

    public void showNavigation(ImageView imageView, int resId, View.OnClickListener listener) {
        if (imageView != null) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(resId);
            if (listener != null) {
                imageView.setOnClickListener(listener);
            }
        }
    }

    public void showNavLeft(int resId, View.OnClickListener listener) {
        showNavigation(mImvNavLeft, resId, listener);
    }

    public void showNavRight(int resId, View.OnClickListener listener) {
        showNavigation(mImvNavRight, resId, listener);
    }

    public void hiddenNavRight() {
        mImvNavRight.setVisibility(View.GONE);
    }

    public void hiddenNavLeft() {
        mImvNavLeft.setVisibility(View.GONE);
    }

    public void replaceFragment(Fragment fragment) {
        while (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStackImmediate();
        }
        getChildFragmentManager().beginTransaction()
                .replace(MAIN_FRAME_ID, fragment)
                .commit();
    }

    public void addFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.trans_right_to_left_in, R.anim.trans_right_to_left_out,
                        R.anim.trans_left_to_right_in, R.anim.trans_left_to_right_out)
                .replace(MAIN_FRAME_ID, fragment)
                .addToBackStack(null)
                .commit();
    }
}
