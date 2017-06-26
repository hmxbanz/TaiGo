package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.TagResponse;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeFragmentPresenter extends BasePresenter implements OnDataListener {
    private static final int GETDRIVERS = 1;
    private List<TagResponse.DataBean> tagList;
    private RecyclerView recyclerView;

    //private ContactsActivity mActivity;
    public HomeFragmentPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
        LoadDialog.show(context);
        atm.request(GETDRIVERS,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETDRIVERS:
                return mUserAction.getTags();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETDRIVERS:
                TagResponse tagResponse = (TagResponse) result;
                if (tagResponse.getCode() == XtdConst.SUCCESS) {
                     tagList = tagResponse.getData();
                    if (tagList.size() > 0) {
                        this.recyclerView.setVisibility(View.GONE);
                    }
                }
                NToast.longToast(context,tagResponse.getMsg());
                break;
        }
    }
}