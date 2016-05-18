package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.net.GuDong;
import com.onlyleo.gankgirl.net.MainRetrofit;
import com.onlyleo.gankgirl.view.IBaseView;


public class BasePresenter<GV extends IBaseView> {

    protected GV mView;
    /**
     * TODO 这里用是否用Activity待考证
     */
    protected Activity mContext;

    public static final GuDong mGuDong = MainRetrofit.getGuDongInstance();

    public BasePresenter(Activity context, GV view) {
        mContext = context;
        mView = view;
    }
}
