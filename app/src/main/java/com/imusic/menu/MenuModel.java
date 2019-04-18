package com.imusic.menu;

public class MenuModel {
    public enum MENU_TYPE {MENU_PLAYLIST, MENU_FAVORITE, MENU_RECENTLY, MENU_ABOUT,MENU_HOME}

    private String mNameMenu;
    private int mImageMenu;
    private MENU_TYPE type;

    public MenuModel(int imageMenu, String mNameMenu, MENU_TYPE type) {
        this.mImageMenu = imageMenu;
        this.mNameMenu = mNameMenu;
        this.type = type;
    }

    public int getImageMenu() {
        return mImageMenu;
    }

    public void setImageMenu(int imageMenu) {
        mImageMenu = imageMenu;
    }

    public String getmNameMenu() {
        return mNameMenu;
    }

    public void setmNameMenu(String mNameMenu) {
        this.mNameMenu = mNameMenu;
    }

    public MENU_TYPE getType() {
        return type;
    }
}
