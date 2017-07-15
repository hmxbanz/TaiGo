package com.xtdar.app.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.MyCommentAdapter;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.MyCommentResponse;
import com.xtdar.app.view.widget.LoadDialog;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class CommentPresenter extends BasePresenter implements OnDataListener ,MyCommentAdapter.OnItemClick {
    private static final int GETCOMMENTLIST = 1;
    //private ContactsActivity mActivity;
    private MyCommentAdapter myCommentAdapter;
    private ListView listView;

    public CommentPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(ListView listView) {
        this.listView=listView;
        LoadDialog.show(context);
        atm.request(GETCOMMENTLIST,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETCOMMENTLIST:
                return mUserAction.getFavorList("10","0");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETCOMMENTLIST:
                MyCommentResponse favorResponse=(MyCommentResponse)result;
                if (favorResponse !=null &&favorResponse.getCode() == XtdConst.SUCCESS ) {
                    if(favorResponse.getData().size()>0){
                        this.listView.setVisibility(View.VISIBLE);
                        //list.addAll(favorResponse.getData());
                        //animFavorAdapter.notifyDataSetChanged();
                    }

                }
                break;
        }
    }

    @Override
    public boolean onClick(int position, View view, String status) {
        return false;
    }
}