package com.onlyleo.gankgirl.ui.activity;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.presenter.CategoryPresenter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.ICategoryView;


public class CategoryActivity extends BaseActivity<CategoryPresenter> implements ICategoryView{

    @Override
    protected int getLayout() {
        return R.layout.activity_category;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void init() {

    }
}