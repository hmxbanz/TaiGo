package com.xtdar.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.ReportPresenter;
import com.xtdar.app.server.response.CommentResponse;



public class ReportActivity extends BaseActivity implements View.OnClickListener {
    private ReportPresenter reportPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initViews();
        reportPresenter=new ReportPresenter(this);
        reportPresenter.init();
    }
    public void initViews(){

        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("举报");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;

        }
    }

    public static void StartActivity(Context context, CommentResponse.DataBean entity) {
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra("com_id",entity.getCom_id());
        intent.putExtra("avatar",entity.getImg_path());
        intent.putExtra("nickName",entity.getNick_name());
        intent.putExtra("content",entity.getComment());
        context.startActivity(intent);
    }

}



