package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.xtdar.app.common.CommonTools;
import com.xtdar.app.view.activity.GuideActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.StartActivity;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class StartPresenter {
    private Context mContext;
    private StartActivity mActivity;
    private Handler hand = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(mActivity,Main2Activity.class);
            mActivity.startActivity(intent);
            mActivity.finish();
        }
    };
    public StartPresenter(Context context){
        this.mContext=context;
        mActivity = (StartActivity) context;
    }

    public void init() {
        if (CommonTools.isFristRun(mActivity)) {
            Intent intent = new Intent(mActivity,GuideActivity.class);
            mActivity.startActivity(intent);
            mActivity.finish();
            return;
        }

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Message msg = hand.obtainMessage();
                hand.sendMessage(msg);
            }
        }.start();
    }

}
