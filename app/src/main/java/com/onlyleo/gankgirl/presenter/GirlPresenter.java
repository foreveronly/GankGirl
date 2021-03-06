package com.onlyleo.gankgirl.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.onlyleo.gankgirl.net.BaseSubscriber;
import com.onlyleo.gankgirl.ui.view.IGirlView;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.FileUtil;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GirlPresenter extends BasePresenter<IGirlView> {

    public GirlPresenter(Activity context, IGirlView view) {
        super(context, view);
    }

    @Override
    public void release() {
        if (subscription != null)
            subscription.unsubscribe();
    }

    /**
     * 保存妹子图片
     *
     * @param bitmap
     * @param title
     */
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
        })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Uri>((Activity) mContext) {
                    @Override
                    public void onNext(Uri uri) {
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        mContext.sendBroadcast(scannerIntent);
                        mView.showSaveGirlResult("保存成功!");
                        bitmap.recycle();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showSaveGirlResult("保存失败!");
                        bitmap.recycle();
                    }
                });
    }

    /**
     * 分享妹子图片
     *
     * @param bitmap
     * @param title
     */
    public void shareGirl(final Bitmap bitmap, final String title) {
        subscription = Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                Uri uri = FileUtil.saveBitmapToSDCard(bitmap, title);
                if (uri == null) {
                    subscriber.onError(new Exception("分享失败!"));
                    bitmap.recycle();
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
                        CommonTools.shareImage(mContext, uri, "分享到");
                        bitmap.recycle();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("log", "some thing is wrong");
                        mView.showSaveGirlResult(throwable.getMessage());
                        bitmap.recycle();
                    }
                });
    }
}
