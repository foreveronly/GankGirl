package com.onlyleo.gankgirl.presenter;

import android.content.Context;

import com.onlyleo.gankgirl.ui.view.IBaseView;

import rx.Subscription;


public abstract class BasePresenter<GV extends IBaseView> {
    Subscription subscription;
    GV mView;
    Context mContext;
    final static int PAGE_SIZE = 20;

    public abstract void release();

    public BasePresenter(Context context, GV view) {
        mContext = context;
        mView = view;
    }

    public void init() {
        mView.init();
    }

}
