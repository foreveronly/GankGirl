package com.onlyleo.gankgirl.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.GirlPresenter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.IGirlView;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.FileUtil;
import com.onlyleo.gankgirl.utils.GlideTools;
import com.onlyleo.gankgirl.utils.TipsUtil;
import com.onlyleo.gankgirl.widget.CompatToolbar;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlActivity extends BaseActivity<GirlPresenter> implements IGirlView {


    @Bind(R.id.iv_girl_all)
    ImageView ivGirl;
    @Bind(R.id.toolbar)
    CompatToolbar toolbar;
    private Girl girl;

    @Override
    protected int getLayout() {
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
        GlideTools.LoadImage(this,ivGirl,girl.url);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(ivGirl);
        photoViewAttacher.update();
        ViewCompat.setTransitionName(ivGirl, getString(R.string.pretty_girl));
        setTitle(CommonTools.toDateTimeStr(girl.publishedAt));
    }

    public void getIntentData() {
        girl = getIntent().getParcelableExtra("girlData");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_girl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bitmap girlbm = CommonTools.drawableToBitamp(ivGirl.getDrawable());
        switch (item.getItemId()) {
            case R.id.action_save:
                if (!FileUtil.isSDCardEnable() || girl == null) {
                    TipsUtil.showSnackTip(ivGirl, "保存失败!");
                } else {
                    presenter.saveGirl(girlbm, CommonTools.toDateString(girl.publishedAt).toString());
                }
                break;
            case R.id.action_share:
                presenter.shareGirl(girlbm, CommonTools.toDateString(girl.publishedAt).toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }

    @Override
    public void showSaveGirlResult(String result) {
        TipsUtil.showSnackTip(ivGirl, result);
    }

    public static void LaunchGirlActivity(Activity activity, View imageView, Girl girl) {
        Intent girlIntent = new Intent(activity, GirlActivity.class);
        girlIntent.putExtra("girlData", girl);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, imageView, activity.getString(R.string.pretty_girl));
        ActivityCompat.startActivity(activity, girlIntent, optionsCompat.toBundle());
    }
}
