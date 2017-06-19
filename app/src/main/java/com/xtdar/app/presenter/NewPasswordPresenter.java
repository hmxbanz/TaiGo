package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.view.activity.NewPasswordActivity;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class NewPasswordPresenter {
    Context mContext;
    NewPasswordActivity mActivity;
    public NewPasswordPresenter(Context context) {
        this.mContext=context;
        this.mActivity= (NewPasswordActivity) context;
    }
    public void init(){
    };
}
