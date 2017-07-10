package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.jaeger.library.StatusBarUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xtdar.app.R;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.presenter.HomeFragmentPresenter;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.DownloadActivity;
import com.xtdar.app.view.activity.HistoryActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.QrCodeActivity;
import com.xtdar.app.view.activity.SearchActivity;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class HomeFragment extends Fragment implements View.OnClickListener  {
private static final int Blue=0x001bb4fb;
    private View view;
    public static HomeFragment instance = null;


    private HomeFragmentPresenter homeFragmentPresenter;
    private TextView title;
    private RelativeLayout layout_back;
    private RecyclerView recycleView;
    private TextView addDriver;

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
        homeFragmentPresenter = new HomeFragmentPresenter(getContext());
        homeFragmentPresenter.init(recycleView);
        //StatusBarUtil.setTranslucent(getActivity(), StatusBarUtil.);
        StatusBarUtil.setColor(getActivity(), Blue,0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        homeFragmentPresenter.loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initViews() {
        layout_back=(RelativeLayout)view.findViewById(R.id.layout_back);
        layout_back.setVisibility(View.INVISIBLE);
        title=(TextView)view.findViewById(R.id.text_title);
        title.setText("设备");
        addDriver=(TextView)view.findViewById(R.id.txt_add_driver);
        addDriver.setOnClickListener(this);
        recycleView= (RecyclerView) view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.layout_ble).setOnClickListener(this);
        view.findViewById(R.id.layout_ble_scan).setOnClickListener(this);
        view.findViewById(R.id.layout_more).setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_ble:
            case R.id.txt_add_driver:
                homeFragmentPresenter.onQrClick();
                break;
            case R.id.layout_ble_scan:
                homeFragmentPresenter.onScanClick();
                break;
            case R.id.layout_more:
                ((Main2Activity)getActivity()).getViewPager().setCurrentItem(2, false);

        }
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
