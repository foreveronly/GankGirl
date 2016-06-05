package com.onlyleo.gankgirl.ui.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.ShareElement;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.GankDailyPresenter;
import com.onlyleo.gankgirl.ui.view.IGankDailyView;
import com.onlyleo.gankgirl.utils.Tools;

import butterknife.Bind;
import butterknife.OnClick;

public class GankDailyActivity extends BaseActivity<GankDailyPresenter> implements IGankDailyView {

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.iv_head_girl)
    ImageView ivHeadGirl;

    private Girl girl;

    @OnClick(R.id.fab)
    public void fabClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.iv_head_girl)
    void girlClick(View view){
        Intent intent = new Intent(this,GirlActivity.class);
        intent.putExtra("girlData",girl);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, ivHeadGirl,getString(R.string.pretty_girl));
        ActivityCompat.startActivity(this,intent,optionsCompat.toBundle());
    }
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank_daily;
    }

    @Override
    protected void initPresenter() {
        presenter = new GankDailyPresenter(this, this);
        presenter.init();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void init() {
        getIntentData();
        initGankDaily();
    }

    public void getIntentData() {
        girl = (Girl) getIntent().getSerializableExtra("girlData");
    }

    public void initGankDaily() {
        initToolbar();
        ivHeadGirl.setImageDrawable(ShareElement.shareDrawable);
        ViewCompat.setTransitionName(ivHeadGirl, getString(R.string.pretty_girl));
        setTitle(Tools.toDate(girl.publishedAt.getTime()),true);
    }
}
