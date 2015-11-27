package cn.owltf.daily.view.ui.fragment;


import android.app.Activity;
import android.net.Network;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.owltf.daily.R;
import cn.owltf.daily.domain.utils.IntentKeys;
import cn.owltf.daily.domain.utils.NetworkUtils;
import cn.owltf.daily.domain.utils.PrefUtils;
import cn.owltf.daily.model.entity.Daily;
import cn.owltf.daily.presenters.NewsListFragmentPresenter;
import cn.owltf.daily.view.adapter.DailyListAdapter;
import cn.owltf.daily.view.IViewPager;
import cn.owltf.daily.view.widget.RecyclerViewItemDecoration;

public class DailyListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, IViewPager{
    @Bind(R.id.cardList)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int date;
    private List<Daily> mNewsList = new ArrayList<>();
    private DailyListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsListFragmentPresenter mPresenter;

    public static DailyListFragment newInstance(){
        DailyListFragment fragment = new DailyListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            Bundle bundle = getArguments();
            date = bundle.getInt(IntentKeys.DATE);
            setRetainInstance(true);
        }
        mPresenter = new NewsListFragmentPresenter(this, date);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_list, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(getActivity()));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);
        mAdapter = new DailyListAdapter(getActivity(), mNewsList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRefresh(); //Snackbar的显示依赖于当前View, 所以在View创建之后刷新
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onRefresh() {
        mPresenter.getNews(date);
        if(NetworkUtils.isNetworkConnected()){
            showProgress();
        }else if(!NetworkUtils.isNetworkConnected() && PrefUtils.isEnableCache()){
            Snackbar.make(getView(), R.string.snack_network_error_load_cache, Snackbar.LENGTH_LONG).show();
        }else if(!NetworkUtils.isNetworkConnected() && !PrefUtils.isEnableCache()){
            Snackbar.make(getView() ,R.string.snack_network_error, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void loadData(List<Daily> dailies) {
        mNewsList.clear();
        mNewsList.addAll(dailies);
        hideProgress();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
