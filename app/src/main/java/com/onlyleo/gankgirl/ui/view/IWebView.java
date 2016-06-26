package com.onlyleo.gankgirl.ui.view;

public interface IWebView extends IBaseView {

    void showProgressBar(int progress);

    void setWebTitle(String title);

    void openFailed();
}
