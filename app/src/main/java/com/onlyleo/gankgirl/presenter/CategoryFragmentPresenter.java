package com.onlyleo.gankgirl.presenter;

import android.content.Context;

import com.onlyleo.gankgirl.model.CategoryData;
import com.onlyleo.gankgirl.net.GankRetrofit;
import com.onlyleo.gankgirl.ui.view.ICategoryView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CategoryFragmentPresenter extends BasePresenter<ICategoryView> {
    public CategoryFragmentPresenter(Context context, ICategoryView view) {
        super(context, view);
    }

    @Override
    public void release() {
        if (subscription != null)
            subscription.unsubscribe();
//        if (mView != null)
//            mView = null;
    }

    public void loadData(String category, int page) {
        subscription = GankRetrofit.getGuDongInstance().getCategoryData(category, PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CategoryData>() {
                    @Override
                    public void call(CategoryData videoData) {
                        if (videoData.results.size() == 0) {
                            mView.showNoMoreData();
                        } else {
                            mView.showCategoryData(videoData.results);
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

}
