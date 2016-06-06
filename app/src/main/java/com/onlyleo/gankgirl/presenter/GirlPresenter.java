package com.onlyleo.gankgirl.presenter;

import android.app.Activity;

import com.onlyleo.gankgirl.ui.view.IGirlView;

/**
 * Created by leoonly on 16/6/5.
 */
public class GirlPresenter extends BasePresenter<IGirlView> {

    public GirlPresenter(Activity context, IGirlView view) {
        super(context, view);
    }

    @Override
    public void release() {
        subscription.unsubscribe();
    }

}
