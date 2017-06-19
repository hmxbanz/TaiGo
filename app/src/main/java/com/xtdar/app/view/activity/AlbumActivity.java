package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtdar.app.presenter.AlbumPresenter;
import com.xtdar.app.Interface.IAlbumView;
import com.xtdar.app.R;
import com.xtdar.app.adapter.AlbumAdapter;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class AlbumActivity extends BaseActivity implements IAlbumView,AlbumAdapter.ItemClickListener,View.OnClickListener {
    private AlbumPresenter albumPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initViews();
        albumPresenter=new AlbumPresenter(this);
        albumPresenter.init();
//        initData();
    }

    private void initViews() {
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        TextView text_right=(TextView) findViewById(R.id.text_right);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("我的相册");
        text_right.setText("上传");
        text_right.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }

    @Override
    public void onItemClick(int position, String data) {
        Toast.makeText(this,"你点击了位置："+String.valueOf(position)+"-标题："+data,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;

        }
    }

    @Override
    public void initData() {

    }
}
