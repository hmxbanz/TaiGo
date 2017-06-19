package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.NewPasswordPresenter;


public class NewPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText mPassword,mPasswordAgain;
    private Button mBtnChangePassword;
    private NewPasswordPresenter mNewPasswordPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        initViews();
        mNewPasswordPresenter = new NewPasswordPresenter(this);
        mNewPasswordPresenter.init();
    }
    public void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle = (TextView) findViewById(R.id.text_title);
        txtTitle.setText("更改密码");
        mPassword = (EditText) findViewById(R.id.password);
        mPasswordAgain = (EditText) findViewById(R.id.password_again);
        mBtnChangePassword = (Button) findViewById(R.id.btn_new_pass);
        mBtnChangePassword.setOnClickListener(this);
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
