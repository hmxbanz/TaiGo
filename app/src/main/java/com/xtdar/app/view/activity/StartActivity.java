package com.xtdar.app.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.xtdar.app.presenter.StartPresenter;

import com.xtdar.app.R;

/**
 * Created by Administrator on 2015/10/3.
 */
public class StartActivity extends Activity {
    private StartPresenter mStartPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStartPresenter = new StartPresenter(this);
        mStartPresenter.init();

        setContentView(R.layout.activity_start);
    }

}
