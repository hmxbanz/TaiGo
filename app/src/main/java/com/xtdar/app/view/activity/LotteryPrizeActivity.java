package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.adapter.LotteryPrizeAdapter;
import com.xtdar.app.presenter.LotteryPrizePresenter;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;


public class LotteryPrizeActivity extends BaseActivity implements View.OnClickListener {
    private LotteryPrizePresenter presenter;
    private RecyclerViewUpRefresh recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_prize);

        initViews();
        presenter = new LotteryPrizePresenter(this);
        presenter.init(recycleView);

    }

    private void initViews() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("获奖查询");
        recycleView = (RecyclerViewUpRefresh) findViewById(R.id.recyclerView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
        }
    }
}
