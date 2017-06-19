package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.view.activity.FeedbackActivity;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ReportPresenter {
    private Context mContext;
    private FeedbackActivity mActivity;
    public ReportPresenter(Context context){
        this.mContext=context;
        this.mActivity = (FeedbackActivity) context;
    }

    public void init() {
    }
}
