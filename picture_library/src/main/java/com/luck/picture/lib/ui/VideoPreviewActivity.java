package com.luck.picture.lib.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.TextView;
import android.widget.ImageButton;

import com.luck.picture.lib.R;

import static android.provider.MediaStore.Video.Thumbnails.FULL_SCREEN_KIND;



/**
 * Created by bbw31 on 2017-03-14.
 */

public class VideoPreviewActivity extends Activity implements View.OnClickListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    public static final String TAG = "VideoPreviewActivity";
    public static final int RESULT_SELECT = 03;//activity返回码
    public static final String START_TYPE = "start_type";//启动预览方式（播放网络视频或者预览要上传的视频）tag
    public static final String VIDEO_PATH = "video_path";//视频地址tag
    public static final int VIDEO_PREVIEW = 0;//播放网络视频
    public static final int VIDEO_SELECT_PREVIEW = 1;//预览要上次的视频

    private String video_path = "";
    private int startType ;
    private VideoView mVideoView;
    private RelativeLayout videoLayout;
    private RelativeLayout videoTitle;
    private ProgressBar videoProgressbar;
    private ImageView playVideo;
    private LinearLayout videoSelect;
    private ImageButton videoBack;
    private Bitmap thumb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_videopreview);

        startType = getIntent().getIntExtra(START_TYPE,0);
        video_path = getIntent().getStringExtra(VIDEO_PATH);
        initView();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (startType){
            case VIDEO_PREVIEW:
                startVideo();
                break;
            case VIDEO_SELECT_PREVIEW:
                playVideo.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initView(){
        videoLayout = (RelativeLayout) findViewById(R.id.video_layout);
        videoTitle = (RelativeLayout) findViewById(R.id.video_preview_title);
        videoBack = (ImageButton) findViewById(R.id.video_back);
        videoSelect = (LinearLayout) findViewById(R.id.video_select);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        videoProgressbar = (ProgressBar) findViewById(R.id.video_progressbar);
        playVideo = (ImageView) findViewById(R.id.video_play);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnErrorListener(this);
        videoLayout.setOnClickListener(this);
        switch (startType){
            case VIDEO_PREVIEW:
                videoProgressbar.setVisibility(View.VISIBLE);
                break;
            case VIDEO_SELECT_PREVIEW:
                thumb = ThumbnailUtils.createVideoThumbnail(video_path,FULL_SCREEN_KIND);
               /* MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                int kind = MediaStore.Video.Thumbnails.MINI_KIND;
                if (Build.VERSION.SDK_INT >= 14) {
                    retriever.setDataSource(video_path, new HashMap<String, String>());
                } else {
                    retriever.setDataSource(video_path);
                }
                thumb = retriever.getFrameAtTime();*/
                mVideoView.setBackgroundDrawable(new BitmapDrawable(thumb));
                videoTitle.setVisibility(View.VISIBLE);
                playVideo.setVisibility(View.VISIBLE);
                videoBack.setVisibility(View.VISIBLE);
                videoSelect.setVisibility(View.VISIBLE);
                videoBack.setVisibility(View.VISIBLE);
                videoSelect.setOnClickListener(this);
                videoBack.setOnClickListener(this);
                playVideo.setOnClickListener(this);
                break;
        }
    };
    private void startVideo(){
        mVideoView.setVideoPath(video_path);
        mVideoView.requestFocus();
        mVideoView.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG,"-------onPrepared");
        switch (startType){
            case VIDEO_PREVIEW:
                videoProgressbar.setVisibility(View.GONE);
                break;
            case VIDEO_SELECT_PREVIEW:
                mVideoView.setBackgroundDrawable(null);
                break;
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG,"-------onCompletion");
        switch (startType){
            case VIDEO_PREVIEW:
                mVideoView.start();
                break;
            case VIDEO_SELECT_PREVIEW:
                playVideo.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d(TAG,"-------onError"+what+"   "+extra);
        Toast.makeText(this,"播放视频出错",Toast.LENGTH_LONG);
        switch (startType){
            case VIDEO_PREVIEW:
                this.finish();
                break;
            case VIDEO_SELECT_PREVIEW:
                playVideo.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.video_layout){
            switch (startType){
                case VIDEO_PREVIEW:
                    finish();
                    break;
                case VIDEO_SELECT_PREVIEW:
                    if (videoTitle.getVisibility()== View.VISIBLE){
                        videoTitle.setVisibility(View.GONE);
                    }else if (videoTitle.getVisibility()== View.GONE){
                        videoTitle.setVisibility(View.VISIBLE);
                    }
                    break;
            }

        }else if (v.getId() == R.id.video_play){
            playVideo.setVisibility(View.GONE);
            startVideo();
        }else if (v.getId() == R.id.video_back){
            finish();
        }else if (v.getId() == R.id.video_select){
            Intent intent = getIntent();
            intent.putExtra(VIDEO_PATH,video_path);
            setResult(RESULT_SELECT,intent);
            finish();
        }
    }
    private int getStatusBaHeight(){
        int result=0;
        int resourceId=getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId>0){
            result=getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
