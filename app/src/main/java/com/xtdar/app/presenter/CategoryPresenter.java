package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.view.activity.CategoryActivity;
import com.xtdar.app.view.activity.MeActivity;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class CategoryPresenter {
    Context mContext;
    CategoryActivity mActivity;

    public CategoryPresenter(Context context) {
        this.mActivity =(CategoryActivity)context;
        this.mContext=context;
    }
    public void init(){
    //mView.initData();
    };

}
