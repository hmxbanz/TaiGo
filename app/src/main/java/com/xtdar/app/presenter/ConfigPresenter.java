package com.xtdar.app.presenter;

import android.content.Context;
import com.xtdar.app.view.activity.SettingActivity;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ConfigPresenter {
    Context mContext;
    SettingActivity mActivity;

    public ConfigPresenter(Context context) {
        this.mActivity =(SettingActivity)context;
        this.mContext=context;
    }
    public void init(){
    //mView.initData();
};


}
