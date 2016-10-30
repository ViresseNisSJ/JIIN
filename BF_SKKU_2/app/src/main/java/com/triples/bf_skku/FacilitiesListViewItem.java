package com.triples.bf_skku;

/**
 * Created by user on 2016-02-29.
 */
import android.graphics.drawable.Drawable;

public class FacilitiesListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;


    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }

}