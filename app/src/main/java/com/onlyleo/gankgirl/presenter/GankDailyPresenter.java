package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.ui.view.IBaseView;

/**
 * Created by leoonly on 16/6/2.
 */
public class GankDailyPresenter extends BasePresenter {

    public GankDailyPresenter(Activity context, IBaseView view) {
        super(context, view);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }

    @Override
    public void loadData(int page) {

    }
}
