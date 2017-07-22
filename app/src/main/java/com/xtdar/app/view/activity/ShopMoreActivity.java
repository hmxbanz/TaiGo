package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.ShopMorePresenter;


public class ShopMoreActivity extends BaseActivity implements View.OnClickListener {
    private ShopMorePresenter presenter;
    private String deviceTtypeId;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_more);
        initViews();
        presenter =new ShopMorePresenter(this);
        presenter.init(deviceTtypeId,recyclerView);
    }
    public void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("更多设备");
        recyclerView=(RecyclerView)findViewById(R.id.shop_more_recyclerview);

        Intent comingIntent = getIntent();
        deviceTtypeId = comingIntent.getStringExtra("deviceTtypeId");

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
