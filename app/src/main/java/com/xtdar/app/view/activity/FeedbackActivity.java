package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.presenter.FeedBackPresenter;

import com.xtdar.app.R;


public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private EditText reportContent,cellphone;
    private Button mBtnRport;
    private FeedBackPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initViews();
        presenter = new FeedBackPresenter(this);
        presenter.init();
    }

    private void initViews() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        mBtnRport =(Button)findViewById(R.id.btn_report);
        mBtnRport.setOnClickListener(this);

        reportContent = (EditText) findViewById(R.id.report_content);
        cellphone = (EditText) findViewById(R.id.cellphone);
        txtTitle = (TextView) findViewById(R.id.text_title);
        txtTitle.setText("用户反馈");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.btn_report:
                presenter.submit(reportContent,cellphone);
                break;

        }
    }
}
