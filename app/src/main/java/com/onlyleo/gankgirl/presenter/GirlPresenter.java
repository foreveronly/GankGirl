package com.onlyleo.gankgirl.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.onlyleo.gankgirl.ui.view.IGirlView;
import com.onlyleo.gankgirl.utils.FileUtil;
import com.onlyleo.gankgirl.utils.ShareUtil;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by leoonly on 16/6/5.
 */
public class GirlPresenter extends BasePresenter<IGirlView> {

    public GirlPresenter(Activity context, IGirlView view) {
        super(context, view);
    }

    @Override
    public void release() {
        if (subscription != null)
            subscription.unsubscribe();
    }

    public void saveGirl(final Bitmap bitmap, final String title) {
        subscription = Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    subscriber.onError(new Exception("保存失败!"));
                } else {
                    subscriber.onNext(uri);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        mContext.sendBroadcast(scannerIntent);
                        mView.showSaveGirlResult("保存成功!");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showSaveGirlResult("保存失败!");
                    }
                });
    }

    public void shareGirl(final Bitmap bitmap, final String title) {
        Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    subscriber.onError(new Exception("保存失败!"));
                } else {
                    subscriber.onNext(uri);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        ShareUtil.shareImage(mContext, uri, "分享到");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("log", "some thing is wrong");
                        mView.showSaveGirlResult(throwable.getMessage());
                    }
                });
    }
}
