package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.net.GuDong;
import com.onlyleo.gankgirl.net.MainRetrofit;
import com.onlyleo.gankgirl.view.IBaseView;

import rx.Subscription;


public abstract class BasePresenter<GV extends IBaseView> {
    protected Subscription subscription;
    protected GV mView;
    protected Activity mContext;
    public abstract void release();
    public static final GuDong mGuDong = MainRetrofit.getGuDongInstance();

    public BasePresenter(Activity context, GV view) {
        mContext = context;
        mView = view;
    }
}
