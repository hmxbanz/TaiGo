package com.xtdar.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.MyVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.presenter.SongDetailPresenter;


public class SongDetailActivity extends BaseActivity implements View.OnClickListener {
    private EditText mUsername,mPassword;
    private Button mBtnLogin;
    private RelativeLayout mLayoutRegister;
    private TextView mTextForgetPassword;
    private SongDetailPresenter songDetailPresenter;
    private TextView mTextRight;
    private MyVideoPlayer videoPlayer;
    private ImageView imgForward;
    private ImageView imgPlay;
    private ImageView imgNext;
    private boolean isPlay=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        initViews();
        songDetailPresenter = new SongDetailPresenter(this);
        songDetailPresenter.init(videoPlayer,txtTitle,imgForward,imgPlay,imgNext);

    }

    private void initViews() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("歌曲详情");
        videoPlayer=(MyVideoPlayer)findViewById(R.id.video_player);

        imgForward = (ImageView) findViewById(R.id.img_forward);
        imgPlay = (ImageView) findViewById(R.id.img_play);
        imgNext = (ImageView) findViewById(R.id.img_next);
        imgForward.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.img_forward:
                songDetailPresenter.forward();
                break;
            case R.id.img_play:
                songDetailPresenter.play();
                break;
            case R.id.img_next:
                songDetailPresenter.next();
                break;

        }
    }

    public static void StartActivity(Context context, String songId,String albumId) {
        Intent intent = new Intent(context, SongDetailActivity.class);
        intent.putExtra(XtdConst.SONGID,songId);
        intent.putExtra(XtdConst.ALBUMID,albumId);
        context.startActivity(intent);
    }
}
