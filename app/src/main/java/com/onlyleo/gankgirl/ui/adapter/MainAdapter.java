package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.net.GankRetrofit;
import com.onlyleo.gankgirl.ui.activity.GankDailyActivity;
import com.onlyleo.gankgirl.ui.activity.GirlActivity;
import com.onlyleo.gankgirl.ui.activity.MainActivity;
import com.onlyleo.gankgirl.utils.CommonTools;
import com.onlyleo.gankgirl.utils.GlideTools;
import com.onlyleo.gankgirl.widget.AlwaysMarqueeTextView;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import smartisanos.api.OneStepHelper;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.GirlHolder> {
    private List<Girl> list;
    private Context context;

    public List<Girl> getList() {
        return list;
    }

    public void setList(List<Girl> list) {
        this.list = list;
    }

    private OneStepHelper mOneStepHelper;

    public MainAdapter(List<Girl> list, Context context) {
        mOneStepHelper = OneStepHelper.getInstance(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public GirlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcard_main, parent, false);
        return new GirlHolder(view);
    }

    @Override
    public void onBindViewHolder(final GirlHolder holder, int position) {
        holder.card.setTag(list.get(position));
        GlideTools.LoadImage(context, holder.ivGirl, list.get(position).url);
        holder.tvDate.setText(CommonTools.toDateTimeStr(list.get(position).publishedAt));
        holder.tvTitle.setText(list.get(position).desc);
        holder.ivGirl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOneStepHelper.isOneStepShowing()) {
                    Calendar calendar = Calendar.getInstance();
                    String url = GankRetrofit.BaseURL + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.DAY_OF_MONTH);
                    mOneStepHelper.dragLink(v, url);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GirlHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_girl)
        ImageView ivGirl;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_title)
        AlwaysMarqueeTextView tvTitle;

        @OnClick(R.id.rl_title)
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
