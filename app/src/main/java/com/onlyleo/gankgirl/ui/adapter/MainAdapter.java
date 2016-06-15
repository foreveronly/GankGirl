package com.onlyleo.gankgirl.ui.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.ShareElement;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.ui.activity.GankDailyActivity;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.widget.AlwaysMarqueeTextView;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.GirlHolder> {
    private List<Girl> list;
    private Context context;
    private Girl girl;
    int lastPosition = 0;

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
    public void onBindViewHolder(GirlHolder holder, int position) {
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
        String title = girl.desc;
        holder.tvDate.setText(CommonTools.toDateTimeStr(girl.createdAt));
        holder.tvTitle.setText(title);
        showItemAnimation(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GirlHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_girl)
        ImageView ivgirl;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_title)
        AlwaysMarqueeTextView tvTitle;
        @Bind(R.id.main_root)
        RelativeLayout mainRoot;

        @OnClick(R.id.main_root)
        void itemCilick() {
            if (ivgirl.getDrawable() != null)
                ShareElement.shareDrawable = ivgirl.getDrawable();
            Intent intent = new Intent(context, GankDailyActivity.class);
            intent.putExtra("girlData", (Serializable) card.getTag());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) context, ivgirl, context.getString(R.string.pretty_girl));
            ActivityCompat.startActivity((Activity) context, intent, optionsCompat.toBundle());
        }

        View card;

        public GirlHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    private void showItemAnimation(GirlHolder holder, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(holder.card, "translationY", 1f * holder.card.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }
}
