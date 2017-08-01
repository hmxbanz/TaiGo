package com.xtdar.app.presenter;

import android.content.Context;
import android.widget.EditText;

import com.xtdar.app.XtdConst;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.view.activity.FeedbackActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ReportPresenter extends BasePresenter{
    private static final int FEEDBACK = 1;
    private Context mContext;
    private FeedbackActivity mActivity;
    private EditText reportContent,cellphone;

    public ReportPresenter(Context context){
        super(context);
        this.mActivity = (FeedbackActivity) context;
    }

    public void init() {
    }

    public void submit(EditText reportContent, EditText cellphone) {
        this.reportContent=reportContent;
        this.cellphone = cellphone;
        LoadDialog.show(context);
        atm.request(FEEDBACK, this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.feedback(this.reportContent,this.cellphone);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        CommonResponse commonResponse = (CommonResponse) result;
        if(commonResponse !=null && commonResponse.getCode()== XtdConst.SUCCESS)
        {
            DialogWithYesOrNoUtils.getInstance().showDialog(context,"反馈成功",null,null,null);
        }
    }
}
