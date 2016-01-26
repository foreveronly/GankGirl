package com.onlyleo.gankgirl.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.presenter.BasePresenter;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    //初始化presenter
    protected P mPresenter;
    protected abstract void initPresenter();
    //设置布局View
    protected abstract int getLayout();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initPresenter();
        checkPresenterIsNull();
        initToolBar();
    }
    //检查Presenter是否存在
    private void checkPresenterIsNull(){
        if(mPresenter==null){
            throw new IllegalStateException("please init mPresenter in initPresenter() method ");
        }
    }

    final private void initToolBar() {
        if(mToolbar == null){
            throw new NullPointerException("please add a Toolbar in your layout.");
        }
        setSupportActionBar(mToolbar);
    }

    public void setTitle(String strTitle,boolean showHome){
        setTitle(strTitle);
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
    }
}
