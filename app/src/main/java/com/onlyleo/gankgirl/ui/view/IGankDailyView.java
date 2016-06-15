package com.onlyleo.gankgirl.ui.view;

import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.ui.base.IBaseView;

import java.util.List;

public interface IGankDailyView extends IBaseView {

    void showErrorView();

    void showGankList(List<Gank> list);
}
