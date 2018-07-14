package com.onlyleo.gankgirl.ui.activity;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Search;
import com.onlyleo.gankgirl.presenter.SearchPresenter;
import com.onlyleo.gankgirl.ui.adapter.SearchListAdapter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.ISearchView;
import com.onlyleo.gankgirl.utils.ToastUtils;
import com.onlyleo.gankgirl.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements ISearchView, LMRecyclerView.LoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_search)
    LMRecyclerView recyclerViewSearch;
    @BindView(R.id.clprogressbar_searh)
    ContentLoadingProgressBar clprogressbarSearh;

    private SearchListAdapter searchListAdapter;
    private List<Search> searches;
    private int page = 1;
    private String query;
    private String category;
    private boolean canLoading = true;
    private boolean load = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initPresenter() {
        presenter = new SearchPresenter(this, this);
        presenter.init();
    }

    @Override
    public void init() {
        initToolbar(toolbar,false);
        setTitle("搜索结果");
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        searches = new ArrayList<>();
        searchListAdapter = new SearchListAdapter(SearchActivity.this, searches);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewSearch.setLayoutManager(linearLayoutManager);
        recyclerViewSearch.setLoadMoreListener(this);
        recyclerViewSearch.setAdapter(searchListAdapter);

        clprogressbarSearh.post(new Runnable() {
            @Override
            public void run() {
                if (!"".equals(query) && !"".equals(category))
                    load = false;
                    presenter.search(query, category, page);
            }
        });
    }

    private void initData() {
        query = getIntent().getStringExtra("query");
        category = getIntent().getStringExtra("category");
    }

    @Override
    public void showGankList(List<Search> list) {

        canLoading = true;
        if (page == 1) {
            searches.clear();
        }
        searches.addAll(list);
        searchListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        if (!clprogressbarSearh.isShown() && !load)
            clprogressbarSearh.show();

    }

    @Override
    public void hideProgress() {
        if (clprogressbarSearh.isShown())
            clprogressbarSearh.hide();
    }

    @Override
    public void showErrorView() {
        ToastUtils.showShort("搜索失败");
    }

    @Override
    public void showNoMoreData() {
        ToastUtils.showShort("已显示全部搜索结果");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searches = null;
    }

    @Override
    public void loadMore() {
        ++page;
        if (canLoading) {
            presenter.search(query, category, page);
            canLoading = false;
            load = true;
        }
    }

}
