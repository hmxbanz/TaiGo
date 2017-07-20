package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v4.util.ArraySet;
import android.view.View;
import android.widget.ListView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.MyCommentAdapter;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.MyCommentResponse;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.activity.ShowDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class CommentPresenter extends BasePresenter implements OnDataListener ,MyCommentAdapter.ItemClickHandler {
    private static final int GETCOMMENTLIST = 1;
    //private ContactsActivity mActivity;
    private MyCommentAdapter myCommentAdapter;
    private ListView listView;
    private List<MyCommentResponse.DataBean> list=new ArrayList<>();

    public CommentPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(ListView listView) {
        this.listView=listView;
        myCommentAdapter = new MyCommentAdapter(context);
        myCommentAdapter.setmList(list);
        myCommentAdapter.setOnItemClick(this);
        listView.setAdapter(myCommentAdapter);
        LoadDialog.show(context);
        atm.request(GETCOMMENTLIST,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETCOMMENTLIST:
                return mUserAction.getCommentList("10","0");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETCOMMENTLIST:
                MyCommentResponse myCommentResponse=(MyCommentResponse)result;
                if (myCommentResponse !=null &&myCommentResponse.getCode() == XtdConst.SUCCESS ) {
                    if(myCommentResponse.getData().size()>0){
                        this.listView.setVisibility(View.VISIBLE);
                        list.addAll(myCommentResponse.getData());
                        myCommentAdapter.notifyDataSetChanged();
                    }

                }
                break;
        }
    }

    @Override
    public boolean onItemClick(int position, View view, String status) {
        ShowDetailActivity.StartActivity(context,status,status);
        return false;
    }
}