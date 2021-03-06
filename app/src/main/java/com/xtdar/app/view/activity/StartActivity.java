package com.xtdar.app.view.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.jaeger.library.StatusBarUtil;
import com.xtdar.app.presenter.StartPresenter;

import com.xtdar.app.R;

/**
 * Created by Administrator on 2015/10/3.
 */
public class StartActivity extends BaseActivity {
    private StartPresenter mStartPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mStartPresenter = new StartPresenter(this);
        mStartPresenter.init();
    }

}
