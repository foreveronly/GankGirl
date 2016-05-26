package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.view.IBaseView;

import rx.Subscription;


public abstract class BasePresenter<GV extends IBaseView> {
    protected Subscription subscription;
    protected GV mView;
    protected Activity mContext;

    public abstract void release();

    public BasePresenter(Activity context, GV view) {
        mContext = context;
        mView = view;
    }

    public void init(){
        mView.init();
    }

    public abstract void loadData(int page);
}
