package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.model.PrettyGirlData;
import com.onlyleo.gankgirl.model.VideoData;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.net.GankRetrofit;
import com.onlyleo.gankgirl.ui.view.IMainView;
import com.onlyleo.gankgirl.utils.CommonTools;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<IMainView> {

    @Override
    public void release() {
        if (subscription != null)
            subscription.unsubscribe();
    }

    public MainPresenter(Activity context, IMainView view) {
        super(context, view);
    }

    /**
     * 加载首页数据
     *
     * @param page
     */
    public void loadData(int page) {
        subscription = Observable.zip(GankRetrofit.getGuDongInstance().getPrettyGirlData(PAGE_SIZE, page),
                GankRetrofit.getGuDongInstance().getVideoData(PAGE_SIZE, page), new Func2<PrettyGirlData, VideoData, PrettyGirlData>() {
                    @Override
                    public PrettyGirlData call(PrettyGirlData prettyGirlData, VideoData videoData) {
                        return getGirlAndTitleAndDate(prettyGirlData, videoData);
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
                        if (prettyGirlData.results.size() == 0) {
                            mView.showNoMoreData();
                        } else {
                            mView.showGirlList(prettyGirlData.results);
                        }
                        mView.hideProgress();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showErrorView();
                        mView.hideProgress();
                    }
                });
    }


    private PrettyGirlData getGirlAndTitleAndDate(PrettyGirlData girl, VideoData videoData) {
        for(Girl g:girl.results){
            g.desc += getFirstVideoDesc(g.publishedAt,videoData.results);
        }
        return girl;
    }

    private int mLastVideoIndex = 0;

    private String getFirstVideoDesc(Date publishedAt, List<Gank> results) {
        String videoDesc = "";
        for (int i = mLastVideoIndex; i < results.size(); i++) {
            Gank video = results.get(i);
            if (video.publishedAt == null) video.publishedAt = video.createdAt;
            if (CommonTools.isTheSameDay(publishedAt, video.publishedAt)) {
                videoDesc = video.desc;
                mLastVideoIndex = i;
                break;
            }
        }
        return videoDesc;
    }
}
