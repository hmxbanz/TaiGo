package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.HelpAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.HelpResponse;
import com.xtdar.app.view.activity.HelpDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HelpPresenter extends BasePresenter implements OnDataListener,HelpAdapter.OnItemClick {
    private static final int GETHELP = 1;
    private ListView listView;
    private List<HelpResponse.DataBean> list=new ArrayList<>();
    private HelpAdapter adapter;


    //private HelpActivity activity;
    public HelpPresenter(Context context) {
        super(context);
        adapter=new HelpAdapter(context);
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
            list.addAll(response.getData());
            adapter.setmList(list);
            adapter.setOnItemClick(this);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        NToast.shortToast(context,response.getMsg());
    }

    @Override
    public boolean onClick(int position, View view, String status) {
        Intent intent = new Intent(context, HelpDetailActivity.class);
        intent.putExtra("title","帮助详情");
        intent.putExtra("url",XtdConst.SERVERURI+"cli-dgc-helpdetail.php?article_id="+status);
        context.startActivity(intent);
        return false;
    }
}