package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.ui.activity.WebActivity;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.TipsUtil;

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
        holder.gankllList.setTag(list.get(position));
        if (position == 0) {
            showTitle(true, holder.titleList);
        } else {
            if (list.get(position).type.equals(list.get(position - 1).type)) {
                showTitle(false, holder.titleList);
            } else {
                showTitle(true, holder.titleList);
            }
        }
        if (holder.titleList.getVisibility() == View.VISIBLE) {
            holder.titleList.setText(list.get(position).type);
        }
        holder.linkList.setText(CommonTools.getGankStyleStr(list.get(position)));
    }

    private void showTitle(boolean show, TextView titleList) {
        if (show) {
            titleList.setVisibility(View.VISIBLE);
        } else {
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
        void onClick() {
            if ("休息视频".equals(((Gank) gankllList.getTag()).type)) {
                if (!CommonTools.isWIFIConnected(context)) {
                    TipsUtil.showTipWithAction(itemView, "你使用的不是wifi网络，要继续吗？", "继续", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebActivity.loadWebViewActivity(context, (Gank) gankllList.getTag());
                        }
                    }, Snackbar.LENGTH_LONG);
                }else{
                    WebActivity.loadWebViewActivity(context, (Gank) gankllList.getTag());
                }
            } else
                WebActivity.loadWebViewActivity(context, (Gank) gankllList.getTag());

        }

        GankDailyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
