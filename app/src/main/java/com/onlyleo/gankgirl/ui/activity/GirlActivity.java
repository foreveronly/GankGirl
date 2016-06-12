package com.onlyleo.gankgirl.ui.activity;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.GirlPresenter;
import com.onlyleo.gankgirl.ui.view.IGirlView;
import com.onlyleo.gankgirl.utils.CalendarUtil;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlActivity extends BaseActivity<GirlPresenter> implements IGirlView {


    @Bind(R.id.iv_girl_all)
    ImageView ivGirl;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Girl girl;

    private PhotoViewAttacher photoViewAttacher;
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
        initToolbar(toolbar);
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
        photoViewAttacher = new PhotoViewAttacher(ivGirl);
    }

    public void getIntentData() {
        girl = (Girl) getIntent().getSerializableExtra("girlData");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_girl, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }
}
