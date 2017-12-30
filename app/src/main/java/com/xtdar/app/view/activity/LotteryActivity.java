package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtdar.app.R;
import com.xtdar.app.common.NToast;
import com.xtdar.app.presenter.LotteryPresenter;
import com.xtdar.app.presenter.SettingPresenter;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.xtdar.app.widget.LotteryPopDialog;
import com.xtdar.app.widget.VerticalScrollTextView;
import com.xtdar.app.widget.lottery.LotteryDisk;

import java.util.ArrayList;
import java.util.List;


public class LotteryActivity extends BaseActivity implements View.OnClickListener {
    private LotteryDisk lotteryPlate;
    private ImageView imgGo;
    private static final String TAG = LotteryActivity.class.getSimpleName();
    private LotteryPresenter presenter;
    private VerticalScrollTextView vTextView;
    private TextView txtTips;
    private TextView txtLotteryRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        initViews();
        presenter = new LotteryPresenter(this);
        presenter.init(lotteryPlate,vTextView);
        presenter.loadData(txtTips);
    }
    public void initViews(){
        txtTips = (TextView) findViewById(R.id.txt_tips);
        txtLotteryRule = (TextView) findViewById(R.id.txt_lottery_rule);
        vTextView = (VerticalScrollTextView) findViewById(R.id.v_text_view);
        txtLotteryRule.setOnClickListener(this);
        lotteryPlate=(LotteryDisk) findViewById(R.id.lottery_plate);
        imgGo = (ImageView) findViewById(R.id.lottery_start);
        imgGo.setOnClickListener(this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lottery_start:
                presenter.startLottery();
                break;
            case R.id.txt_lottery_rule:
                presenter.getLotteryRule();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lottery_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_lottery_prize:
                startActivity(new Intent(this,LotteryPrizeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        if (vTextView != null) {
//            vTextView.startPlay();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (vTextView != null) {
            vTextView.stopPlay();
        }
    }

}

