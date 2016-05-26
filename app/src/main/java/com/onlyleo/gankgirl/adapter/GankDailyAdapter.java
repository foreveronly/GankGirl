package com.onlyleo.gankgirl.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by leoonly on 16/5/18.
 */
public class GankDailyAdapter extends RecyclerView.Adapter<GankDailyAdapter.GankHolder>{
    private List<Girl> list;
    private Context context;
    private Girl girl;
    int lastPosition = 0;
    public GankDailyAdapter(List<Girl>list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public GankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gankdaily_item, parent, false);
        return new GankHolder(view);
    }

    @Override
    public void onBindViewHolder(GankHolder holder, int position) {
        girl = list.get(position);
        holder.card.setTag(girl);
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        holder.ivgirl.setBackgroundColor(Color.argb(204, red, green, blue));
        Glide.with(context)
                .load(girl.url)
                .crossFade()
                .into(holder.ivgirl);
        String desc[] = girl.desc.split(",");
        String date = desc[0];
        String title = desc[1];
        holder.tvDate.setText(date);
        holder.tvTitle.setText(title);
        showItemAnimation(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GankHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_girl)
        ImageView ivgirl;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_title)
        TextView tvTitle;

        View card;

        public GankHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
    private void showItemAnimation(GankHolder holder, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(holder.card, "translationY", 1f * holder.card.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }
}
