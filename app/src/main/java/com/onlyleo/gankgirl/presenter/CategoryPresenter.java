package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.ui.view.ICategoryView;

/**
 * Created by BBC on 2016/6/15 0015.
 */
public class CategoryPresenter extends BasePresenter<ICategoryView> {
    public CategoryPresenter(Activity context, ICategoryView view) {
        super(context, view);
    }

    @Override
    public void release() {

    }
}
