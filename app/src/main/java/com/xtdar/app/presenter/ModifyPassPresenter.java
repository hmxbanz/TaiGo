package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.view.activity.ModifyPassActivity;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ModifyPassPresenter {
    Context mContext;
    ModifyPassActivity mActivity;
    public ModifyPassPresenter(Context context) {
        this.mContext=context;
        this.mActivity= (ModifyPassActivity) context;
    }
    public void init(){
    };
}
