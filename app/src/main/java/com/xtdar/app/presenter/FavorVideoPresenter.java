package com.xtdar.app.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.AnimFavorAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.FavorResponse;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class FavorVideoPresenter extends BasePresenter implements OnDataListener,AnimFavorAdapter.OnItemButtonClick {
    private static final int GETLIST = 1;
    private static final int DELFAVOR = 2;
    private AnimFavorAdapter animFavorAdapter;
    private ListView listView;
    private List<FavorResponse.DataBean> list=new ArrayList<>();
    private String delFavorId;
    private int itemIndex;

    public FavorVideoPresenter(Context context){
        super(context);
        animFavorAdapter = new AnimFavorAdapter(context);
        animFavorAdapter.setmList(list);
        animFavorAdapter.setOnItemButtonClick(this);

    }

    public void init(ListView listView) {
        this.listView=listView;
        this.listView.setAdapter(animFavorAdapter);
        LoadDialog.show(context);
        atm.request(GETLIST,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETLIST:
                return mUserAction.getFavorList("10","0");
            case DELFAVOR:
                return mUserAction.delFavor(delFavorId);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETLIST:
                FavorResponse favorResponse=(FavorResponse)result;
                if (favorResponse !=null &&favorResponse.getCode() == XtdConst.SUCCESS ) {
                    if(favorResponse.getData().size()>0){
                        this.listView.setVisibility(View.VISIBLE);
                        list.addAll(favorResponse.getData());
                        animFavorAdapter.notifyDataSetChanged();
                    }

                }
                break;
            case DELFAVOR:
                CommonResponse commonResponse=(CommonResponse)result;
                NToast.shortToast(context,commonResponse.getMsg());
                list.remove(itemIndex);
                animFavorAdapter.notifyDataSetChanged();
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
        this.delFavorId=status;
        this.itemIndex=position;
        atm.request(DELFAVOR,this);
        return false;
    }
}