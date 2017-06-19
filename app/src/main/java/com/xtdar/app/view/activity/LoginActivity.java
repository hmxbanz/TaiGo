package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.presenter.LoginPresenter;

import com.xtdar.app.R;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mUsername,mPassword;
    private Button mBtnLogin;
    private RelativeLayout mLayoutRegister;
    private TextView mTextForgetPassword;
    private LoginPresenter mLoginPresenter;
    private TextView mTextRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        mLoginPresenter = new LoginPresenter(this);
        mLoginPresenter.init(mUsername,mPassword);
    }

    private void initViews() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("登录");
        mTextRight =(TextView) findViewById(R.id.text_right);
        mTextRight.setText("注册");
        mTextRight.setOnClickListener(this);


        mTextForgetPassword = (TextView) findViewById(R.id.text_forget_password);
        mLayoutRegister = (RelativeLayout) findViewById(R.id.layout_register);
        mLayoutRegister.setOnClickListener(this);
        mTextForgetPassword.setOnClickListener(this);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.text_right:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                mLoginPresenter.login();
                break;
            case R.id.text_forget_password:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
        }
    }
}
