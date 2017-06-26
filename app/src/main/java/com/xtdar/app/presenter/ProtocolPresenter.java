package com.xtdar.app.presenter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.view.activity.ProtocolActivity;
import com.xtdar.app.view.widget.LoadDialog;



/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ProtocolPresenter extends BasePresenter implements OnDataListener {
    private static final int GETPROTOCOCOL = 1;
    private ProtocolActivity mActivity;
    private TextView txtProtocol;
    public ProtocolPresenter(Context context){
        super(context);
        mActivity = (ProtocolActivity) context;
    }

    public void init() {
        txtProtocol = (TextView) mActivity.findViewById(R.id.txt_protocol);
        LoadDialog.show(context);
        atm.request(GETPROTOCOCOL,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.getProtocol();
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        txtProtocol.setText(Html.fromHtml(result.toString()));
    }
}
