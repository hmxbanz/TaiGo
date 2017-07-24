package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.CommentAdapter;
import com.xtdar.app.adapter.RelateRecommendItemAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.model.UserList;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.CommentResponse;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.RelateRecommendResponse;
import com.xtdar.app.server.response.ShowDetailResponse;
import com.xtdar.app.video.SampleListener;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.activity.ShowDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.view.widget.SelectableRoundedImageView;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ShowDetailPresenter extends BasePresenter{
    private static final int GETDETAIL = 1;
    private static final int ADDFAVOR = 2;
    private static final int GETCOMMENT = 3;
    private static final int ADDCOMMENT = 4;
    private final GlideImageLoader glideImageLoader;
    private ShowDetailActivity mActivity;
    private String itemId;
    private StandardGSYVideoPlayer videoPlayer;
    private TextView title;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private RecyclerView recycleView;
    private String classId;
    private GridLayoutManager gridLayoutManager;
    private EditText comment;

    public ShowDetailPresenter(Context context){
        super(context);
        this.mActivity= (ShowDetailActivity) context;
        glideImageLoader = new GlideImageLoader();
    }

    public void init(StandardGSYVideoPlayer videoPlayer, TextView title, RecyclerView recycleView) {
        this.videoPlayer=videoPlayer;
        this.title = title;
        this.recycleView=recycleView;
        Intent intent = mActivity.getIntent();
        itemId = intent.getStringExtra("show_id");
        //classId = intent.getStringExtra(XtdConst.CLASSID);

        //resolveNormalVideoUI();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(mActivity, videoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        this.videoPlayer.setIsTouchWiget(true);
        //detailPlayer.setIsTouchWigetFull(false);
        //关闭自动旋转
        this.videoPlayer.setRotateViewAuto(false);
        this.videoPlayer.setLockLand(false);
        this.videoPlayer.setShowFullAnimation(false);
        this.videoPlayer.setNeedLockFull(true);
        //detailPlayer.setOpenPreView(false);
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                ShowDetailPresenter.this.videoPlayer.startWindowFullscreen(context, true, true);
            }
        });

        videoPlayer.setStandardVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }

            @Override
            public void onClickStartError(String url, Object... objects) {
                super.onClickStartError(url, objects);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        videoPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });

        LoadDialog.show(context);
        atm.request(GETDETAIL, this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {

        switch (requestCode) {
            case GETDETAIL:
                return mUserAction.getShowDetail(itemId);
            case GETCOMMENT:
                return mUserAction.getComment(itemId,"0","10");
            case ADDFAVOR:
                return mUserAction.addFavor(itemId);
            case ADDCOMMENT:
                return mUserAction.addComment(itemId,"t_show",comment.getText().toString());
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETDETAIL:
                ShowDetailResponse de = (ShowDetailResponse) result;
                if (de.getCode() == XtdConst.SUCCESS) {
                    ShowDetailResponse.DataBean entity = de.getData();
                    this.videoPlayer.setUp(XtdConst.IMGURI+entity.getShow_resource(), false, null, "");

                    //增加封面
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    glideImageLoader.displayImage(context, XtdConst.IMGURI+entity.getHead_img(),imageView);
                    videoPlayer.setThumbImageView(imageView);
                    this.title.setText(entity.getTitle());
                    SelectableRoundedImageView avatar=(SelectableRoundedImageView)mActivity.findViewById(R.id.avatar);
                    glideImageLoader.displayImage(context, XtdConst.IMGURI+entity.getImg_path(),avatar);
                    ((TextView)mActivity.findViewById(R.id.nickname)).setText(entity.getNick_name());
                    ((TextView)mActivity.findViewById(R.id.txt_title)).setText(entity.getTitle());
                    ((TextView)mActivity.findViewById(R.id.new_comment)).setText("最新评论：("+entity.getCom_count()+")");
                    ((TextView)mActivity.findViewById(R.id.click_count)).setText("观看："+entity.getClick_count());
                    ((TextView)mActivity.findViewById(R.id.comment_count)).setText("评论："+entity.getCom_count());
                    //请求评论
                    atm.request(GETCOMMENT,this);

                }
                NToast.shortToast(context,de.getMsg());
                break;

            case GETCOMMENT:
                CommentResponse commentResponse = (CommentResponse) result;
                if (commentResponse.getCode() == XtdConst.SUCCESS) {
                    List<CommentResponse.DataBean> entities = commentResponse.getData();

                    gridLayoutManager=new GridLayoutManager(context,1);
                    recycleView.setLayoutManager(gridLayoutManager);
                    CommentAdapter dataAdapter = new CommentAdapter(entities, context);
                    //dataAdapter.setFooterView(LayoutInflater.from(this).inflate(R.layout.recyclerview_footer,null));
                    recycleView.setAdapter(dataAdapter);
                    recycleView.setNestedScrollingEnabled(false);
                    if(Build.VERSION.SDK_INT>=23)
                        recycleView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                            @Override
                            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                                if (gridLayoutManager.findLastCompletelyVisibleItemPosition()==(UserList.getData().size()-1))
                                {}
                            }
                        });
                    //recycleView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
                    dataAdapter.setOnItemClickListener(new CommentAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int position, String itemId, String classId) {
                            //DetailActivity.StartActivity(context,itemId,classId);
                        }


                    });

                }
                NToast.shortToast(context,commentResponse.getMsg());
                break;
            case ADDCOMMENT:
                CommonResponse CommonResponse = (CommonResponse) result;
                if (CommonResponse.getCode() == XtdConst.SUCCESS) {
                    DialogWithYesOrNoUtils.getInstance().showDialog(context,"评论成功",null,null,new AlertDialogCallback());
                }
                NToast.shortToast(context,CommonResponse.getMsg());
                break;

        }
    }

    public void addFavor() {
        LoadDialog.show(context);
        atm.request(ADDFAVOR,this);
    }

    public void addComment(EditText comment) {
        this.comment = comment;
        if ("".equals(this.comment.getText().toString().trim()))
        {
            NToast.shortToast(context,"请输入评论内容");
            return;
        }
        LoadDialog.show(context);
        atm.request(ADDCOMMENT,this);
    }
}
