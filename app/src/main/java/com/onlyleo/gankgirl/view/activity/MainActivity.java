package com.onlyleo.gankgirl.view.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.adapter.GankDailyAdapter;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.MainPresenter;
import com.onlyleo.gankgirl.utils.ToastUtils;
import com.onlyleo.gankgirl.view.IMainView;
import com.onlyleo.gankgirl.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, IMainView, LMRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.recycler_view_gankdaily)
    LMRecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Girl> list;
    private GankDailyAdapter adapter;
    private boolean isRefresh = true;
    private boolean canLoading = true;
    private int page = 1;

    @Override
    public void onBackPressed() {
        assert drawerLayout != null;
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
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
        list = new ArrayList<>();
        adapter = new GankDailyAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.loadData(page);
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);
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

    }

    @Override
    public void showNoMoreData() {
        ToastUtils.showShort(this,"没有更多数据!");
    }

    @Override
    public void showGankList(List<Girl> girlList) {
        canLoading = true;
        page++;
        if (isRefresh) {
            list.clear();
            list.addAll(girlList);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            list.addAll(girlList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadMore() {
        isRefresh = true;
        page++;
        presenter.loadData(page);
    }

    @OnClick(R.id.fab)
    public void fabClick(View view) {
        isRefresh = true;
        page = 1;
        presenter.loadData(page);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_index) {
            setTitle(getResources().getString(R.string.app_name), false);
        } else if (id == R.id.nav_android) {
            setTitle(getResources().getString(R.string.menu_android), false);
        } else if (id == R.id.nav_iOS) {
            setTitle(getResources().getString(R.string.menu_iOS), false);
        } else if (id == R.id.nav_app) {
            setTitle(getResources().getString(R.string.menu_app), false);
        } else if (id == R.id.nav_recommend) {
            setTitle(getResources().getString(R.string.menu_recommend), false);
        } else if (id == R.id.nav_video) {
            setTitle(getResources().getString(R.string.menu_video), false);
        } else if (id == R.id.nav_resource) {
            setTitle(getResources().getString(R.string.menu_resource), false);
        } else if (id == R.id.nav_girl) {
            setTitle(getResources().getString(R.string.menu_girl), false);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.loadData(page);
    }

}
