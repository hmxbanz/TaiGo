package com.xtdar.app.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.xtdar.app.adapter.MyVideoAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.activity.MyVideoActivity;
import com.xtdar.app.view.activity.ShowDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MyVideoPresenter extends BasePresenter implements OnDataListener,MyVideoAdapter.OnItemButtonClick  {
    private static final int DELMYVIDEO = 2;
    Context mContext;
    MyVideoActivity mActivity;
    private static final int GETSHOWLIST = 1;
    private List<ShowResponse.DataBean> entities;
    private ListView listView;
    private MyVideoAdapter adapter;
    private String delIndexId;
    private int itemIndex;

    public MyVideoPresenter(Context context) {
        super(context);
        mActivity= (MyVideoActivity) context;
        adapter = new MyVideoAdapter(context);
        adapter.setOnItemButtonClick(this);
    }

    public void init(ListView listView) {
        this.listView =listView;
        this.listView.setAdapter(adapter);
        LoadDialog.show(context);
        atm.request(GETSHOWLIST,this);

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETSHOWLIST:
                return mUserAction.getMyVideo();
            case DELMYVIDEO:
                return mUserAction.delMyVideo(delIndexId);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETSHOWLIST:
                ShowResponse showResponse=(ShowResponse)result;
                if (showResponse != null && showResponse.getData() != null) {
                    entities=showResponse.getData();
                    adapter.setmList(entities);
                    adapter.notifyDataSetChanged();
                    this.listView.setVisibility(View.VISIBLE);
                }
                break;
            case DELMYVIDEO:
                CommonResponse commonResponse=(CommonResponse)result;
                NToast.shortToast(context,commonResponse.getMsg());
                entities.remove(itemIndex);
                adapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public boolean onItemClick(int position, View view, String status) {
        ShowDetailActivity.StartActivity(context,status,status);
        return false;
    }

    @Override
    public boolean onButtonClick(int position, View view, String status) {
        this.delIndexId =status;
        this.itemIndex=position;
        atm.request(DELMYVIDEO,this);
        return false;
    }

}
