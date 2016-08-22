package com.onlyleo.gankgirl.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.onlyleo.gankgirl.GlobalConfig;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.db.GankDBCURD;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.net.GankRetrofit;
import com.onlyleo.gankgirl.presenter.GankDailyPresenter;
import com.onlyleo.gankgirl.ui.adapter.GankDailyAdpter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.IGankDailyView;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.TipsUtil;
import com.onlyleo.gankgirl.widget.CompatToolbar;
import com.onlyleo.gankgirl.widget.VideoImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class GankDailyActivity extends BaseActivity<GankDailyPresenter> implements IGankDailyView {

    @Bind(R.id.recycler_view_gank_daily)
    RecyclerView recyclerViewGankdaily;
    @Bind(R.id.toolbar)
    CompatToolbar toolbar;
    @Bind(R.id.gank_daily_iv)
    VideoImageView gankDailyIv;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private Girl girl;
    private List<Gank> list;
    private GankDailyAdpter adapter;
    private Calendar calendar;
    private GankDBCURD dbcurd;

    @OnClick(R.id.fab)
    void fabClick() {
        if (!CommonTools.isWIFIConnected(this.getApplicationContext())) {
            TipsUtil.showTipWithAction(fab, "你使用的不是wifi网络，要继续吗？", "继续", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.size() > 0 && list.get(0).type.equals("休息视频")) {
                        Intent intent = new Intent(GankDailyActivity.this, WebActivity.class);
                        intent.putExtra(GlobalConfig.GANK, list.get(0));
                        startActivity(intent);
                    } else {
                        TipsUtil.showSnackTip(fab, getString(R.string.open_url_failed));
                    }
                }
            }, Snackbar.LENGTH_LONG);
        } else {
            if (list.size() > 0 && list.get(0).type.equals("休息视频")) {
                Intent intent = new Intent(GankDailyActivity.this, WebActivity.class);
                intent.putExtra(GlobalConfig.GANK, list.get(0));
                startActivity(intent);
            } else {
                TipsUtil.showSnackTip(fab, getString(R.string.open_url_failed));
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gank_daily;
    }

    @Override
    protected void initPresenter() {
        presenter = new GankDailyPresenter(this, this);
        presenter.init();
    }

    @Override
    public void showErrorView() {
        TipsUtil.showTipWithAction(recyclerViewGankdaily, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadData(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));
            }
        });
    }

    @Override
    public void showGankList(List<Gank> gankList) {
        list.addAll(gankList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void init() {
        initToolbar(toolbar);
        getIntentData();
//        initDBHelper();
        initGankDaily();
    }

    public void getIntentData() {
        girl = getIntent().getParcelableExtra("girlData");
        calendar = Calendar.getInstance();
        calendar.setTime(girl.publishedAt);
        presenter.loadData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void initDBHelper() {
        dbcurd = new GankDBCURD(this.getApplicationContext());
    }

    public void initGankDaily() {
        setTitle(CommonTools.toDateTimeStr(girl.publishedAt));
        list = new ArrayList<>();
        adapter = new GankDailyAdpter(this, list);
        recyclerViewGankdaily.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGankdaily.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGankdaily.setAdapter(adapter);
        gankDailyIv.setImageDrawable(GlobalConfig.shareDrawable);
        ViewCompat.setTransitionName(gankDailyIv, getString(R.string.pretty_girl));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gankdaily, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                CommonTools.shareGankDaily(this.getApplicationContext(), GankRetrofit.HOST + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.DAY_OF_MONTH));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }

}
