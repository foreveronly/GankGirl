package com.onlyleo.gankgirl.ui.adapter;

import android.animation.ObjectAnimator;
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

public class Categoryadapter extends RecyclerView.Adapter<Categoryadapter.CategoryViewHolder> {

    private Context context;
    private List<Gank> list;
    private int lastPosition = -1;

    public Categoryadapter(Context context, List<Gank> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_fragment, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Gank gank = list.get(position);
        holder.card.setTag(gank);
        holder.gankllList.setTag(gank);
        holder.linkList.setText(CommonTools.getGankStyleStr(gank));
        showItemAnimation(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
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

        public CategoryViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    private void showItemAnimation(CategoryViewHolder holder, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(holder.card, "translationY", 1f * holder.card.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }
}
