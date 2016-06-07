package com.onlyleo.gankgirl.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.GirlPresenter;
import com.onlyleo.gankgirl.ui.view.IGirlView;
import com.onlyleo.gankgirl.utils.CalendarUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class GirlActivity extends BaseActivity<GirlPresenter> implements IGirlView {


    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.iv_girl)
    ImageView ivGirl;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Girl girl;
    @OnClick(R.id.fab)
    public void fabClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_girl;
    }

    @Override
    protected void initPresenter() {
        presenter = new GirlPresenter(this, this);
        presenter.init();
    }

    @Override
    public void init() {
        setSupportActionBar(toolbar);
        getIntentData();
        initGirl();
    }

    public void initGirl() {
        Glide.with(this)
                .load(girl.url)
                .crossFade()
                .into(ivGirl);
        ViewCompat.setTransitionName(ivGirl, getString(R.string.pretty_girl));
        setTitle(CalendarUtil.toDateTimeStr(girl.publishedAt));
    }
    public void getIntentData(){
        girl = (Girl) getIntent().getSerializableExtra("girlData");
    }
}
