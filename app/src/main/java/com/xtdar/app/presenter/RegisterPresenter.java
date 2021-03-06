package com.xtdar.app.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.RegisterActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;


public class RegisterPresenter extends BasePresenter {
    private static final int GETCAPTCHA = 1;
    private static final int REGISTER = 2;
    private String nickname;
    private final String headimgurl;
    private RegisterActivity mActivity;
    private TextView mBtnCaptcha;
    private String userName;
    private String password;
    private String captcha;

    public RegisterPresenter(Context context){
        super(context);
        mActivity = (RegisterActivity) context;
        Intent fromIntent=((RegisterActivity) context).getIntent();
        nickname = fromIntent.getStringExtra("nickname");
        headimgurl = fromIntent.getStringExtra("headimgurl");
    }

    public void init() {
    }

    public void getCaptcha(TextView btnCaptcha, EditText userName) {
        this.mBtnCaptcha=btnCaptcha;

        this.userName = userName.getText().toString();

        if(TextUtils.isEmpty(this.userName))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        timer.start();//开始倒计时60秒
        LoadDialog.show(mActivity);
        atm.request(GETCAPTCHA,this);
    }
    public void register(CheckBox checkBox, EditText cellphone, EditText password, EditText captcha) {
        Boolean check=checkBox.isChecked();
        this.userName = cellphone.getText().toString();
        this.password = password.getText().toString();
        this.captcha = captcha.getText().toString();
        if(this.nickname==null)
            this.nickname=this.userName;
        if(!check)
        {
            NToast.shortToast(context, R.string.protocol_not_checked);
            return;
        }
        if(TextUtils.isEmpty(this.userName))
        {
            NToast.shortToast(context, R.string.phone_number_be_null);
            return;
        }
        if (TextUtils.isEmpty(this.password)) {
            NToast.shortToast(context, R.string.password_be_null);
            return;
        }
        if (this.password.contains(" ")) {
            NToast.shortToast(context, R.string.password_cannot_contain_spaces);
            return;
        }
        if (TextUtils.isEmpty(this.captcha)) {
            NToast.shortToast(context, R.string.captcha_cannot_be_null);
            return;
        }

        LoadDialog.show(mActivity);
        atm.request(REGISTER,this);

    }
    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case GETCAPTCHA:
                return mUserAction.getCaptcha(this.userName);
            case REGISTER:
                return mUserAction.register(headimgurl,nickname,this.password,this.captcha);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETCAPTCHA:
                CommonResponse commonResponse = (CommonResponse) result;
                NToast.shortToast(context,commonResponse.getMsg());
                break;
            case REGISTER:
                CommonResponse commonResponse2 = (CommonResponse) result;
                if (commonResponse2.getCode() == XtdConst.SUCCESS) {
                    DialogWithYesOrNoUtils.getInstance().showDialog(context, "注册成功", null,null, new DialogWithYesOrNoUtils.DialogCallBack() {
                        @Override
                        public void executeEvent() {
                            context.startActivity(new Intent(context, Main2Activity.class));
                        }

                        @Override
                        public void onCancle() {

                        }
                    });
                }
                NToast.shortToast(context,commonResponse2.getMsg());
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
