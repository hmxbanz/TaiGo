package com.shuyu.gsyvideoplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.File;

/**
 * Created by PVer on 2017/6/14.
 */

public class MyVideoPlayer extends StandardGSYVideoPlayer {
    private        Bitmap             mBackGround;
    private        String             mTitle;
    private static String             mSaveUrl;
    private             int    NOTIFICATION_ID    = 100;
    private             int    REQUEST_CODE_PLAY  = 1;
    private             int    REQUEST_CODE_PAUSE = 2;
    private             int    REQUEST_CODE_STOP  = 3;
    public MyVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomConfig();
    }


//    @Override
//    public int getLayoutId() {
//        //        if (mIfCurrentIsFullscreen) {
//        //            return R.layout.video_layout_custom;
//        //        }
//        return R.layout.custom_video_layout;
//    }

    /**
     * 自定义UI
     */
    private void initCustomConfig() {
        hideTitle();
        SeekBar seekBar = (SeekBar) findViewById(R.id.progress);
        seekBar.setOnSeekBarChangeListener(this);
    }


    /**
     * 隐藏title
     */
    private void hideTitle() {
        //mTopContainer.setBackgroundColor(Color.TRANSPARENT);
        getTitleTextView().setVisibility(View.GONE);
        getBackButton().setVisibility(View.GONE);
    }

    /**
     * 设置封面图为默认背景,让播放音频时背景不会变成黑色
     *
     * @param url
     */
    public void setBackGroundToPlayer(String url) {

        //设置封面
        Glide.with(mContext).load(url).asBitmap().dontAnimate().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mBackGround = resource;
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(resource);
                setThumbImageView(imageView);
                ImageView i = new ImageView(mContext);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                i.setLayoutParams(params);
                i.setBackgroundColor(Color.parseColor("#450b0b0b"));
                mThumbImageViewLayout.addView(i);
            }
        });
    }

//    @Override
//    protected void showPauseCover() {
//
//        try {
//            if (mCurrentState == CURRENT_STATE_PAUSE && mFullPauseBitmap != null
//                    && !mFullPauseBitmap.isRecycled() && !mBackGround.isRecycled()) {
//                mCoverImageView.setRotation(mRotate);
//                mFullPauseBitmap = mBackGround;
//                mCoverImageView.setImageBitmap(mFullPauseBitmap);
//                mCoverImageView.setVisibility(VISIBLE);
//                mCoverImageView.setImageAlpha(180);//Color(Color.parseColor("#450b0b0b"));
//                mCoverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    //改变当前播放状态
    public void changeState(int state) {
        mCurrentState = state;
        setStateAndUi(state);
    }


    //获取当前状态
    public int getCurrentState() {
        return mCurrentState;
    }


    @Override
    public void setStateAndUi(int state) {

        super.setStateAndUi(state);
        switch (mCurrentState) {
            case CURRENT_STATE_NORMAL:
                mBottomContainer.setVisibility(View.VISIBLE);
                break;
            case CURRENT_STATE_PREPAREING://准备播放
                mCoverImageView.setVisibility(View.VISIBLE);
                break;
            case CURRENT_STATE_PLAYING: //播放中
                mSaveUrl = mOriginUrl;
                mCoverImageView.setVisibility(View.VISIBLE);

                break;
            case CURRENT_STATE_PAUSE://暂停

                //mBottomProgressBar.setVisibility(View.GONE);

                break;

        }
        mThumbImageViewLayout.setVisibility(View.VISIBLE);
    }


}
