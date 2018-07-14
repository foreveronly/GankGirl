package com.onlyleo.gankgirl.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.onlyleo.gankgirl.GlobalConfig;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.presenter.WebPresenter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.IWebView;
import com.onlyleo.gankgirl.utils.TipsUtil;
import com.onlyleo.gankgirl.widget.LoveVideoView;

import butterknife.BindView;

public class WebActivity extends BaseActivity<WebPresenter> implements IWebView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    NumberProgressBar progressbar;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.contentView)
    LinearLayout contentView;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.web_video)
    LoveVideoView webVideo;
    private Gank gank;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    public static void loadWebViewActivity(Context from, Gank gank) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra(GlobalConfig.GANK, gank);
        from.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        presenter = new WebPresenter(this, this);
        presenter.init();
    }

    @Override
    public void showProgressBar(int progress) {
        if (progressbar == null) return;
        progressbar.setProgress(progress);
        if (progress == 100) {
            progressbar.setVisibility(View.GONE);
        } else {
            progressbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setWebTitle(String title) {
        setTitle(title);
    }

    @Override
    public void openFailed() {
        TipsUtil.showSnackTip(webView, "打开失败");
    }

    @Override
    public void init() {
        initToolbar(toolbar,false);
        gank = getIntent().getParcelableExtra(GlobalConfig.GANK);
        setTitle(gank.desc);
        if (("休息视频").equals(gank.type)) {
            webVideo.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            appBar.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                appBar.setElevation(0);
            presenter.loadWebVideo(webVideo, gank.url);
        } else {
            webView.setVisibility(View.VISIBLE);
            presenter.settingOfWebView(webView, gank.url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.refresh(webView);
                break;
            case R.id.action_copy_url:
                presenter.copyUrl(webView.getUrl());
                break;
            case R.id.action_open_in_browser:
                presenter.openInBrowser(webView.getUrl());
                break;
            case R.id.action_share_gank:
                presenter.moreOperation(gank);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            contentView.removeView(webView);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        if (webVideo != null) {
            webVideo.resumeTimers();
            webVideo.onResume();
        }
        presenter.release();
    }

    @Override
    protected void onPause() {
        if (webView != null) webView.onPause();
        if (webVideo != null) {
            webVideo.onPause();
            webVideo.pauseTimers();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (webView != null) webView.onResume();
        if (webVideo != null) {
            webVideo.resumeTimers();
            webVideo.onResume();
        }
        super.onResume();
    }

}
