package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.xtdar.app.R;
import com.xtdar.app.common.NToast;
import com.xtdar.app.presenter.ScorePresenter;

public class ScoreActivity extends BaseActivity {

    private ScorePresenter scorePresenter;
    private TextView txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        findViewById(R.id.layout_detail).setOnClickListener(this);
        findViewById(R.id.layout_lottery).setOnClickListener(this);
        findViewById(R.id.layout_singin).setOnClickListener(this);
        txtScore = (TextView) findViewById(R.id.txt_score);
        findViewById(R.id.txt_query).setOnClickListener(this);
        findViewById(R.id.txt_sign).setOnClickListener(this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("我的积分");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        scorePresenter=new  ScorePresenter(this);
}

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.titleBlue),0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        scorePresenter.init(txtScore);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_detail:
                startActivity(new Intent(this, ScoreDetailActivity.class));
                break;
            case R.id.layout_lottery:
                startActivity(new Intent(this, LotteryActivity.class));
                break;
            case R.id.layout_singin:
            case R.id.txt_sign:
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.txt_query:
                startActivity(new Intent(this, LotteryPrizeActivity.class));
                break;
        }
    }
}
