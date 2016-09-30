package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.ui.fragment.CategoryFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private String[] title = {"Android", "iOS", "前端", "瞎推荐", "拓展资源", "App"};
    @Bind(R.id.tv_tabItem)
    TextView tabTv;

    public CategoryPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(title[position]);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    public View getTabView(int postion) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_tab_category, null);
        ButterKnife.bind(this, v);
        tabTv.setText(title[postion]);
        tabTv.setTextColor(Color.WHITE);
        tabTv.setAlpha(0.3f);
        return v;
    }

}
