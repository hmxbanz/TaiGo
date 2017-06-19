package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.view.activity.UpdateActivity;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class UpdatePresenter {
    private Context mContext;
    private UpdateActivity mActivity;
    public UpdatePresenter(Context context){
        this.mContext=context;
        this.mActivity = (UpdateActivity) context;
    }

    public void init() {
    }
}
