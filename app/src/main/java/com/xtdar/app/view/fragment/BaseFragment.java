package com.xtdar.app.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtdar.app.common.NLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by asus on 2016/3/26.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private boolean isVisible = false;//当前Fragment是否可见
    private boolean isInitView = false;//是否与View建立起映射关系
    private boolean isFirstLoad = true;//是否是第一次加载数据

    private View view;
    private SparseArray<View> mViews;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NLog.w("BaseFragment","onCreateView" );
        view = inflater.inflate(getLayoutId(), container, false);
        mViews = new SparseArray<>();
        unbinder = ButterKnife.bind(this, view);//返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        initView();
        isInitView = true;
        lazyLoadData();
        return view;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        NLog.w("BaseFragment","onViewCreated" );
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        NLog.w("BaseFragment","onAttach" );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        NLog.w("BaseFragment","isVisibleToUser " + isVisibleToUser );
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (isFirstLoad) {
            NLog.w("BaseFragment","第一次加载 " + " isInitView  " + isInitView + "  isVisible  " + isVisible );
        } else {
            NLog.w("BaseFragment","不是第一次加载" + " isInitView  " + isInitView + "  isVisible  " + isVisible );
        }

        if (!isFirstLoad || !isVisible || !isInitView) {
            NLog.w("BaseFragment","不加载" + " isInitView  " + isInitView + "  isVisible  " + isVisible );
            return;
        }

            NLog.w("BaseFragment","完成数据第一次加载"+ "   " + this.getClass().getSimpleName());
        initData();
        isFirstLoad = false;
    }

    /**
     * 加载页面布局文件
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    protected abstract void initView();

    /**
     * 加载要显示的数据
     */
    protected abstract void initData();

    /**
     * fragment中可以通过这个方法直接找到需要的view，而不需要进行类型强转
     * @param viewId
     * @param <E>
     * @return
     */
    protected <E extends View> E findView(int viewId) {
        if (view != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) this.view.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }


    @Override
    public void onStart() {
        super.onStart();
        //NLog.w("BaseFragment","onStart" );
    }

    @Override
    public void onResume() {
        super.onResume();
        //NLog.w("BaseFragment","onResume" );
    }

    @Override
    public void onPause() {
        super.onPause();
        //NLog.w("BaseFragment","onPause" );
    }

    @Override
    public void onStop() {
        super.onStop();
        //NLog.w("BaseFragment","onStop" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //NLog.w("BaseFragment","onDestroy" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
          isVisible = false;
          isInitView = false;
          isFirstLoad = true;
          unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

    }
}
