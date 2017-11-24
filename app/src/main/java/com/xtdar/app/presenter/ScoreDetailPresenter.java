package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.SignHistoryAdapter;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ScoreResponse;
import com.xtdar.app.server.response.SignHistoryResponse;
import com.xtdar.app.view.activity.HelpDetailActivity;
import com.xtdar.app.view.activity.ScoreDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ScoreDetailPresenter extends BasePresenter implements OnDataListener {
    private static final int GETSCORE = 1;
    private static final int GETHISTORY = 2;
    private ScoreDetailActivity mActivity;
    private TextView txtScore;
    private final GlideImageLoader glideImageLoader;
    private GridLayoutManager gridLayoutManager;
    private SignHistoryAdapter signHistoryAdapter;
    private RecyclerViewUpRefresh recycleView;


    public ScoreDetailPresenter(Context context){
        super(context);
        mActivity = (ScoreDetailActivity) context;
        glideImageLoader = new GlideImageLoader();
        gridLayoutManager=new GridLayoutManager(context,1);
        signHistoryAdapter = new SignHistoryAdapter(context);
    }

    public void init(TextView txtScore, RecyclerViewUpRefresh recycleView) {
        this.txtScore=txtScore;
        this.recycleView=recycleView;
        recycleView.setAdapter(signHistoryAdapter);
        recycleView.setLayoutManager(gridLayoutManager);
        loadData();
    }

    public void loadData() {
        LoadDialog.show(context);
        atm.request(GETSCORE, this);
    }


    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETSCORE :
                return mUserAction.getScore();
            case GETHISTORY :
                return mUserAction.getHistory("2017-1-1","2017-12-12");
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);

        switch (requestCode) {
            case GETSCORE :
                ScoreResponse scoreResponse=(ScoreResponse)result;
                if (scoreResponse != null && scoreResponse.getCode() == XtdConst.SUCCESS) {
                    this.txtScore.setText(scoreResponse.getData().getScore());

                    LoadDialog.show(context);
                    atm.request(GETHISTORY, this);
                }
                break;
            case GETHISTORY :
                SignHistoryResponse signHistoryResponse=(SignHistoryResponse)result;
                if (signHistoryResponse != null && signHistoryResponse.getCode() == XtdConst.SUCCESS) {
                    signHistoryAdapter.setListItems(signHistoryResponse.getData());
                    signHistoryAdapter.notifyDataSetChanged();
                    this.recycleView.setNestedScrollingEnabled(false);
                    this.recycleView.loadMoreEnd();
                }
                break;
        }


    }

    public void getScoreRule() {
        Intent intent = new Intent(mActivity, HelpDetailActivity.class);
        intent.putExtra("title","积分规则");
        intent.putExtra("url", XtdConst.SERVERURI+"cli-dgc-helpdetail.php?article_id=8");
        mActivity.startActivity(intent);
    }

}