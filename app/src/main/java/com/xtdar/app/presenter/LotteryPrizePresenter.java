package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.LotteryPrizeAdapter;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.LotteryPrizeResponse;
import com.xtdar.app.view.activity.LotteryPrizeActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.myRecyclerView.LoadMoreListener;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class LotteryPrizePresenter extends BasePresenter implements OnDataListener, LoadMoreListener {
    private static final int GETPRIZELIST = 1;
    private final GridLayoutManager gridLayoutManager;
    private LotteryPrizeActivity mActivity;
    private RecyclerViewUpRefresh recycleView;
    private LotteryPrizeAdapter adapter;
    private List<LotteryPrizeResponse.DataBean> list=new ArrayList<>();
    private String lastHisId="0";

    public LotteryPrizePresenter(Context context){
        super(context);
        mActivity = (LotteryPrizeActivity) context;
        adapter = new LotteryPrizeAdapter(mActivity);
        gridLayoutManager=new GridLayoutManager(context,1);
    }

    public void init(RecyclerViewUpRefresh recycleView) {
        atm.request(GETPRIZELIST,this);
        this.recycleView=recycleView;
        this.recycleView.setLayoutManager(gridLayoutManager);
        this.recycleView.setAdapter(adapter);
        this.recycleView.setLoadMoreListener(this);

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETPRIZELIST:
                return mUserAction.getPrizeList(lastHisId);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(mActivity);
        if(result==null)return;
        switch (requestCode) {
            case GETPRIZELIST:
                LotteryPrizeResponse prizeResponse=(LotteryPrizeResponse)result;
                if (prizeResponse.getCode() == XtdConst.SUCCESS) {
                    lastHisId = prizeResponse.getData().get(prizeResponse.getData().size() - 1).getHis_id();
                    list.addAll(prizeResponse.getData());
                    adapter.setListItems(list);
                    adapter.notifyDataSetChanged();
                    recycleView.loadMoreComplete();
                }
        }
    }

    @Override
    public void onLoadMore() {
        atm.request(GETPRIZELIST,this);
    }
}