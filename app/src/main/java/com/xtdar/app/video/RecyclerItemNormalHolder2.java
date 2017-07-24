package com.xtdar.app.video;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoshuyu on 2017/1/9.
 */

public class RecyclerItemNormalHolder2 extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerView2List";

    protected Context context = null;

    @BindView(R.id.video_item_player)
    StandardGSYVideoPlayer gsyVideoPlayer;

    ImageView imageView;
    @BindView(R.id.avatar)
    SelectableRoundedImageView roundedImageView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nickname)
    TextView nickName;
    @BindView(R.id.createdate)
    TextView createDate;
    @BindView(R.id.click_count)
    TextView clickCount;
    @BindView(R.id.comment_count)
    TextView commentCount;

    private GlideImageLoader glideImageLoader;

    public RecyclerItemNormalHolder2(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        imageView = new ImageView(context);
        glideImageLoader = new GlideImageLoader();
    }

    public void onBind(final int position, ClassListResponse.DataBean videoModel) {

        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        glideImageLoader.displayImage(context, XtdConst.IMGURI+videoModel.getItem_cover(),imageView);
        //glideImageLoader.displayImage(context, XtdConst.IMGURI+videoModel.get.getImg_path(),roundedImageView);

        //title.setText(videoModel.getTitle());
        nickName.setText("TaiGo");
        createDate.setText(videoModel.getPost_date());
        clickCount.setText("观看:"+videoModel.getClick_count());
        //commentCount.setText("评论:"+videoModel.getCom_count());
//        if (position % 2 == 0) {
//            imageView.setImageResource(R.mipmap.xxx1);
//        } else {
//            imageView.setImageResource(R.mipmap.xxx2);
//        }
        if (imageView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup)imageView.getParent();
            viewGroup.removeView(imageView);
        }
        gsyVideoPlayer.setThumbImageView(imageView);

        final String url = XtdConst.IMGURI+videoModel.getResource();

        //默认缓存路径
        gsyVideoPlayer.setUp(url, true , null, videoModel.getItem_title());

        //增加title
        //gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
        gsyVideoPlayer.setRotateViewAuto(true);
        gsyVideoPlayer.setLockLand(true);
        gsyVideoPlayer.setPlayTag(TAG);
        gsyVideoPlayer.setShowFullAnimation(true);
        //循环
        //gsyVideoPlayer.setLooping(true);
        gsyVideoPlayer.setNeedLockFull(true);

        //gsyVideoPlayer.setSpeed(2);

        gsyVideoPlayer.setPlayPosition(position);

        gsyVideoPlayer.setStandardVideoAllCallBack(new SampleListener(){
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                Debuger.printfLog("onPrepared");
                if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                    //静音
                    GSYVideoManager.instance().setNeedMute(false);
                }

            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                //全屏不静音
                GSYVideoManager.instance().setNeedMute(true);
            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {
                super.onEnterFullscreen(url, objects);
                GSYVideoManager.instance().setNeedMute(false);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

}