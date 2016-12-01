package com.onlyleo.gankgirl.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Category;
import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.presenter.CategoryFragmentPresenter;
import com.onlyleo.gankgirl.ui.adapter.GankListAdapter;
import com.onlyleo.gankgirl.ui.base.BaseFragment;
import com.onlyleo.gankgirl.ui.view.ICategoryView;
import com.onlyleo.gankgirl.utils.TipsUtil;
import com.onlyleo.gankgirl.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

public class CategoryFragment extends BaseFragment<CategoryFragmentPresenter> implements ICategoryView,
        LMRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recycler_view_category)
    LMRecyclerView recyclerViewCategory;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    public static final String TYPE = "type";
    private String type;
    private GankListAdapter categoryadapter;
    private List<Category> list;
    private int page = 1;
    private boolean canLoading = true;
    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    public CategoryFragment() {

    }


    public static CategoryFragment newInstance(String type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initPresenter() {
        presenter = new CategoryFragmentPresenter(getContext(), this);
        presenter.init();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    public void loadMore() {
        ++page;
        if (canLoading) {
            presenter.loadData(type, page);
            canLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.loadData(type, page);
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorView() {
        canLoading = true;
        TipsUtil.showTipWithAction(recyclerViewCategory, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadData(type, page);
            }
        });
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        TipsUtil.showSnackTip(recyclerViewCategory, "加载完啦");
    }

    @Override
    public void showCategoryData(List<Category> list) {
        canLoading = true;

        if (page == 1) {
            this.list.clear();
            this.list.addAll(list);
            categoryadapter.notifyDataSetChanged();
        } else {
            this.list.addAll(list);
            categoryadapter.notifyDataSetChanged();
        }
    }

    @Override
    public void init() {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.release();
    }

    @Override
    protected void lazyLoad() {
        if (isPrepared && isVisible && !mHasLoadedOnce) {
            mHasLoadedOnce = true;
            list = new ArrayList<>();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            categoryadapter = new GankListAdapter(getActivity(), list);
            recyclerViewCategory.setLoadMoreListener(this);
            recyclerViewCategory.setLayoutManager(layoutManager);

            AlphaAnimatorAdapter alphaAnimatorAdapter = new AlphaAnimatorAdapter(categoryadapter, recyclerViewCategory);

            recyclerViewCategory.setAdapter(alphaAnimatorAdapter);
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    presenter.loadData(type, page);
                }
            });
        }

    }
}
