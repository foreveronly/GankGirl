package com.onlyleo.gankgirl.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.presenter.CategoryPresenter;
import com.onlyleo.gankgirl.ui.adapter.CategoryPagerAdapter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.IBaseView;
import com.onlyleo.gankgirl.widget.CompatToolbar;

import butterknife.Bind;


public class CategoryActivity extends BaseActivity<CategoryPresenter> implements IBaseView {


    @Bind(R.id.toolbar)
    CompatToolbar toolbar;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.container)
    ViewPager container;

    @Override
    protected int getLayout() {
        return R.layout.activity_category;
    }

    @Override
    protected void initPresenter() {
        presenter = new CategoryPresenter(this, this);
        presenter.init();
    }

    @Override
    public void init() {
        initToolbar(toolbar);
        CategoryPagerAdapter pagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager());
        container.setAdapter(pagerAdapter);
        container.setOffscreenPageLimit(4);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(container);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }
}