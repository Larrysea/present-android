package com.larry.present.common.basetemplate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.Subscription;

/**
 * 所有类继承的基础fragment
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;
    protected Subscription subscription;

    private View convertView;
    private SparseArray<View> mViews;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View v);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViews = new SparseArray<>();
        convertView = inflater.inflate(getLayoutId(), container, false);
        initViews();

        isInitView = true;
        lazyLoad();
        return convertView;
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    public <E extends View> E findView(int viewId) {
        if (convertView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    private void lazyLoad() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            //不加载数据
            return;
        }
        //加载数据
        initListener();
        initData();

        isFirstLoad = false;

    }


    /**
     * 设置监听方法
     *
     * @param view
     * @param <E>
     */
    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
