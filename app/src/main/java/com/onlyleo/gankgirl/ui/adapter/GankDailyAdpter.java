package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Gank;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GankDailyAdpter extends RecyclerView.Adapter<GankDailyAdpter.GankDailyHolder> {

    private List<Gank> list;
    private Context context;

    public GankDailyAdpter(Context context, List<Gank> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public GankDailyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_daily, parent, false);
        return new GankDailyHolder(view);
    }


    @Override
    public void onBindViewHolder(GankDailyHolder holder, int position) {

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

        @OnClick(R.id.link_list)
        public void onClick() {

        }

        View card;

        public GankDailyHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
