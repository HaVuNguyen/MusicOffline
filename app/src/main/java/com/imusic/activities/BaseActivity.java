package com.imusic.activities;

import android.app.AlertDialog;
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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.menu.MenuAdapter;
import com.imusic.menu.MenuModel;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements DrawerLayout.DrawerListener {
    private boolean isOnPause = false;
    public ImageView mImvNavRight, mImvBack, mImvNavLeft;
    private TextView mTvTitle;
    protected Fragment mFragment;
    protected ProgressDialog mProgressDialog;
    protected DrawerLayout mDrawerLayout;
    private ImageView mImvMenu;
    private View mLayoutSlideMenu;
    private RecyclerView mRcMenu;
    private MenuAdapter mMenuAdapter;
    private Context mContext;
    private List<MenuModel> mMenuLists = new ArrayList<>();


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
        initComponents();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnPause = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPause = true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int messageId) {
        Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show();
    }

    public void showLoading(boolean isShow) {
        if (isShow) {
            mProgressDialog.show();
        } else {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void finish() {
        hideKeyBoard();
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

    public void hideKeyBoard() {
        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        InputMethodManager inputManager = (InputMethodManager) BaseActivity.this
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(
                                BaseActivity.this.getCurrentFocus().getApplicationWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (IllegalStateException e) {
                    } catch (Exception e) {
                    }
                }
            });

        } catch (IllegalStateException e) {
            // TODO: handle exception
        } catch (Exception e) {
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

    void complain(String message) {
        //alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }
}
