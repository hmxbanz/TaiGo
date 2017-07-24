package com.xtdar.app.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.SysMsgAdapter;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.SysMsgResponse;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class SysMsgPresenter extends BasePresenter implements OnDataListener,SysMsgAdapter.OnItemButtonClick {
    private static final int GETLIST = 1;
    private static final int DELFAVOR = 2;
    private SysMsgAdapter adapter;
    private ListView listView;
    private List<SysMsgResponse.DataBean> list=new ArrayList<>();
    private String delFavorId;
    private int itemIndex;

    public SysMsgPresenter(Context context){
        super(context);
        adapter = new SysMsgAdapter(context);
        adapter.setmList(list);
        adapter.setOnItemButtonClick(this);

    }

    public void init(ListView listView) {
        this.listView=listView;
        this.listView.setAdapter(adapter);
        LoadDialog.show(context);
        atm.request(GETLIST,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETLIST:
                return mUserAction.getSysMsgList("10","0");

        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETLIST:
                SysMsgResponse sysMsgResponse=(SysMsgResponse)result;
                if (sysMsgResponse !=null &&sysMsgResponse.getCode() == XtdConst.SUCCESS ) {
                    if(sysMsgResponse.getData().size()>0){
                        this.listView.setVisibility(View.VISIBLE);
                        list.addAll(sysMsgResponse.getData());
                        adapter.notifyDataSetChanged();
                    }

                }
                break;

        }
    }

    @Override
    public boolean onItemClick(int position, View view, String status) {
        DetailActivity.StartActivity(context,status,status);
        return false;
    }

    @Override
    public boolean onButtonClick(int position, View view, String status) {

        return false;
    }
}