package com.onlyleo.gankgirl.utils;

import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import com.onlyleo.gankgirl.model.entity.Girl;

import java.util.List;

/**
 * Created by OnLy on 2017/4/5 0005.
 */

public class GankDiffCallback extends DiffUtil.Callback {
    private List<Girl> oldList;
    private List<Girl> newList;

    public GankDiffCallback(List<Girl> oldList, List<Girl> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return TextUtils.equals(oldList.get(oldItemPosition).url, newList.get(newItemPosition).url);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }
}
