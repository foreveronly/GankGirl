package com.onlyleo.gankgirl.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class LMRecyclerView extends RecyclerView {
    private FloatingActionButton floatingActionButton;
    private LoadMoreListener listener;


    public LMRecyclerView(Context context) {
        super(context);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void applyFloatingActionButton(FloatingActionButton floatingActionButton) {
        this.floatingActionButton = floatingActionButton;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.listener = loadMoreListener;
    }



    @Override
    public void onScrollStateChanged(int state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        switch (state) {
            case SCROLL_STATE_IDLE:
                if (floatingActionButton != null && !floatingActionButton.isShown())
                    floatingActionButton.show();
                int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                if (lastVisibleItem == totalItemCount - 1) {
                    if (listener != null)
                        listener.loadMore();
                }
                break;
            case SCROLL_STATE_DRAGGING:
                if (floatingActionButton != null && floatingActionButton.isShown())
                    floatingActionButton.hide();
                break;
            case SCROLL_STATE_SETTLING:
                if (floatingActionButton != null && floatingActionButton.isShown())
                    floatingActionButton.hide();
                break;
        }
    }

    public interface LoadMoreListener {
        void loadMore();
    }
}
