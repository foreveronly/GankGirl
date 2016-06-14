package com.onlyleo.gankgirl.ui.view;

import com.onlyleo.gankgirl.model.entity.Gank;

import java.util.List;

/**
 * Created by leoonly on 16/6/2.
 */
public interface IGankDailyView extends IBaseView {

    void showProgress();
    void hideProgress();
    void showErrorView();
    void showGankList(List<Gank> list);
}
