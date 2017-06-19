package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.DownloadPresenter;

public class DownloadActivity extends BaseActivity implements View.OnClickListener {
    private DownloadPresenter downloadPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initViews();
        downloadPresenter =new DownloadPresenter(this);
        downloadPresenter.init();
    }
    public void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("下载");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
        }
    }


}
