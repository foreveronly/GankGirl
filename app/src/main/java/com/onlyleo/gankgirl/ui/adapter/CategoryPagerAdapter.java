package com.onlyleo.gankgirl.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.onlyleo.gankgirl.ui.fragment.CategoryFragment;

/**
 * Created by panl on 16/1/5.
 */
public class CategoryPagerAdapter extends FragmentStatePagerAdapter {


    private String[] title = {"Android","iOS","前端","瞎推荐","拓展资源","App"};

    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
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
}
