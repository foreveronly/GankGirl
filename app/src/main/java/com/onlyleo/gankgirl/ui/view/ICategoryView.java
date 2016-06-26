package com.onlyleo.gankgirl.ui.view;

import com.onlyleo.gankgirl.model.entity.Gank;

import java.util.List;

/**
 * Created by BBC on 2016/6/15 0015.
 */
public interface ICategoryView extends IBaseView {
    void showProgress();

    void hideProgress();

    void showErrorView();

    void showNoMoreData();

    void showCategoryData(List<Gank> list);
}
