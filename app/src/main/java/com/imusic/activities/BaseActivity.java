package com.imusic.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.fragment.favorite.FavoriteFragment;
import com.imusic.fragment.playlist.PlaylistFragment;
import com.imusic.fragment.recently.RecentlyFragment;
import com.imusic.fragment.song.SongFragment;
import com.imusic.menu.MenuAdapter;
import com.imusic.menu.MenuModel;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements DrawerLayout.DrawerListener {
    private boolean isOnPause = false;
    public ImageView mImvNavRight;
    private ImageView mImvNavLeft;
    private TextView mTvTitle;
    protected Fragment mFragment;
    protected ProgressDialog mProgressDialog;
    protected DrawerLayout mDrawerLayout;
    private ImageView mImvMenu, mImvBack;
    private View mLayoutSlideMenu;
    private RecyclerView mRcMenu;
    private MenuAdapter mMenuAdapter;
    private List<MenuModel> mMenuLists = new ArrayList<>();
    private Context mContext;
    private View mViewTab;
    private View mTabHome, mCurrentTab;


    protected abstract int initLayout();

    protected abstract void initComponents();

    protected abstract void addListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = initLayout();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.tv_coming));
        mViewTab = findViewById(R.id.view_tab);
        mTabHome = findViewById(R.id.tab_my_music);

        initNavigation();
        initComponents();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPause = true;
    }

    public void initRightMenu() {
        if (mDrawerLayout != null) {
            mDrawerLayout.addDrawerListener(this);
            loadMenu();
        }
    }

    private void loadMenu() {
        mRcMenu = findViewById(R.id.recyclerview_menu);
        mRcMenu.setLayoutManager(new LinearLayoutManager(this));
        mMenuLists.clear();
        mMenuLists.add(new MenuModel(R.drawable.ic_home, getString(R.string.tv_home), MenuModel.MENU_TYPE.MENU_HOME));
        mMenuLists.add(new MenuModel(R.drawable.ic_recently, getString(R.string.tv_recently_songs), MenuModel.MENU_TYPE.MENU_RECENTLY));
        mMenuLists.add(new MenuModel(R.drawable.ic_my_playlist, getString(R.string.tv_playlist), MenuModel.MENU_TYPE.MENU_PLAYLIST));
        mMenuLists.add(new MenuModel(R.drawable.ic_my_favorite, getString(R.string.tv_favorite), MenuModel.MENU_TYPE.MENU_FAVORITE));
        mMenuLists.add(new MenuModel(R.drawable.ic_about, getString(R.string.tv_about), MenuModel.MENU_TYPE.MENU_ABOUT));

        mMenuAdapter = new MenuAdapter(this, mMenuLists);
        mRcMenu.setAdapter(mMenuAdapter);
        mMenuAdapter.setIOnClickItemListener(new MenuAdapter.IOnClickItemListener() {
            @Override
            public void onItemClick(MenuModel.MENU_TYPE menuType) {
                Intent i = new Intent();
                switch (menuType) {
                    case MENU_HOME:
                        mFragment = new SongFragment();
                        setNewPage(mFragment);
                        setTitle(getString(R.string.tv_my_music));
                        mCurrentTab = mTabHome;
                        mCurrentTab.setSelected(true);
                        replaceFragment(mFragment);
                        mViewTab.setVisibility(View.VISIBLE);
                        break;
                    case MENU_RECENTLY:
                        setTitle(getString(R.string.tv_recently_songs));
                        addFragment(new RecentlyFragment());
                        break;
                    case MENU_PLAYLIST:
                        setTitle(getString(R.string.tv_playlist));
                        mFragment = new PlaylistFragment();
                        addFragment(mFragment);
                        break;
                    case MENU_FAVORITE:
                        setTitle(getString(R.string.tv_favorite));
                        addFragment(new FavoriteFragment());
                        break;
                    case MENU_ABOUT:
                        Toast.makeText(BaseActivity.this, getResources().getString(R.string.tv_coming), Toast.LENGTH_LONG).show();
                        break;
                }
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawer(mLayoutSlideMenu);
                }
            }
        });
    }

    public void toggleMenuRight() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(mLayoutSlideMenu)) {
                mDrawerLayout.closeDrawer(mLayoutSlideMenu);
            } else {
                mDrawerLayout.openDrawer(mLayoutSlideMenu);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("CutPasteId")
    public void initNavigation() {
        mImvBack = findViewById(R.id.imv_back);
        mImvMenu = findViewById(R.id.imv_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mLayoutSlideMenu = findViewById(R.id.layout_left_menu);

        mTvTitle = findViewById(R.id.tv_title);
        if (mTvTitle != null) {
            mImvNavLeft = findViewById(R.id.imv_back);
            mImvNavRight = findViewById(R.id.imv_menu);
            mTvTitle.setSelected(true);
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

    public void setTitle(String title) {
        if (mTvTitle != null) {
            mTvTitle.setText(title);
        }
    }

    public void setNewPage(Fragment fragment) {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_main, fragment, "currentFragment");
            transaction.commitAllowingStateLoss();
            if (mFragment != null)
                transaction.remove(mFragment);
            mFragment = fragment;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.trans_right_to_left_in, R.anim.trans_right_to_left_out,
                        R.anim.trans_left_to_right_in, R.anim.trans_left_to_right_out)
                .replace(R.id.frame_main, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStackImmediate(R.id.frame_main, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main, fragment)
                .commit();
    }

    @Override
    public void finish() {
//        hideKeyBoard();
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void startActivity(Intent intent) {
        // TODO Auto-generated method stub
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

//    public void toast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//
//    public void toast(int messageId) {
//        Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show();
//    }
//
//    public void showLoading(boolean isShow) {
//        if (isShow) {
//            mProgressDialog.show();
//        } else {
//            if (mProgressDialog.isShowing()) {
//                mProgressDialog.dismiss();
//            }
//        }
//    }
//    void complain(String message) {
//        //alert("Error: " + message);
//    }
//
//    void alert(String message) {
//        AlertDialog.Builder bld = new AlertDialog.Builder(this);
//        bld.setMessage(message);
//        bld.setNeutralButton("OK", null);
//        bld.create().show();
//    }
//
//    public void hideKeyBoard() {
//        try {
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        InputMethodManager inputManager = (InputMethodManager) BaseActivity.this
//                                .getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputManager.hideSoftInputFromWindow(
//                                BaseActivity.this.getCurrentFocus().getApplicationWindowToken(),
//                                InputMethodManager.HIDE_NOT_ALWAYS);
//                    } catch (IllegalStateException e) {
//                    } catch (Exception e) {
//                    }
//                }
//            });
//
//        } catch (IllegalStateException e) {
//            // TODO: handle exception
//        } catch (Exception e) {
//        }
//    }
//    public void setPaddingNavRight(int padding) {
//        mImvNavRight.setPadding(padding, padding, padding, padding);
//    }
}
