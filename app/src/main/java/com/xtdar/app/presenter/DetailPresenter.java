package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.xtdar.app.server.response.DetailResponse;
import com.xtdar.app.server.response.RelateRecommendResponse;
import com.xtdar.app.video.SampleListener;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.xtdar.app.widget.myRecyclerView.LoadMoreListener;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class DetailPresenter extends BasePresenter implements LoadMoreListener {
    private static final int GETDETAIL = 1;
    private static final int ADDFAVOR = 2;
    private static final int GETRELATERECOMMEND = 3;
    private static final int GETCOMMENT = 4;
    private static final int ADDCOMMENT = 5;
    private final GlideImageLoader glideImageLoader;
    private final CommentAdapter dataAdapter;
    private DetailActivity mActivity;
    private String itemId;
    private StandardGSYVideoPlayer videoPlayer;
    private TextView title,content;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private RecyclerView recyclerView;
    private RecyclerViewUpRefresh recycleViewComment;
    private String classId;
    private GridLayoutManager gridLayoutManager;
    private EditText comment;
    private String lastItemId;

    private String shareId,shareTitle,shareContent,sharePic;

    public DetailPresenter(Context context){
        super(context);
        this.mActivity= (DetailActivity) context;
        glideImageLoader = new GlideImageLoader();

        gridLayoutManager=new GridLayoutManager(context,1);
        dataAdapter = new CommentAdapter(context);
        dataAdapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.recyclerview_top2,null));
    }

    public void init(StandardGSYVideoPlayer videoPlayer, TextView title, TextView content, RecyclerView recycleView, RecyclerViewUpRefresh recycleViewComment) {
        this.videoPlayer=videoPlayer;
        this.title = title;
        this.content=content;
        this.recyclerView =recycleView;
        this.recycleViewComment=recycleViewComment;
        Intent intent = mActivity.getIntent();
        itemId = intent.getStringExtra(XtdConst.ITEMID);
        classId = intent.getStringExtra(XtdConst.CLASSID);

        this.recycleViewComment.setAdapter(dataAdapter);
        this.recycleViewComment.setCanloadMore(true);
        this.recycleViewComment.setLoadMoreListener(this);
        this.recycleViewComment.setLayoutManager(gridLayoutManager);

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
                DetailPresenter.this.videoPlayer.startWindowFullscreen(context, true, true);
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
                return mUserAction.getDetail(itemId);
            case GETRELATERECOMMEND:
                return mUserAction.getRelateRecommend(classId);
            case ADDFAVOR:
                return mUserAction.addFavor(itemId);
            case GETCOMMENT:
                return mUserAction.getComment(itemId,"t_class_item","0","10");
            case ADDCOMMENT:
                return mUserAction.addComment(itemId,"t_class_item",comment.getText().toString());
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETDETAIL:
                DetailResponse de = (DetailResponse) result;
                if (de.getCode() == XtdConst.SUCCESS) {
                    DetailResponse.DataBean entity = de.getData();
                    this.videoPlayer.setUp(XtdConst.IMGURI+entity.getResource(), false, null, "");

                    //增加封面
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    glideImageLoader.displayImage(context, XtdConst.IMGURI+entity.getItem_cover(),imageView);
                    videoPlayer.setThumbImageView(imageView);
                    this.title.setText(entity.getItem_title());
                    this.content.setText(entity.getContent());

                    //请求推荐
                    atm.request(GETRELATERECOMMEND,this);

                    shareTitle=entity.getItem_title();
                    shareContent=entity.getContent();
                    sharePic=entity.getItem_cover();
                    shareId=entity.getItem_id();

                }
                NToast.shortToast(context,de.getMsg());
                break;

            case GETRELATERECOMMEND:
                RelateRecommendResponse relateRecommendResponse = (RelateRecommendResponse) result;
                if (relateRecommendResponse.getCode() == XtdConst.SUCCESS) {
                    List<RelateRecommendResponse.DataBean> entities = relateRecommendResponse.getData();

                    gridLayoutManager=new GridLayoutManager(context,2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    RelateRecommendItemAdapter dataAdapter = new RelateRecommendItemAdapter(entities, context);
                    //dataAdapter.setFooterView(LayoutInflater.from(this).inflate(R.layout.recyclerview_footer,null));
                    recyclerView.setAdapter(dataAdapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    if(Build.VERSION.SDK_INT>=23)
                        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                            @Override
                            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                                if (gridLayoutManager.findLastCompletelyVisibleItemPosition()==(UserList.getData().size()-1))
                                {}
                            }
                        });
                    //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
                    dataAdapter.setOnItemClickListener(new RelateRecommendItemAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int position, String itemId, String classId) {
                            DetailActivity.StartActivity(context,itemId,classId);
                        }


                    });

                }
                atm.request(GETCOMMENT,this);
                NToast.shortToast(context,relateRecommendResponse.getMsg());
                break;
            case ADDFAVOR:
                CommonResponse commonResponse=(CommonResponse) result;
                if (commonResponse.getCode() == XtdConst.SUCCESS) {
                    DialogWithYesOrNoUtils.getInstance().showDialog(context,"收藏成功",null,null ,new AlertDialogCallback());
                }
                NToast.shortToast(context,commonResponse.getMsg());
                break;
            case GETCOMMENT:
                CommentResponse commentResponse = (CommentResponse) result;
                if (commentResponse.getCode() == XtdConst.SUCCESS) {
                    List<CommentResponse.DataBean> entities = commentResponse.getData();
                    lastItemId = entities.get(entities.size() - 1).getCom_id();

                    dataAdapter.setListItems(entities);
                    dataAdapter.notifyDataSetChanged();

                }
                NToast.shortToast(context,commentResponse.getMsg());
                break;
            case ADDCOMMENT:
                CommonResponse CommonResponse = (CommonResponse) result;
                if (CommonResponse.getCode() == XtdConst.SUCCESS) {
                    DialogWithYesOrNoUtils.getInstance().showDialog(context,"评论成功",null,null,new AlertDialogCallback());
                    //请求评论
                    atm.request(GETCOMMENT,this);
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

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(mSwipRefreshLayout.isRefreshing()){
//                    mSwipRefreshLayout.setRefreshing(false);
//                }
//                adapter.addItems(initData());
                atm.request(GETCOMMENT,DetailPresenter.this);
                recycleViewComment.loadMoreComplete();
            }
        }, 2000);
    }

    public void share() {
        String url=XtdConst.SERVERURI+"mobi-dis-shareitem.php?item_id="+shareId;
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(shareTitle);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        //oks.setTitleUrl("http://data.xtaigo.com/dl.html");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareContent);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(XtdConst.SERVERURI+sharePic);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(shareTitle);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

        // 启动分享GUI
        oks.show(context);

    }
}
