package com.onlyleo.gankgirl.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.onlyleo.gankgirl.GankGirlApp;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.adapter.MainAdapter;
import com.onlyleo.gankgirl.model.entity.Girl;
import com.onlyleo.gankgirl.presenter.MainPresenter;
import com.onlyleo.gankgirl.ui.view.IMainView;
import com.onlyleo.gankgirl.widget.LMRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, IMainView, LMRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

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
    private MainAdapter adapter;
    private boolean isRefresh = true;
    private boolean canLoading = true;
    private int page = 1;
    private boolean isQuit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }else if(isQuit){
                GankGirlApp.getInstance().exit();
            }else if(!isQuit){
                isQuit = true;
                Snackbar.make(drawerLayout,"再按一次退出程序",Snackbar.LENGTH_SHORT).show();
                mHandler.sendEmptyMessageDelayed(0,2000);
            }
        }
        return false;
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
        initToolbar();
        setTitle(getString(R.string.app_name),true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        list = new ArrayList<>();
        adapter = new MainAdapter(list, this);
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
        Snackbar.make(drawerLayout,"加载完啦",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showGirlList(List<Girl> girlList) {
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
        isRefresh = false;
        page++;
        presenter.loadData(page);
    }

    @OnClick(R.id.fab)
    public void fabClick(View view) {
        isRefresh = true;
        page = 1;
        recyclerView.smoothScrollToPosition(0);
        presenter.loadData(page);
    }
    @OnClick(R.id.toolbar)
    void toolbarClick(){
        recyclerView.smoothScrollToPosition(0);
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
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.loadData(page);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isQuit = false;
        }
    };

}
