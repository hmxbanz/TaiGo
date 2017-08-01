package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.presenter.UpdatePresenter;

import com.xtdar.app.R;

public class UpdateActivity extends BaseActivity implements View.OnClickListener {
    public TextView txt_right;
    private UpdatePresenter presenter;
    private EditText nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initViews();
        presenter = new UpdatePresenter(this);
        presenter.init();
    }

    private void initViews() {
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txt_right = (TextView) findViewById(R.id.text_right);
        txt_right.setText("保存");
        txt_right.setOnClickListener(this);
        txtTitle = (TextView) findViewById(R.id.text_title);
        txtTitle.setText("修改资料");
        nickName = (EditText) findViewById(R.id.nick_name);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.text_right:
                presenter.save(nickName);
                break;
        }

    }
}