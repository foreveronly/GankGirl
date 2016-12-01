package com.onlyleo.gankgirl.presenter;

import android.app.Activity;
import android.content.Context;

import com.onlyleo.gankgirl.model.SearchData;
import com.onlyleo.gankgirl.net.BaseSubscriber;
import com.onlyleo.gankgirl.net.GankRetrofit;
import com.onlyleo.gankgirl.ui.view.ISearchView;
import com.orhanobut.logger.Logger;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SearchPresenter extends BasePresenter<ISearchView> {

    public SearchPresenter(Context context, ISearchView view) {
        super(context, view);
    }

    @Override
    public void release() {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    /**
     * 搜索
     *
     * @param query
     * @param category
     * @param page
     */
    public void search(String query, final String category, int page) {
        subscription = GankRetrofit.getGankApi().getSearchData(query, category, PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                })
                .subscribe(new BaseSubscriber<SearchData>((Activity) mContext) {
                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }
                    @Override
                    public void onNext(SearchData searchData) {
                        if (searchData.results.size() == 0) {
                            mView.showNoMoreData();
                        } else {
                            mView.showGankList(searchData.results);
                        }
                        mView.hideProgress();
                    }
                });
    }
}
