package com.onlyleo.gankgirl.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.presenter.CategoryPresenter;
import com.onlyleo.gankgirl.ui.adapter.CategoryPagerAdapter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.IBaseView;
import com.onlyleo.gankgirl.widget.CompatToolbar;

import butterknife.Bind;


public class CategoryActivity extends BaseActivity<CategoryPresenter> implements IBaseView, ViewPager.OnPageChangeListener {

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
        setTitle("分类");
        CategoryPagerAdapter pagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager(), CategoryActivity.this);
        container.setAdapter(pagerAdapter);
        container.setOffscreenPageLimit(4);
        container.addOnPageChangeListener(this);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(container);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position < tabLayout.getTabCount() - 1) {
            View midV = tabLayout.getTabAt(position).getCustomView();
            View rightV = tabLayout.getTabAt(position + 1).getCustomView();
            TextView midTv = (TextView) midV.findViewById(R.id.tv_tabItem);
            TextView rightTv = (TextView) rightV.findViewById(R.id.tv_tabItem);
            midTv.setAlpha(1f - (positionOffset * 7 / 10));
            rightTv.setAlpha(0.3f + (positionOffset * 7 / 10));
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}