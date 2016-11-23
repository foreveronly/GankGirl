package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.ui.activity.GankDailyActivity;
import com.onlyleo.gankgirl.ui.activity.GirlActivity;
import com.onlyleo.gankgirl.ui.activity.MainActivity;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.GlideTools;
import com.onlyleo.gankgirl.widget.AlwaysMarqueeTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.GirlHolder> {
    private List<Girl> list;
    private Context context;
    private int lastPosition = 0;

    public MainAdapter(List<Girl> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public GirlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new GirlHolder(view);
    }

    @Override
    public void onBindViewHolder(final GirlHolder holder, int position) {
        holder.card.setTag(list.get(position));
        GlideTools.LoadImage(context, holder.ivGirl, list.get(position).url);
        holder.tvDate.setText(CommonTools.toDateTimeStr(list.get(position).publishedAt));
        holder.tvTitle.setText(list.get(position).desc);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GirlHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_girl)
        ImageView ivGirl;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_title)
        AlwaysMarqueeTextView tvTitle;
        @Bind(R.id.ll_title)
        LinearLayout llTitle;


        @OnClick(R.id.ll_title)
        void itemCilick() {
            GankDailyActivity.LaunchGankDailyActivity((MainActivity) context, ivGirl, (Girl) card.getTag());
        }

        @OnClick(R.id.iv_girl)
        void girlClick() {
            GirlActivity.LaunchGirlActivity((MainActivity) context, ivGirl, (Girl) card.getTag());
        }

        View card;

        GirlHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
