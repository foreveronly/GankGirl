package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.BuildConfig;
import com.onlyleo.gankgirl.model.ContentData;
import com.onlyleo.gankgirl.model.PrettyGirlData;
import com.onlyleo.gankgirl.model.entity.Content;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.net.GankRetrofit;
import com.onlyleo.gankgirl.ui.view.IMainView;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.Comparator;

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
//        if(mView!=null)
//            mView = null;
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
                GankRetrofit.getGuDongInstance().getContentData(PAGE_SIZE, page), new Func2<PrettyGirlData, ContentData, PrettyGirlData>() {
                    @Override
                    public PrettyGirlData call(PrettyGirlData prettyGirlData, ContentData contentData) {
                        return getGirlAndTitleAndDate(prettyGirlData, contentData);
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
                        if (BuildConfig.DEBUG)
                            Logger.e(throwable.getMessage());
                        mView.showErrorView();
                        mView.hideProgress();
                    }
                });

    }

    private PrettyGirlData getGirlAndTitleAndDate(PrettyGirlData girl, ContentData contentData) {
        //list按时间降序排序
        Collections.sort(girl.results, new Comparator<Girl>() {
            @Override
            public int compare(Girl lhs, Girl rhs) {
                return rhs.publishedAt.compareTo(lhs.publishedAt);
            }
        });
        Collections.sort(contentData.results, new Comparator<Content>() {
            @Override
            public int compare(Content lhs, Content rhs) {
                return rhs.publishedAt.compareTo(lhs.publishedAt);
            }
        });
        int size = Math.min(girl.results.size(), contentData.results.size());
        for (int i = 0; i < size; i++) {
            girl.results.get(i).desc = contentData.results.get(i).title;
        }
        return girl;
    }
}
