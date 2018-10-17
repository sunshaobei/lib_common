package com.sunsh.baselibrary.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.sunsh.baselibrary.widgets.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class LazyLoadFragment extends BaseFragment {

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    //是否首次加载
    private boolean isFirstLoad = true;

    private FrameLayout rootView;
    private Unbinder bind;

    private LoadingView loadingView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = new FrameLayout(getActivity());
            View content = inflater.inflate(getLayoutId(), null);
            rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rootView.addView(content, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            loadingView = new LoadingView(getActivity());
            rootView.addView(loadingView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            loadingView.setVisibility(View.GONE);
            bind = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    protected View getRootView() {
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    /**
     * setUserVisibleHint(boolean isVisibleToUser) 在Fragment OnCreateView()方法之前调用的
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        if (isViewCreated && isUIVisible && isFirstLoad) {
            isFirstLoad = false;
            initUi();
            loadData();
        }
    }


    public void showLoadingView() {
        loadingView.showLoading();
    }

    public void dismissLoadingView() {
        loadingView.dismissLoading();
    }

    public LoadingView getLoadingView() {
        return loadingView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


    protected abstract int getLayoutId();

    protected abstract void initUi();

    protected abstract void loadData();
}
