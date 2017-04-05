package com.onlyleo.gankgirl.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.onlyleo.gankgirl.presenter.BasePresenter;
import com.onlyleo.gankgirl.utils.CommonTools;

import butterknife.ButterKnife;
import smartisanos.api.OneStepHelper;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();
    protected P presenter;
    protected OneStepHelper mOneStepHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initPresenter();
        checkPresenterIsNull();
        mOneStepHelper = OneStepHelper.getInstance(this);
        Log.i(TAG, "onCreate");
    }

    protected abstract int getLayout();

    protected abstract void initPresenter();

    protected void initToolbar(Toolbar toolbar, boolean setStatusBarColor) {
        CommonTools.setTranslucentStatusBar(getWindow(),setStatusBarColor);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //检查Presenter是否存在
    private void checkPresenterIsNull() {
        if (presenter == null) {
            throw new IllegalStateException("please init mPresenter in initPresenter() method ");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        Log.i(TAG, "onDestroy");
        super.onDestroy();
//        RefWatcher refWatcher = GankGirlApp.getRefWatcher(this);
//        refWatcher.watch(this);


    }
}
