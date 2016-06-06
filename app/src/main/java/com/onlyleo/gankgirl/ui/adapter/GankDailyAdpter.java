package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.onlyleo.gankgirl.model.entity.Gank;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by BBC on 2016/6/6 0006.
 */
public class GankDailyAdpter extends RecyclerView.Adapter {
    private List<Gank> list;
    private Context context;
    public GankDailyAdpter(Context context,List<Gank> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GankViewHolder extends RecyclerView.ViewHolder{


        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(context,itemView);
        }
    }
}
