package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.Interface.IFavorView;
import com.xtdar.app.R;
import com.xtdar.app.presenter.FavorPresenter;
import com.xtdar.app.presenter.MyVideoPresenter;

public class MyVideoActivity extends BaseActivity implements IFavorView,View.OnClickListener {
    private MyVideoPresenter myVideoPresenter;
    RecyclerView videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        initViews();
        myVideoPresenter=new MyVideoPresenter(this);
        myVideoPresenter.init(videoList);
    }
    public void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("我的视频");
        videoList= (RecyclerView) findViewById(R.id.list_item_recycler);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
        }
    }

    @Override
    public void initData() {
        txtTitle.setText("我的视频");
    }

}
