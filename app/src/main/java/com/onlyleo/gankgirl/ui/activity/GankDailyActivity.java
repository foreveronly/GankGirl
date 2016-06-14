package com.onlyleo.gankgirl.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.ShareElement;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.GankDailyPresenter;
import com.onlyleo.gankgirl.ui.adapter.GankDailyAdpter;
import com.onlyleo.gankgirl.ui.view.IGankDailyView;
import com.onlyleo.gankgirl.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class GankDailyActivity extends BaseActivity<GankDailyPresenter> implements IGankDailyView {

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.iv_head_girl)
    ImageView ivHeadGirl;

    @Bind(R.id.recycler_view_gank_daily)
    RecyclerView recyclerViewGankdaily;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Girl girl;
    private List<Gank> list;
    private GankDailyAdpter adapter;
    private Calendar calendar;

    @OnClick(R.id.fab)
    public void fabClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.iv_head_girl)
    void girlClick(View view) {
        Intent intent = new Intent(this, GirlActivity.class);
        intent.putExtra("girlData", girl);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, ivHeadGirl, getString(R.string.pretty_girl));
        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
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
    public void showGankList(List<Gank> gankList) {
        list.addAll(gankList);
        adapter.notifyDataSetChanged();
        fab.setClickable(true);
    }

    @Override
    public void init() {
        initToolbar(toolbar);
        getIntentData();
        initGankDaily();
    }

    public void getIntentData() {
        girl = (Girl) getIntent().getSerializableExtra("girlData");
        calendar = Calendar.getInstance();
        calendar.setTime(girl.publishedAt);
        presenter.loadData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void initGankDaily() {
        if (ShareElement.shareDrawable != null)
            ivHeadGirl.setImageDrawable(ShareElement.shareDrawable);
        else
            Glide.with(this)
                    .load(girl.url)
                    .crossFade()
                    .into(ivHeadGirl);
        ViewCompat.setTransitionName(ivHeadGirl, getString(R.string.pretty_girl));
        setTitle(DateUtil.toDateTimeStr(girl.publishedAt));
        list = new ArrayList<>();
        adapter = new GankDailyAdpter(this, list);
        recyclerViewGankdaily.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGankdaily.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGankdaily.setAdapter(adapter);
        fab.setClickable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }
}
