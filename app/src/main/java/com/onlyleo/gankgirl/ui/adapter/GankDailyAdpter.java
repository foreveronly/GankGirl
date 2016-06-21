package com.onlyleo.gankgirl.ui.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private int mLastPosition = -1;

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
            holder.titleList.setText(gank.type);
        }
        holder.linkList.setText(CommonTools.getGankStyleStr(gank));
        showItemAnim(holder.linkList, position);
    }

    public void showTitle(boolean show, TextView titleList) {
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
        public void onClick() {
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

        public GankDailyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void showItemAnim(final View view, final int position) {
        final Context context = view.getContext();
        if (position > mLastPosition) {
            view.setAlpha(0);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(context,
                            R.anim.slide_in_bottom);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            view.setAlpha(1);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    view.startAnimation(animation);
                }
            }, 300);
            mLastPosition = position;
        }
    }
}
