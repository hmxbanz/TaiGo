package com.xtdar.app.presenter;

import android.content.Context;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class UnityPlayerPresenter extends BasePresenter implements OnDataListener {
    private static final int UPLOADSCORE = 1;
    private UnityPlayerActivity mActivity;
    private String serverGameId,score;

    public UnityPlayerPresenter(Context context){
        super(context);
        mActivity = (UnityPlayerActivity) context;
    }

    public void init() {
        LoadDialog.show(context);
    }
    public void uploadScore(String serverGameId,String score) {
        this.serverGameId=serverGameId;
        this.score=score;
        atm.request(UPLOADSCORE,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return   mUserAction.unityUploadScore(serverGameId,score);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        CommonResponse commonResponse=(CommonResponse)result;
        if (commonResponse != null && commonResponse.getCode() == XtdConst.SUCCESS) {
            NToast.shortToast(mActivity,commonResponse.getMsg());
        }
    }
}