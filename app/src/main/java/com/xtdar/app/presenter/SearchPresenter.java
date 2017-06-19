package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.view.activity.SearchActivity;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class SearchPresenter {
    private Context mContext;
    private SearchActivity mActivity;

    public SearchPresenter(Context context){
        this.mContext=context;
        this.mActivity= (SearchActivity) context;
    }

    public void init() {
    }
}
