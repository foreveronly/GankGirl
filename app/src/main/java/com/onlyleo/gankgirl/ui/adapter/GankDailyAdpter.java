package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.utils.StringStyleUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GankDailyAdpter extends RecyclerView.Adapter<GankDailyAdpter.GankDailyHolder> {

    private List<Gank> list;
    private Context context;
    int lastPosition = 0;
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
        Gank gank = list.get(position);
        holder.gankllList.setTag(gank);
        if(position == 0){
            showTitle(true,holder.titleList);
        }else{
            if(list.get(position).type.equals(list.get(position-1).type)){
                showTitle(false,holder.titleList);
            }else {
                showTitle(true,holder.titleList);
            }
        }
        holder.titleList.setText(gank.type);
        holder.linkList.setText(StringStyleUtil.getGankStyleStr(gank));
    }

    public void showTitle(boolean show,TextView titleList){
        if(show){
            titleList.setVisibility(View.VISIBLE);
        }else{
            titleList.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class GankDailyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.gankll_list)
        LinearLayout gankllList;
        @Bind(R.id.title_list)
        TextView titleList;
        @Bind(R.id.link_list)
        TextView linkList;

        @OnClick(R.id.gankll_list)
        public void onClick() {

        }


        public GankDailyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
