package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.presenter.ReportPresenter;

import com.xtdar.app.R;


public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private EditText mReportContent;
    private Button mBtnRport;
    private CheckBox mCheckboxViolence, mCheckboxPolitics, mCheckboxPorn, mCheckboxFraud, mCheckboxOther;
    private ReportPresenter mReportPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initViews();
        mReportPresenter = new ReportPresenter(this);
        mReportPresenter.init();
    }

    private void initViews() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        mBtnRport =(Button)findViewById(R.id.btn_report);
        mBtnRport.setOnClickListener(this);

        mReportContent = (EditText) findViewById(R.id.report_content);
        txtTitle = (TextView) findViewById(R.id.text_title);
        txtTitle.setText("用户反馈");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.layout_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;

        }
    }
}
