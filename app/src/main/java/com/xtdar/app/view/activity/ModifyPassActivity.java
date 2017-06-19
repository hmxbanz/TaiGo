package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.ModifyPassPresenter;


public class ModifyPassActivity extends BaseActivity implements View.OnClickListener {
    private EditText mOldPassword,mPassword,mPasswordAgain;
    private Button mBtnModifyPass;
    private ModifyPassPresenter mModifyPassPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass);
        initViews();
        mModifyPassPresenter = new ModifyPassPresenter(this);
        mModifyPassPresenter.init();
    }
    public void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("设置密码");

        mOldPassword = (EditText) findViewById(R.id.old_pass);
        mPassword = (EditText) findViewById(R.id.password);
        mPasswordAgain = (EditText) findViewById(R.id.password_again);
        mBtnModifyPass = (Button) findViewById(R.id.btn_modify_pass);

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
