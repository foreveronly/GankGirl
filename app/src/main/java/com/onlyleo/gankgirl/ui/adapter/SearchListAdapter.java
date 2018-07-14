package com.onlyleo.gankgirl.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.model.entity.Search;
import com.onlyleo.gankgirl.ui.activity.WebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用于 gank 的 recyerview 的适配器
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.GankListViewHolder> {

    private Activity context;
    private List<Search> search;

    public SearchListAdapter(Activity context, List<Search> list) {
        this.context = context;
        this.search = list;
    }

    @Override
    public GankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ganklist, parent, false);
        return new GankListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GankListViewHolder holder, int position) {
        holder.card.setTag(search.get(position));
        holder.gankllList.setTag(search.get(position));
        holder.linkList.setText(search.get(position).desc);
    }

    @Override
    public int getItemCount() {
        return search.size();
    }

    class GankListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.link_list)
        TextView linkList;
        @BindView(R.id.gankll_list)
        LinearLayout gankllList;

        @OnClick(R.id.gankll_list)
        void onClick() {
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
