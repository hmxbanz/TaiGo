package com.xtdar.app.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.MePresenter;
import com.xtdar.app.view.widget.SelectableRoundedImageView;


public class MeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLayoutAbout,mLayoutClear, mLayoutModifyPass;
    private MePresenter mMePresenter;

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private Uri selectUri;
    private SelectableRoundedImageView selectableRoundedImageView;
    private LinearLayout layoutUser;
    private TextView txtFindPwd;
    private TextView txtNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initViews();
        mMePresenter =new MePresenter(this);
        mMePresenter.init(selectableRoundedImageView, txtNickName,txtFindPwd);
    }

    public void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);

        mLayoutModifyPass = (LinearLayout) findViewById(R.id.layout_modify_pass);
        mLayoutModifyPass.setOnClickListener(this);

        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("资料");
        selectableRoundedImageView = (SelectableRoundedImageView) findViewById(R.id.img_avator);
        selectableRoundedImageView.setOnClickListener(this);
        layoutUser = (LinearLayout) findViewById(R.id.layout_user);
        layoutUser.setOnClickListener(this);
        txtFindPwd = (TextView) findViewById(R.id.txt_find_pwd);
        txtFindPwd.setOnClickListener(this);

        txtNickName = (TextView) findViewById(R.id.nick_name);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.txt_find_pwd:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.img_avator:
                mMePresenter.showPhotoDialog();
                break;
            case R.id.layout_user:
                startActivity(new Intent(this,UpdateActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mMePresenter.onActivityResult(requestCode, resultCode, data);
    }


}
