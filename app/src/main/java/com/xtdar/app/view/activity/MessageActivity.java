package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.Interface.IFavorView;
import com.xtdar.app.R;
import com.xtdar.app.presenter.FavorPresenter;
import com.xtdar.app.presenter.MessagePresenter;

public class MessageActivity extends BaseActivity implements IFavorView,View.OnClickListener {
    private MessagePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initViews();
        presenter=new MessagePresenter(this);
        presenter.init();
    }
    public void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
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
        txtTitle.setText("消息中心");
    }

}
