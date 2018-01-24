package com.awok.model;

/**
 * Created by dilip on 25/1/18.
 */

public class NavigationOption {

    private String title;
    private int iconResource;
    private boolean isSelected;


    public String getTitle() {
        return title;
    }

    public void setTitle(String titleName) {
        this.title = titleName;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
