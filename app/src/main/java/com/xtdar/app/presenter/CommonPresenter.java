package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.view.widget.LoadDialog;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class CommonPresenter extends BasePresenter implements OnDataListener {
    //private ContactsActivity mActivity;
    public CommonPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init() {
        LoadDialog.show(mContext);
    }


}