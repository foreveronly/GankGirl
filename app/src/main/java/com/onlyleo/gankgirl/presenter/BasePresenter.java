package com.onlyleo.gankgirl.presenter;

import android.content.Context;

import com.onlyleo.gankgirl.ui.view.IBaseView;

import rx.Subscription;


public abstract class BasePresenter<GV extends IBaseView> {
    protected Subscription subscription;
    protected GV mView;
    protected Context mContext;
    protected final static int PAGE_SIZE = 20;

    public abstract void release();

    public BasePresenter(Context context, GV view) {
        mContext = context;
        mView = view;
    }

    public void init() {
        mView.init();
    }

}
