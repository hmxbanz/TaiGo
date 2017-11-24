package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.common.NToast;
import com.xtdar.app.presenter.ScoreDetailPresenter;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;


public class ScoreDetailActivity extends BaseActivity {

    private ScoreDetailPresenter scoreDetailPresenter;
    private TextView txtScore;
    private RecyclerViewUpRefresh recycleView;
    private ImageView iconCalendar;
    private LinearLayout layoutScoreRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);

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

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        collapsingToolbarLayout.setTitle("我的积分");

        txtScore = (TextView) findViewById(R.id.txt_score);
        recycleView= (RecyclerViewUpRefresh) findViewById(R.id.recyclerView);
        iconCalendar= (ImageView) findViewById(R.id.icon_calendar);
        iconCalendar.setOnClickListener(this);
        layoutScoreRule = (LinearLayout) findViewById(R.id.layout_score_rule);
        layoutScoreRule.setOnClickListener(this);
        scoreDetailPresenter=new ScoreDetailPresenter(this);
        scoreDetailPresenter.init(txtScore,recycleView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        scoreDetailPresenter.loadData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_calendar:
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.layout_detail:
                startActivity(new Intent(this, ScoreDetailActivity.class));
                break;
            case R.id.layout_lottery:
                NToast.longToast(this,"功能即将开放");
                break;
            case R.id.layout_singin:
                startActivity(new Intent(this, SignInActivity.class));
            case R.id.layout_score_rule:
                scoreDetailPresenter.getScoreRule();
        }
    }
}
