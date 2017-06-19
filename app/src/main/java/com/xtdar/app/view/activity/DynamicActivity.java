package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.DynamicPresenter;

public class DynamicActivity extends BaseActivity implements View.OnClickListener {
    private EditText mContent;
    private Button mBtnSubmit;
    private DynamicPresenter mDynamicPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        initViews();
        mDynamicPresenter = new DynamicPresenter(this);
        mDynamicPresenter.init();
    }

    private void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle = (TextView) findViewById(R.id.text_title);
        txtTitle.setText("发布");
        mContent = (EditText) findViewById(R.id.content);
        mContent.setOnClickListener(this);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(this);
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
