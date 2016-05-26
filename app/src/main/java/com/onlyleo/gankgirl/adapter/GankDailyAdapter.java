package com.onlyleo.gankgirl.adapter;

import android.content.Context;
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
        Glide.with(context)
                .load(girl.url)
                .crossFade()
                .into(holder.ivMeizi);
        String desc[] = girl.desc.split(",");
        String date = desc[0];
        String title = desc[1];
        holder.tvDate.setText(date);
        holder.tvTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GankHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_meizi)
        ImageView ivMeizi;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_title)
        TextView tvTitle;

        public GankHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
