package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.LoginResponse;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.widget.LoadDialog;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class LoginPresenter extends BasePresenter{
    private static final int LOGIN = 1;
    private static final int GET_TOKEN = 2;
    private LoginActivity mActivity;
    private EditText mUsername;
    private EditText mPassword;
    public LoginPresenter(Context context){
        super(context);
        mActivity = (LoginActivity) context;
    }

    public void init(EditText username, EditText password) {
        this.mUsername=username;
        this.mPassword=password;
    }
    public void login() {
        if(TextUtils.isEmpty(mUsername.getText().toString()))
        {
            NToast.shortToast(mContext, R.string.phone_number_be_null);
            return;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            NToast.shortToast(mContext, R.string.password_be_null);
            return;
        }
        if (mPassword.getText().toString().contains(" ")) {
            NToast.shortToast(mContext, R.string.password_cannot_contain_spaces);
            return;
        }
        LoadDialog.show(mContext);
        atm.request(LOGIN,this);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException {
        switch (requestCode) {
            case LOGIN:
                return mUserAction.login(mUsername.getText().toString(), mPassword.getText().toString());
            case GET_TOKEN:
                //return action.getToken();

        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(mContext);
        if (result != null) {
            switch (requestCode) {
                case LOGIN:
                    LoginResponse loginResponse = (LoginResponse) result;
                    if (loginResponse.getCode() == XtdConst.SUCCESS) {
                        LoginResponse.ResultEntity entity=loginResponse.getData();
                        editor.putString(XtdConst.ACCESS_TOKEN, entity.getAccess_key());
                        editor.putString(XtdConst.LOGIN_USERNAME, mUsername.getText().toString());
                        editor.putString(XtdConst.LOGING_PASSWORD, mPassword.getText().toString());

                        editor.putBoolean(XtdConst.ISLOGIN, true);
//                        editor.putString(XtdConst.USERID, entity.getUserID());
//                        editor.putString(XtdConst.USERINFOID, entity.getUserInfoID());
//                        editor.putString("iconSmall", entity.getIconSmall());
//                        editor.putString("checkName", entity.getCheckName());
                        editor.apply();

                        mContext.startActivity(new Intent(mContext,Main2Activity.class));
                    } else if (loginResponse.getCode() == XtdConst.FAILURE) {

                    }
                    NToast.shortToast(mContext, loginResponse.getMsg());
                    break;

            }
        }
    }
}
