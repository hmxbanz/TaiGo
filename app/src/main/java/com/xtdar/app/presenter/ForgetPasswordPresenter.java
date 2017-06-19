package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.view.activity.ForgetPasswordActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;


public class ForgetPasswordPresenter extends BasePresenter {
        private static final int GETCAPTCHA = 1;
        private static final int RESETPWD = 2;
        private ForgetPasswordActivity mActivity;
        private TextView mBtnCaptcha;
        private String userName;
        private String password;
        private String captcha;

        public ForgetPasswordPresenter(Context context){
            super(context);
            mActivity = (ForgetPasswordActivity) context;
        }

        public void init() {
        }

        public void getCaptcha(TextView btnCaptcha, EditText userName) {
            this.mBtnCaptcha=btnCaptcha;

            this.userName = userName.getText().toString();

            if(TextUtils.isEmpty(this.userName))
            {
                NToast.shortToast(mContext, R.string.phone_number_be_null);
                return;
            }
            timer.start();//开始倒计时60秒
            LoadDialog.show(mActivity);
            atm.request(GETCAPTCHA,this);
        }
        public void resetPassword(EditText cellphone, EditText password, EditText captcha) {
            this.userName = cellphone.getText().toString();
            this.password = password.getText().toString();
            this.captcha = captcha.getText().toString();

            if(TextUtils.isEmpty(this.userName))
            {
                NToast.shortToast(mContext, R.string.phone_number_be_null);
                return;
            }
            if (TextUtils.isEmpty(this.password)) {
                NToast.shortToast(mContext, R.string.password_be_null);
                return;
            }
            if (this.password.contains(" ")) {
                NToast.shortToast(mContext, R.string.password_cannot_contain_spaces);
                return;
            }
            if (TextUtils.isEmpty(this.captcha)) {
                NToast.shortToast(mContext, R.string.captcha_cannot_be_null);
                return;
            }

            LoadDialog.show(mActivity);
            atm.request(RESETPWD,this);

        }
        @Override
        public Object doInBackground(int requestCode, String id) throws HttpException {
            switch (requestCode) {
                case GETCAPTCHA:
                    return mUserAction.getCaptchaForget(this.userName);
                case RESETPWD:
                    return mUserAction.resetPassword(this.userName,this.password,this.captcha);
            }
            return null;
        }

        @Override
        public void onSuccess(int requestCode, Object result) {
            LoadDialog.dismiss(mContext);
            switch (requestCode) {
                case GETCAPTCHA:
                    CommonResponse commonResponse = (CommonResponse) result;
                    NToast.shortToast(mContext,commonResponse.getMsg());
                    break;
                case RESETPWD:
                    CommonResponse commonResponse2 = (CommonResponse) result;
                    if (commonResponse2.getCode() == XtdConst.SUCCESS) {
                        DialogWithYesOrNoUtils.getInstance().showDialog(mContext, "密码重置成功", null, new AlertDialogCallback() {
                            @Override
                            public void executeEvent() {
                                mContext.startActivity(new Intent(mContext, Main2Activity.class));
                            }

                        });
                    }
                    NToast.shortToast(mContext,commonResponse2.getMsg());
                    break;
            }
        }

        private CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBtnCaptcha.setEnabled(false);
                mBtnCaptcha.setText((millisUntilFinished / 1000) + "秒后可重发");
            }
            @Override
            public void onFinish() {
                mBtnCaptcha.setEnabled(true);
                mBtnCaptcha.setText("获取验证码");
            }
        };
    }
