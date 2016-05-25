package com.onlyleo.gankgirl.view;


import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.model.entity.Soul;

import java.util.List;

public interface IMainView<T extends Soul> extends IBaseView{
    void showProgress();
    void hideProgress();
    void showErrorView();
    void showNoMoreData();
    void showGankList(List<Girl> meiziList);
}
