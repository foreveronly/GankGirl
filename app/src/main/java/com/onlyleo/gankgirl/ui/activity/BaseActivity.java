package com.onlyleo.gankgirl.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.onlyleo.gankgirl.GankGirlApp;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.ShareElement;
import com.onlyleo.gankgirl.presenter.BasePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();
    protected ActionBar actionBar;
    protected P presenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        GankGirlApp.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initToolbar();
        initPresenter();
        checkPresenterIsNull();
        Log.i(TAG,"onCreate");
    }

    protected abstract int provideContentViewId();

    protected abstract void initPresenter();

    public void initToolbar(){
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setTitle(String strTitle,boolean showHome){
        setTitle(strTitle);
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
    }

    //检查Presenter是否存在
    private void checkPresenterIsNull(){
        if(presenter==null){
            throw new IllegalStateException("please init mPresenter in initPresenter() method ");
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(ShareElement.shareDrawable !=null){
            ShareElement.shareDrawable = null;
        }
        Log.i(TAG,"onDestroy");

    }
}
