package com.onlyleo.gankgirl.ui.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.onlyleo.gankgirl.GlobalConfig;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.GirlPresenter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.IGirlView;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.FileUtil;
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

    private Bitmap girlbm;
    private PhotoViewAttacher photoViewAttacher;

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
        photoViewAttacher = new PhotoViewAttacher(ivGirl);
        if (GlobalConfig.shareDrawable != null){
            ivGirl.setImageDrawable(GlobalConfig.shareDrawable);
            girlbm = ((BitmapDrawable)GlobalConfig.shareDrawable).getBitmap();
        }
        else
            Glide.with(this).load(girl.url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ivGirl.setImageBitmap(resource);
                    photoViewAttacher.update();
                    girlbm = resource;
                }
                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    ivGirl.setImageDrawable(errorDrawable);
                }
            });
        photoViewAttacher.update();
        ViewCompat.setTransitionName(ivGirl, getString(R.string.pretty_girl));
        setTitle(CommonTools.toDateTimeStr(girl.publishedAt));
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
    public boolean onOptionsItemSelected(MenuItem item) {
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
        GlobalConfig.shareDrawable = null;
        presenter.release();
    }

    @Override
    public void showSaveGirlResult(String result) {
        TipsUtil.showSnackTip(ivGirl, result);
    }
}
