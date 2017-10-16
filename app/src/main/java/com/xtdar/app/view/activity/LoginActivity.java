package com.xtdar.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xtdar.app.presenter.LoginPresenter;

import com.xtdar.app.R;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mUsername,mPassword;
    private Button mBtnLogin;
    private RelativeLayout mLayoutQqLogin,mLayoutWxLogin,mLayoutWeiboWxLogin;
    private TextView mTextForgetPassword;
    private LoginPresenter presenter;
    private TextView mTextRight;
    private String openId,loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        presenter = new LoginPresenter(this);
        presenter.init(mUsername,mPassword);
        presenter.openId =openId;
        presenter.loginType=loginType;
    }


    public static void StartActivity(Context context, String openId, String type) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("openId",openId);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    private void initViews() {
        Intent intent=getIntent();
        openId = intent.getStringExtra("openId");
        loginType = intent.getStringExtra("type");

        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        mTextRight =(TextView) findViewById(R.id.text_right);

        mTextRight.setOnClickListener(this);


        mTextForgetPassword = (TextView) findViewById(R.id.text_forget_password);
        mLayoutQqLogin = (RelativeLayout) findViewById(R.id.layout_qq_login);
        mLayoutQqLogin.setOnClickListener(this);
        mLayoutWxLogin = (RelativeLayout) findViewById(R.id.layout_wx_login);
        mLayoutWxLogin.setOnClickListener(this);
//        mLayoutWeiboWxLogin = (RelativeLayout) findViewById(R.id.layout_weibo_login);
//        mLayoutWeiboWxLogin.setOnClickListener(this);
        mTextForgetPassword.setOnClickListener(this);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

        if(TextUtils.isEmpty(openId)){
            txtTitle.setText("登录");
            mTextRight.setText("注册");
        }
        else
        {
            txtTitle.setText("绑定");
            mBtnLogin.setText("绑定");
        }
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
                if(TextUtils.isEmpty(openId)){
                    presenter.login("nomal");  return;
                    }
                presenter.login("bind");
                break;
            case R.id.text_forget_password:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.layout_wx_login:
                presenter.wxLogin();
                break;
            case R.id.layout_qq_login:
                presenter.qqLogin();
                break;
//            case R.id.layout_weibo_login:
//                presenter.weiboLogin();
//                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //presenter.unbindService();
    }
}
