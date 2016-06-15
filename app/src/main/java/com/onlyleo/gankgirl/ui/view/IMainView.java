package com.onlyleo.gankgirl.ui.view;


import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.ui.base.IBaseView;

import java.util.List;

public interface IMainView extends IBaseView {
    void showProgress();

    void hideProgress();

    void showErrorView();

    void showNoMoreData();

    void showGirlList(List<Girl> girlList);
}
