package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.presenter.UpdatePresenter;

import com.xtdar.app.R;
import cn.qqtheme.framework.picker.OptionPicker;

public class UpdateActivity extends BaseActivity implements View.OnClickListener {
    public TextView text_age,text_right;
    private UpdatePresenter mUpdatePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initViews();
        mUpdatePresenter = new UpdatePresenter(this);
        mUpdatePresenter.init();
    }

    private void initViews() {
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        text_age = (TextView) findViewById(R.id.text_body_height);
        text_age.setOnClickListener(this);
        text_right = (TextView) findViewById(R.id.text_right);
        text_right.setText("保存");
        text_right.setOnClickListener(this);
        txtTitle = (TextView) findViewById(R.id.text_title);
        txtTitle.setText("修改资料");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.btn_age:
                String[] ageRange=new String[]{"身份证", "身份证", "身份证","身份证"};
                OptionPicker picker = new OptionPicker(this,ageRange );
                picker.setOffset(1);
                picker.setSelectedIndex(1);
                picker.setTextSize(14);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int position, String option) {
                        text_age.setText(option);
                    }
                });
                picker.show();
                break;
        }

    }
}
