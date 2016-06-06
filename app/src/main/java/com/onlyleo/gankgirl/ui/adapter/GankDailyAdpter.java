package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BBC on 2016/6/6 0006.
 */
public class GankDailyAdpter extends RecyclerView.Adapter {

    private List<Gank> list;
    private Context context;

    public GankDailyAdpter(Context context, List<Gank> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_daily, parent, false);
        return new GankDailyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Gank gank = list.get(position);
        Logger.d(gank.toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GankDailyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title_list)
        TextView titleList;
        @Bind(R.id.link_list)
        TextView linkList;
        public GankDailyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(context, itemView);
        }
    }
}
