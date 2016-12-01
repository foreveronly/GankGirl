package com.onlyleo.gankgirl.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.MainPresenter;
import com.onlyleo.gankgirl.ui.adapter.MainAdapter;
import com.onlyleo.gankgirl.ui.base.BaseActivity;
import com.onlyleo.gankgirl.ui.view.IMainView;
import com.onlyleo.gankgirl.utils.CheckVersion;
import com.onlyleo.gankgirl.utils.SPDataTools;
import com.onlyleo.gankgirl.utils.TipsUtil;
import com.onlyleo.gankgirl.widget.CompatToolbar;
import com.onlyleo.gankgirl.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;

/**
 * 主页面
 */

public class MainActivity extends BaseActivity<MainPresenter>
        implements IMainView, SwipeRefreshLayout.OnRefreshListener, LMRecyclerView.LoadMoreListener {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.recycler_view_main)
    LMRecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar)
    CompatToolbar toolbar;
    private List<Girl> list;
    private MainAdapter adapter;
    private boolean canLoading = true;
    private int page = 1; //页数
    private long quitTime = 0; //记录按 back 键的时间
    private SearchView searchView;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if(searchView.isShown()){
                searchView.onActionViewCollapsed();  //collapse your ActionView
            }else {
                if (System.currentTimeMillis() - quitTime > 2000) {
                    TipsUtil.showSnackTip(fab, "再按一次退出程序");
                    quitTime = System.currentTimeMillis();
                } else {
                    this.finish();
                }
            }

        }
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        CheckVersion.getInstance(this).checkVersion(true); //检查更新
        presenter = new MainPresenter(this, this);
        presenter.init();
    }

    @Override
    public void init() {
        initToolbar(toolbar);
        initRecyclerView();
    }

    @Override
    public void initToolbar(Toolbar toolbar) {
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(toolbar);//使活动支持ToolBar
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.tool_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("query",query);
                intent.putExtra("category","all");
                startActivity(intent);
                searchView.onActionViewCollapsed();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tool_category:
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                break;
            case R.id.tool_setting:

                break;
            case R.id.tool_update:
                CheckVersion.getInstance(MainActivity.this).checkVersion(false);
                break;
            case R.id.tool_about:

                break;
        }
        return true;
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
        TipsUtil.showTipWithAction(fab, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadData(page);
            }
        });
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        TipsUtil.showSnackTip(fab, "加载完啦");
    }

    @Override
    public void showGirlList(List<Girl> girlList) {
        canLoading = true;
        if (page == 1) {
            if (list != null && list.size() != 0) {
                Girl netGirl = girlList.get(0);
                Girl spGirl = list.get(0);
                if (!netGirl.url.equals(spGirl.url)) {
                    list.clear();
                    list.addAll(girlList);
                    adapter.notifyDataSetChanged();
                } else TipsUtil.showSnackTip(fab, "已经是最新内容了！");
            } else {
                list.addAll(girlList);
                adapter.notifyDataSetChanged();
            }
            SPDataTools.saveFirstPageGirls(this, girlList);
        } else {
            list.addAll(girlList);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.fab)
    public void fabClick(View view) {
        page = 1;
        recyclerView.smoothScrollToPosition(0);
        presenter.loadData(page);
    }

    @OnClick(R.id.toolbar)
    void toolbarClick() {
        recyclerView.smoothScrollToPosition(0);
    }


    @Override
    public void onRefresh() {
        page = 1;
        presenter.loadData(page);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }

    @Override
    public void loadMore() {
        ++page;
        if (canLoading) {
            presenter.loadData(page);
            canLoading = false;
        }
    }

    private void initRecyclerView(){
        list = SPDataTools.getFirstPageGirls(this);
        if (list == null) list = new ArrayList<>();
        adapter = new MainAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLoadMoreListener(this);
        recyclerView.applyFloatingActionButton(fab);
        ScaleInAnimatorAdapter animatorAdapter = new ScaleInAnimatorAdapter(adapter, recyclerView);
        recyclerView.setAdapter(animatorAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                presenter.loadData(page);
            }
        });
    }

}
