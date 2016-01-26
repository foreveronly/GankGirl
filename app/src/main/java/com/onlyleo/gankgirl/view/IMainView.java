package com.onlyleo.gankgirl.view;


import com.onlyleo.gankgirl.model.entity.Soul;

import java.util.List;

public interface IMainView<T extends Soul> extends IBaseView{
    /**
     * load data successfully
     * @param data
     */
    void fillData(List<T> data);

    /**
     * append data to history list(load more)
     * @param data
     */
    void appendMoreDataToView(List<T> data);

    /**
     * no more data for show and this condition is hard to appear,it need you scroll main view long time
     * I think it has no body do it like this ,even though，I deal this condition also, In case someone does it.
     */
    void hasNoMoreData();

    /**
     * show change log info in a dialog
     * @param assetFileName the name of local html file like "changelog.html"
     */
    void showChangeLogInfo(String assetFileName);
}
