package com.onlyleo.gankgirl.ui.adapter;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Category;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.ui.activity.WebActivity;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.TipsUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用于 gank 的 recyerview 的适配器
 */
public class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.GankListViewHolder> {

    private Activity context;
    private List<Category> list;

    public GankListAdapter(Activity context, List<Category> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ganklist, parent, false);
        return new GankListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GankListViewHolder holder, int position) {
        holder.card.setTag(list.get(position));
        holder.gankllList.setTag(list.get(position));
        holder.linkList.setText(CommonTools.getGankStyleStr(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GankListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.link_list)
        TextView linkList;
        @Bind(R.id.gankll_list)
        LinearLayout gankllList;

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
                } else {
                    WebActivity.loadWebViewActivity(context, (Gank) gankllList.getTag());
                }
            } else
                WebActivity.loadWebViewActivity(context, (Gank) gankllList.getTag());
        }

        View card;

        GankListViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
