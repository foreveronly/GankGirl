package com.onlyleo.gankgirl.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
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

import static com.onlyleo.gankgirl.utils.SPDataTools.getFirstPageGirls;

public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, IMainView, SwipeRefreshLayout.OnRefreshListener, LMRecyclerView.LoadMoreListener {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.recycler_view_main)
    LMRecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar)
    CompatToolbar toolbar;
    private List<Girl> list;
    private MainAdapter adapter;
    private boolean canLoading = true;
    private int page = 1;
    private long quitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (System.currentTimeMillis() - quitTime > 2000) {
                    TipsUtil.showSnackTip(fab, "再按一次退出程序");
                    quitTime = System.currentTimeMillis();
                } else {
                    this.finish();
                }
            }
        }
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        CheckVersion.getInstance(this).checkVersion(true);
        presenter = new MainPresenter(this, this);
        presenter.init();
    }

    @Override
    public void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        list = getFirstPageGirls(this);
        if (list == null) list = new ArrayList<>();
        adapter = new MainAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLoadMoreListener(this);
        recyclerView.applyFloatingActionButton(fab);
        ScaleInAnimatorAdapter animatorAdapter = new ScaleInAnimatorAdapter(adapter,recyclerView);
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

        if(page==1){
            if(list!=null){
                Girl netGirl = girlList.get(0);
                Girl spGirl = SPDataTools.getFirstPageGirls(this).get(0);
                if(!netGirl.url.equals(spGirl.url)){
                    list.clear();
                    list.addAll(girlList);
                    adapter.notifyDataSetChanged();
                }
            }else{
                SPDataTools.saveFirstPageGirls(this, girlList);
                list.addAll(girlList);
                adapter.notifyDataSetChanged();
            }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_category) {
            startActivity(new Intent(this, CategoryActivity.class));
//        } else if (id == R.id.nav_search) {
//        } else if (id == R.id.nav_setting) {
        } else if (id == R.id.nav_update) {
            CheckVersion.getInstance(this).checkVersion(false);
        }
//        else if (id == R.id.nav_about) {
//        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
}
