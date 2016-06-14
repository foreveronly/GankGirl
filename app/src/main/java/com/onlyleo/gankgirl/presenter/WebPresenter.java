package com.onlyleo.gankgirl.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.ui.view.IWebView;
import com.onlyleo.gankgirl.utils.AndroidUtil;
import com.onlyleo.gankgirl.utils.ShareUtil;

/**
 * Created by BBC on 2016/6/14 0014.
 */
public class WebPresenter extends BasePresenter<IWebView> {

    public WebPresenter(Activity context, IWebView view) {
        super(context, view);
    }

    @Override
    public void release() {

    }

    public void settingOfWebView(WebView webView, String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new GankClient());
        webView.loadUrl(url);
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mView.showProgressBar(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mView.setWebTitle(title);
        }
    }

    private class GankClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }
    public void refresh(WebView webView) {
        webView.reload();
    }

    public void copyUrl(String text) {
        AndroidUtil.copyToClipBoard(mContext.getApplicationContext(), text,"复制成功");
    }

    public void openInBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        } else {
            mView.openFailed();
        }
    }

    public void moreOperation(Gank gank) {
        if (gank != null)
            ShareUtil.shareGank(mContext, gank);
    }
}
