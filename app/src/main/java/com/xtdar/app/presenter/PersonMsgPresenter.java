package com.xtdar.app.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.PersonMsgAdapter;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.server.response.PersonMsgResponse;
import com.xtdar.app.view.activity.NewsDetailActivity;
import com.xtdar.app.view.activity.ShowDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

import static com.xtdar.app.presenter.MinePresenter.UPDATEUNREAD;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class PersonMsgPresenter extends BasePresenter implements OnDataListener,PersonMsgAdapter.ItemClickHandler {
    private static final int GETLIST = 1;
    private PersonMsgAdapter adapter;
    private ListView listView;
    private List<PersonMsgResponse.DataBean> list=new ArrayList<>();

    public PersonMsgPresenter(Context context){
        super(context);
        adapter = new PersonMsgAdapter(context);
        adapter.setmList(list);
        adapter.setOnItemClick(this);

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
                return mUserAction.getPersonMsgList("30","0");

        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETLIST:
                PersonMsgResponse personMsgResponse=(PersonMsgResponse)result;
                if (personMsgResponse !=null &&personMsgResponse.getCode() == XtdConst.SUCCESS ) {
                    if(personMsgResponse.getData().size()>0){
                        this.listView.setVisibility(View.VISIBLE);
                        list.addAll(personMsgResponse.getData());
                        adapter.notifyDataSetChanged();
                    }

                }
                BroadcastManager.getInstance(context).sendBroadcast(UPDATEUNREAD,"updateUnread");
                break;
        }
    }

    @Override
    public boolean onItemClick(int position, View view, PersonMsgResponse.DataBean status) {
        switch (status.getLinkObj().getTo())
        {
            case "show_detail":
                ShowDetailActivity.StartActivity(context,status.getLinkObj().getKey_val(),status.getLinkObj().getKey_val());
                break;
            case "recommend_detail":
            NewsDetailActivity.StartActivity(context,status.getLinkObj().getKey_val());
                break;

        }

        return false;
    }

}