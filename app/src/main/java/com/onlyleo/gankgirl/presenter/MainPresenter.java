package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.view.IMainView;

/**
 * Created by bbc on 16/1/26.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    @Override
    public void release() {
        subscription.unsubscribe();
    }

    public MainPresenter(Activity context, IMainView view) {
        super(context, view);
    }

}
