package com.onlyleo.gankgirl.ui.view;


import com.onlyleo.gankgirl.model.entity.Search;

import java.util.List;


public interface ISearchView extends IBaseView {
    void showGankList(List<Search> list);

    void showProgress();

    void hideProgress();

    void showErrorView();


    void showNoMoreData();
}
