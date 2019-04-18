package com.imusic.menu;

public class MenuModel {
    public enum MENU_TYPE{MENU_PLAYLIST, MENU_FAVORITE, MENU_RECENTLY, MENU_ABOUT}

    private String mNameMenu;
    private MENU_TYPE type;

    public MenuModel(String mNameMenu, MENU_TYPE type) {
        this.mNameMenu = mNameMenu;
        this.type = type;
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
