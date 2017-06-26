package com.xtdar.app.presenter;

import android.content.Context;
import android.widget.ListView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.HelpResponse;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HelpPresenter extends BasePresenter implements OnDataListener {
    private static final int GETHELP = 1;
    private ListView listView;
    private List<HelpResponse> list;


    //private HelpActivity activity;
    public HelpPresenter(Context context) {
        super(context);
        //activity = (HelpActivity) context;
    }

    public void init(ListView listView) {
        this.listView = listView;
        LoadDialog.show(context);
        atm.request(GETHELP, this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.getHelps();
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);

        HelpResponse response = (HelpResponse) result;
        if (response != null && response.getCode() == XtdConst.SUCCESS) {
            NToast.shortToast(context,response.getMsg());
        }
    }
}