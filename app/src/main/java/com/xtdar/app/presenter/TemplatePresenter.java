package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.view.activity.SearchActivity;
import com.xtdar.app.view.activity.TemplateActivity;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class TemplatePresenter {
    private Context mContext;
    private TemplateActivity mActivity;

    public TemplatePresenter(Context context){
        this.mContext=context;
        this.mActivity= (TemplateActivity) context;
    }

    public void init() {
    }
}
