package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.common.NToast;
import com.xtdar.app.presenter.ScorePresenter;

public class ScoreActivity extends BaseActivity {

    private LinearLayout layoutDetail, layoutLottery, layoutSingIn;
    private ScorePresenter scorePresenter;
    private TextView txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        layoutDetail = (LinearLayout) findViewById(R.id.layout_detail);
        layoutLottery = (LinearLayout) findViewById(R.id.layout_lottery);
        layoutSingIn = (LinearLayout) findViewById(R.id.layout_singin);
        layoutDetail.setOnClickListener(this);
        layoutLottery.setOnClickListener(this);
        layoutSingIn.setOnClickListener(this);
        txtScore = (TextView) findViewById(R.id.txt_score);

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
                startActivity(new Intent(this, SignInActivity.class));
                break;
        }
    }
}
