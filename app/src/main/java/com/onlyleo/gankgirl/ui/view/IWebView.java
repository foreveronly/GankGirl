package com.onlyleo.gankgirl.ui.view;

/**
 * Created by BBC on 2016/6/14 0014.
 */
public interface IWebView extends IBaseView {

    void showProgressBar(int progress);

    void setWebTitle(String title);

    void openFailed();
}
