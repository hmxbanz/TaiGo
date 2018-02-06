package com.xtdar.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.xtdar.app.R;
import com.xtdar.app.presenter.ReplyDetailPresenter;
import com.xtdar.app.server.response.CommentResponse;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;

import java.util.ArrayList;
import java.util.List;


public class ReplyDetailActivity extends BaseActivity implements View.OnClickListener {
    public static List<?> images=new ArrayList<>();
    private RecyclerViewUpRefresh recycleView;

    private ReplyDetailPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reply_detail);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.titleBlue),0);
        initViews();
        presenter = new ReplyDetailPresenter(this);
        presenter.init();
    }

    private void initViews() {
        //mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
//
//        recycleView= (RecyclerViewUpRefresh) findViewById(R.id.recyclerView);
//
//        AppBarLayout app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

    }

    public static void StartActivity(Context context,CommentResponse.DataBean bean) {
        Intent intent = new Intent(context, ReplyDetailActivity.class);
        intent.putExtra("commentId",bean.getCom_id());
        intent.putExtra("nickName", bean.getNick_name());
        intent.putExtra("avatar", bean.getImg_path());
        intent.putExtra("content", bean.getComment());
        intent.putExtra("createDate", bean.getCom_date());
        intent.putExtra("replyCount", bean.getReply_count());
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
