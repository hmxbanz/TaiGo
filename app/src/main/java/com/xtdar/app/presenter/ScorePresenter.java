package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.widget.TextView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ScoreResponse;
import com.xtdar.app.view.activity.HelpDetailActivity;
import com.xtdar.app.view.activity.ScoreActivity;
import com.xtdar.app.view.widget.LoadDialog;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ScorePresenter extends BasePresenter implements OnDataListener {
    private static final int GETSCORE = 1;
    private ScoreActivity mActivity;
    private TextView txtScore;
    private Object scoreRule;

    public ScorePresenter(Context context){
        super(context);
        mActivity = (ScoreActivity) context;
    }

    public void init(TextView txtScore) {
        this.txtScore=txtScore;
        LoadDialog.show(context);
        atm.request(GETSCORE, this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.getScore();
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);

        ScoreResponse scoreResponse=(ScoreResponse)result;
        if (scoreResponse != null && scoreResponse.getCode() == XtdConst.SUCCESS) {
            this.txtScore.setText(scoreResponse.getData().getScore());
        }

    }

}