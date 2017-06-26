package com.xtdar.app.presenter;

import android.content.Context;
import android.widget.EditText;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.HelpResponse;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.UpdateActivity;
import com.xtdar.app.view.widget.LoadDialog;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class UpdatePresenter extends BasePresenter{
    private static final int SAVE = 1;
    private UpdateActivity activity;
    private EditText nickName;

    public UpdatePresenter(Context context){
        super(context);
        this.activity = (UpdateActivity) context;
    }

    public void init() {
    }

    public void save(EditText nickName) {
        this.nickName = nickName;
        LoadDialog.show(context);
        atm.request(SAVE,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.save(this.nickName.getText().toString());
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);

        CommonResponse response = (CommonResponse) result;
        if (response != null && response.getCode() == XtdConst.SUCCESS) {
            BroadcastManager.getInstance(context).sendBroadcast(MePresenter.UPDATENICKNAME,this.nickName);
            //activity.finish();
        }
        NToast.shortToast(context,response.getMsg());

    }
}
