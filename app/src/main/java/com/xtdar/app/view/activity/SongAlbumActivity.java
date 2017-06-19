package com.xtdar.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.presenter.LoginPresenter;
import com.xtdar.app.presenter.SongAlbumPresenter;


public class SongAlbumActivity extends BaseActivity implements View.OnClickListener {

    private SongAlbumPresenter songAlbumPresenter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_album);

        initViews();
        songAlbumPresenter = new SongAlbumPresenter(this);
        songAlbumPresenter.init(listview);
    }

    private void initViews() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("专辑详情");
        listview=(ListView)findViewById(R.id.listview);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;

        }
    }

    public static void StartActivity(Context context, String itemId) {
        Intent intent = new Intent(context, SongAlbumActivity.class);
        intent.putExtra(XtdConst.ITEMID,itemId);
        context.startActivity(intent);
    }
}
