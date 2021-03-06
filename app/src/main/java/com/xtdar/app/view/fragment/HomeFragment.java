package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lzy.ninegrid.NineGridView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xtdar.app.R;
import com.xtdar.app.common.NToast;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.presenter.HomeFragmentPresenter;
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.MineActivity;
import com.xtdar.app.view.activity.SignInActivity;
import com.youth.banner.Banner;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class HomeFragment extends Fragment implements View.OnClickListener  {
private static final int Blue=0x001bb4fb;
    private View view;
    public static HomeFragment instance = null;

    private HomeFragmentPresenter presenter;
    private TextView title;
    private RelativeLayout layout_me;
    private RecyclerView recycleView;
    private SwipeRefreshLayout swiper;
    private Banner banner;

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        initViews();

//        initData();
        presenter = new HomeFragmentPresenter(getContext());
        presenter.init(banner,swiper,recycleView);
        //StatusBarUtil.setTranslucent(getActivity(), StatusBarUtil.);
        StatusBarUtil.setTranslucent(getActivity(),0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadData();
    }

    private void initViews() {
        layout_me=(RelativeLayout)view.findViewById(R.id.layout_me);
        layout_me.setOnClickListener(this);
        title=(TextView)view.findViewById(R.id.text_title);
        title.setText("设备");
        view.findViewById(R.id.txt_add_driver).setOnClickListener(this);
        recycleView= (RecyclerView) view.findViewById(R.id.recyclerView);
        swiper= (SwipeRefreshLayout) view.findViewById(R.id.swiper);
        view.findViewById(R.id.right_icon).setOnClickListener(this);
        view.findViewById(R.id.icon_add).setOnClickListener(this);
        //简单使用
        banner = (Banner) view.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());//设置图片加载器
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unbindService();
    }
    @Override
    public void onClick(View v) {
      presenter.onMeClick(v);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == HomeFragmentPresenter.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    NToast.longToast(getActivity(), "解析结果:"+result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    NToast.longToast(getActivity(), "解析二维码失败");
                }
            }
        }
    }
}
