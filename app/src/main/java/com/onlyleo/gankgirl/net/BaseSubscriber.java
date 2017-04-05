package com.onlyleo.gankgirl.net;

import android.app.Activity;

import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.ToastUtils;

import rx.Subscriber;


public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Activity context;

    public BaseSubscriber(Activity context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!CommonTools.isNetworkAvailable(context)) {

            ToastUtils.showShort("当前网络不可用，请检查网络情况");
            // **一定要主动调用下面这一句**
            onCompleted();

        }
        // 显示进度条
    }

    @Override
    public void onCompleted() {
        //关闭等待进度条

    }

}
