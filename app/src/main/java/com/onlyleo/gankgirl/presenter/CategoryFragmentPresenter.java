package com.onlyleo.gankgirl.presenter;

import android.app.Activity;
import android.content.Context;

import com.onlyleo.gankgirl.model.CategoryData;
import com.onlyleo.gankgirl.net.BaseSubscriber;
import com.onlyleo.gankgirl.net.GankRetrofit;
import com.onlyleo.gankgirl.ui.view.ICategoryView;
import com.orhanobut.logger.Logger;

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
        subscription = GankRetrofit.getGankApi().getCategoryData(category, PAGE_SIZE, page)
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
                .subscribe(new BaseSubscriber<CategoryData>((Activity) mContext) {
                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onNext(CategoryData categoryData) {
                        if (categoryData.results.size() == 0) {
                            mView.showNoMoreData();
                        } else {
                            mView.showCategoryData(categoryData.results);
                        }
                        mView.hideProgress();
                    }
                });
    }

}
