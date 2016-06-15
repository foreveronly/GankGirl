package com.onlyleo.gankgirl.ui.view;

import com.onlyleo.gankgirl.ui.base.IBaseView;

public interface IWebView extends IBaseView {

    void showProgressBar(int progress);

    void setWebTitle(String title);

    void openFailed();
}
