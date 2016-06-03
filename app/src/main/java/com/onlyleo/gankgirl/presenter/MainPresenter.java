package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.model.PrettyGirlData;
import com.onlyleo.gankgirl.model.VideoData;
import com.onlyleo.gankgirl.net.MainRetrofit;
import com.onlyleo.gankgirl.utils.ToastUtils;
import com.onlyleo.gankgirl.ui.view.IMainView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

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

    @Override
    public void loadData(int page) {
        subscription = Observable.zip(MainRetrofit.getGuDongInstance().getPrettyGirlData(10, page),
                MainRetrofit.getGuDongInstance().getVideoData(20, page), new Func2<PrettyGirlData, VideoData, PrettyGirlData>() {
                    @Override
                    public PrettyGirlData call(PrettyGirlData prettyGirlData, VideoData videoData) {
                        return getGirlAndTitleAndDate(prettyGirlData,videoData);
                    }
                }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PrettyGirlData>() {
                    @Override
                    public void call(PrettyGirlData prettyGirlData) {
                        if (prettyGirlData.results.size() == 0){
                            mView.showNoMoreData();
                        }else {
                            mView.showGirlList(prettyGirlData.results);
                        }
                        mView.hideProgress();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShort(mContext,throwable.toString());
                        mView.showErrorView();
                        mView.hideProgress();
                    }
                });
    }


    private PrettyGirlData getGirlAndTitleAndDate(PrettyGirlData girl, VideoData video){
        int size = Math.min(girl.results.size(),video.results.size());
        for (int i = 0; i < size; i++) {
            girl.results.get(i).desc = video.results.get(i).desc;
        }
        return girl;
    }

}
