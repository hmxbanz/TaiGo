package com.xtdar.app.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.common.PhotoUtils;
import com.xtdar.app.presenter.MePresenter;
import com.xtdar.app.view.widget.BottomMenuDialog;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.view.widget.SelectableRoundedImageView;


public class MeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLayoutAbout,mLayoutClear, mLayoutModifyPass;
    private MePresenter mMePresenter;

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private Uri selectUri;
    private SelectableRoundedImageView selectableRoundedImageView;
    private TextView nickName;
    private TextView txtFindPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        initViews();
        mMePresenter =new MePresenter(this);
        mMePresenter.init(selectableRoundedImageView,nickName);
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
        nickName = (TextView) findViewById(R.id.nick_name);
        nickName.setOnClickListener(this);
        txtFindPwd = (TextView) findViewById(R.id.txt_find_pwd);
        txtFindPwd.setOnClickListener(this);
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mMePresenter.onActivityResult(requestCode, resultCode, data);
    }


}
