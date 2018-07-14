package com.onlyleo.gankgirl.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.GirlPresenter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.IGirlView;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.FileUtil;
import com.onlyleo.gankgirl.utils.GlideTools;
import com.onlyleo.gankgirl.utils.TipsUtil;

import java.io.File;

import butterknife.BindView;


public class GirlActivity extends BaseActivity<GirlPresenter> implements IGirlView {

    @BindView(R.id.iv_girl_all)
    PhotoView ivGirl;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
        initToolbar(toolbar,false);
        getIntentData();
        initGirl();
    }

    public void initGirl() {
        GlideTools.LoadImage(this, ivGirl, girl.url);
        ivGirl.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ViewCompat.setTransitionName(ivGirl, getString(R.string.pretty_girl));
        setTitle(CommonTools.toDateTimeStr(girl.publishedAt));
        ivGirl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOneStepHelper.isOneStepShowing()) {
                    Bitmap girlbm = CommonTools.drawableToBitamp(ivGirl.getDrawable());
                    File file = FileUtil.getFileByBitmap(girlbm, CommonTools.toDateString(girl.publishedAt).toString());
                    mOneStepHelper.dragImage(v, file, "image/jpeg");
                    return false;
                }
                return false;
            }
        });
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
                    girlbm.recycle();
                } else {
                    presenter.saveGirl(girlbm, CommonTools.toDateString(girl.publishedAt).toString());
                }
                break;
            case R.id.action_share:
                presenter.shareGirl(girlbm, CommonTools.toDateString(girl.publishedAt).toString());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {

        presenter.release();
        super.onDestroy();
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

//    public static void LaunchGirlActivity(Activity activity, View imageView, View textView, Girl girl) {
//        Intent Intent = new Intent(activity, GirlActivity.class);
//        Intent.putExtra("girlData", girl);
//        Pair<View, String> image = new Pair<>(imageView, activity.getString(R.string.pretty_girl));
//        Pair<View, String> text = new Pair<>(textView, activity.getString(R.string.date));
//
//        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, text);
//        ActivityCompat.startActivity(activity, Intent, optionsCompat.toBundle());
//    }
}
