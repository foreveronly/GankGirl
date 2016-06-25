package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.model.GankData;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.net.GankRetrofit;
import com.onlyleo.gankgirl.ui.view.IGankDailyView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class GankDailyPresenter extends BasePresenter<IGankDailyView> {

    public GankDailyPresenter(Activity context, IGankDailyView view) {
        super(context, view);
    }

    @Override
    public void release() {
        if (subscription != null)
            subscription.unsubscribe();
        if (mView != null)
            mView = null;
    }

    /**
     * 加载每日数据
     *
     * @param year
     * @param month
     * @param day
     */
    public void loadData(int year, int month, int day) {
        subscription = GankRetrofit.getGuDongInstance().getGankData(year, month, day)
                .subscribeOn(Schedulers.io())
                .map(new Func1<GankData, List<Gank>>() {
                    @Override
                    public List<Gank> call(GankData gankData) {
                        return addAllResults(gankData.results);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Gank>>() {
                    @Override
                    public void call(List<Gank> gankList) {
                        mView.showGankList(gankList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showErrorView();
                    }
                });
    }

    public void loadVideo(String videoUrl) {

    }

    private List<Gank> addAllResults(GankData.Result results) {
        List<Gank> mGankList = new ArrayList<>();
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.前端List != null) mGankList.addAll(results.前端List);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) mGankList.addAll(0, results.休息视频List);
        return mGankList;
    }
}
