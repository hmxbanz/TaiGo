package com.xtdar.app.presenter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.NewsCommentAdapter;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.CommentResponse;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.NewsDetailResponse;
import com.xtdar.app.view.activity.NewsDetailActivity;
import com.xtdar.app.view.activity.ReplyDetailActivity;
import com.xtdar.app.view.activity.ReportActivity;
import com.xtdar.app.view.widget.BottomMenuDialog;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.xtdar.app.widget.myRecyclerView.LoadMoreListener;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;
import com.xtdar.app.widget.txtHtml.UrlImageGetter;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class NewsDetailPresenter extends BasePresenter implements View.OnClickListener,LoadMoreListener,NewsCommentAdapter.ItemClickListener {
    private static final int GETDETAIL = 1;
    private static final int ADDFAVOR = 2;
    private static final int GETCOMMENT = 3;
    private static final int ADDCOMMENT = 4;
    private static final int THUMBUP = 5;
    private static final int COMMENTTHUMBUP = 6;
    private static final int COMMENTTHUMBUPOFF = 7;
    private static final int THUMBUPOFF = 8;
    private static final int ADDREPLY = 9;
    private final GlideImageLoader glideImageLoader;
    private NewsDetailActivity mActivity;
    private String itemId,commentId;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private RecyclerViewUpRefresh recyclerView;
    private String classId;
    private GridLayoutManager gridLayoutManager;
    private EditText comment;
    private BasePresenter basePresenter;
    private int pageIndex=0;
    private List<CommentResponse.DataBean> entities=new ArrayList<>();
    private String shareId,shareTitle,shareContent,sharePic;
    private TextView txtContent;
    private final NewsCommentAdapter dataAdapter;
    private BottomMenuDialog dialog;
    private View layout_comment;
    private TextView btnSend;
    private TextView txtComment, txtThumbUp, txtShare;
    private TextView commentTextView;
    private String commentValue;
    private boolean flag;
    private CommentResponse.DataBean bean;
    private String atId="0",replyId;
    private LinearLayout layout_reply;
    private UrlImageGetter reviewImgGetter;

    public NewsDetailPresenter(Context context){
        super(context);
        this.mActivity= (NewsDetailActivity) context;
        glideImageLoader = new GlideImageLoader();
        basePresenter = BasePresenter.getInstance(context);
        dataAdapter = new NewsCommentAdapter(context);
        dataAdapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.recyclerview_top2,null));
        dataAdapter.setOnItemClickListener(this);
        gridLayoutManager=new GridLayoutManager(context,1);
    }

    public void init() {
        this.recyclerView =(RecyclerViewUpRefresh)mActivity.findViewById(R.id.recyclerView);
        this.txtContent = (TextView) mActivity.findViewById(R.id.txt_content);
        this.recyclerView.setAdapter(dataAdapter);
        this.recyclerView.setCanloadMore(true);
        this.recyclerView.setLoadMoreListener(this);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.comment = (EditText) mActivity.findViewById(R.id.comment);
        this.btnSend = (TextView) mActivity.findViewById(R.id.btn_send);
        this.btnSend.setOnClickListener(this);

        this.txtComment = (TextView) mActivity.findViewById(R.id.txt_comment);
        Drawable[] compoundComment = this.txtComment.getCompoundDrawables();
        compoundComment[1].setBounds(0,0,40,40);
        this.txtComment.setCompoundDrawables(null,compoundComment[1],null,null);
        this.txtThumbUp = (TextView) mActivity.findViewById(R.id.txt_thumb_up);
        this.txtThumbUp.setOnClickListener(this);
        Drawable[] compoundThumbUp = this.txtThumbUp.getCompoundDrawables();
        compoundThumbUp[1].setBounds(0,0,50,50);
        this.txtThumbUp.setCompoundDrawables(null,compoundThumbUp[1],null,null);
        this.txtShare = (TextView) mActivity.findViewById(R.id.txt_share);
        Drawable[] compoundShare = this.txtShare.getCompoundDrawables();
        compoundShare[1].setBounds(0,0,40,40);
        this.txtShare.setCompoundDrawables(null,compoundShare[1],null,null);
        this.txtShare.setOnClickListener(this);

        mActivity.findViewById(R.id.txt_comment2).setOnClickListener(this);
        mActivity.findViewById(R.id.layout_back).setOnClickListener(this);
        this.layout_comment=mActivity.findViewById(R.id.layout_comment);

        reviewImgGetter = new UrlImageGetter(txtContent,XtdConst.SERVERURI);//实例化URLImageGetter

        Intent intent = mActivity.getIntent();
        itemId = intent.getStringExtra("recommend_id");

        LoadDialog.show(context);
        atm.request(GETDETAIL, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                mActivity.finish();
                break;
            case R.id.txt_share:
                share();
                break;
            case R.id.btn_send:
                if("0".equals(atId))
                    addComment();
                else
                    addReply();
                break;
            case R.id.txt_comment2:
                this.layout_comment.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_thumb_up:
                thumbUp();
                break;

        }
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {

        switch (requestCode) {
            case GETDETAIL:
                return mUserAction.getNewsDetail(itemId);
            case GETCOMMENT:
                return mUserAction.getNewsComment(itemId,"t_recommend",pageIndex+"","0");
            case THUMBUP:
                return mUserAction.thumbUp(itemId);
            case THUMBUPOFF:
                return mUserAction.thumbUpOff(itemId);
            case COMMENTTHUMBUP:
                return mUserAction.commentThumbUp(commentId);
            case COMMENTTHUMBUPOFF:
                return mUserAction.commentThumbUpOff(commentId);
            case ADDCOMMENT:
                return mUserAction.addComment(itemId,"t_recommend",comment.getText().toString());
            case ADDREPLY:
                return mUserAction.addReply(comment.getText().toString(),replyId,atId);
        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if(result==null)return;
        switch (requestCode) {
            case GETDETAIL:
                NewsDetailResponse de = (NewsDetailResponse) result;
                if (de.getCode() == XtdConst.SUCCESS) {
                    NewsDetailResponse.DataBean entity = de.getData();
                    //glideImageLoader.displayImage(context, XtdConst.IMGURI+entity.getHead_img(),imageView);
                    //this.title.setText(entity.getTitle());
                    //SelectableRoundedImageView avatar=(SelectableRoundedImageView)mActivity.findViewById(R.id.avatar);
                    //glideImageLoader.displayImage(context, XtdConst.IMGURI+entity.getImg_path(),avatar);
                    ((TextView)mActivity.findViewById(R.id.txt_head)).setText("评论："+entity.getTitle());
                    ((TextView)mActivity.findViewById(R.id.txt_create_date)).setText(entity.getPost_time()+" 作者："+entity.getWriter());
                    this.txtComment.setText(entity.getComment_count());
                    this.txtThumbUp.setText(entity.getLike_count());

                    InitThumbUp(entity);

                    this.txtShare.setText(entity.getShare_count());

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        txtContent.setText(Html.fromHtml(entity.getContent(),Html.FROM_HTML_MODE_COMPACT,reviewImgGetter,null));
                    } else {
                        txtContent.setText(Html.fromHtml(entity.getContent(),reviewImgGetter,null));
                    }

                    //请求评论
                    atm.request(GETCOMMENT,this);

                    shareTitle=entity.getTitle();
                    shareContent=entity.getContent().substring(0,30);
                    sharePic=entity.getHead_img();
                    shareId=entity.getRecommend_id();
                }
                NToast.shortToast(context,de.getMsg());
                break;
            case GETCOMMENT:
                CommentResponse commentResponse = (CommentResponse) result;
                if (commentResponse.getCode() == XtdConst.SUCCESS && commentResponse.getData().size()>0) {
                    entities.addAll(commentResponse.getData());
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
                    pageIndex=0;
                    entities.clear();
                    atm.request(GETCOMMENT,this);
                }
                NToast.shortToast(context,CommonResponse.getMsg());
                break;
            case THUMBUP:
                CommonResponse CommonResponse2 = (CommonResponse) result;
                if (CommonResponse2.getCode() == XtdConst.SUCCESS) {
                    if(CommonResponse2.getMsg().equals("你已点赞过了"))
                    {
                        ChangeThumbUp(false);
                        thumbUpOff();
                    }
                    else
                    {
                        ChangeThumbUp(true);
                    }

                }
                break;
            case COMMENTTHUMBUP:
                CommonResponse CommonResponse3 = (CommonResponse) result;

                if (CommonResponse3.getCode() == XtdConst.SUCCESS) {
                    if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Drawable compoundThumbUpOn = mActivity.getDrawable(R.drawable.icon_thumbsup);
                        if(CommonResponse3.getMsg().equals("你已点赞过了"))
                        {
                            bean.setLike_count((Integer.valueOf(bean.getLike_count())-1)+"");
                            commentTextView.setText(bean.getLike_count());
                            compoundThumbUpOn.setTint(context.getResources().getColor(R.color.gray));
                            LoadDialog.show(context);
                            atm.request(COMMENTTHUMBUPOFF,this);
                        }
                        else
                        {
                            bean.setLike_count((Integer.valueOf(bean.getLike_count())+1)+"");
                            commentTextView.setText(bean.getLike_count());
                            compoundThumbUpOn.setTint(context.getResources().getColor(R.color.salmon));
                            NToast.shortToast(context,CommonResponse3.getMsg());
                        }
                        compoundThumbUpOn.setBounds(0, 0, 30, 30);
                        this.commentTextView.setCompoundDrawables(compoundThumbUpOn,null , null, null);
                    }

                }

                break;
            case ADDREPLY:
                CommonResponse CommonResponse4 = (CommonResponse) result;
                if (CommonResponse4.getCode() == XtdConst.SUCCESS) {
                    //实例化一个LinearLayout
                    LinearLayout tempLinearLayout=new LinearLayout(context);
                    //设置LinearLayout属性(宽和高)
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置边距
                    layoutParams.setLayoutDirection(LayoutDirection.LTR);
                    //设置边距
                    layoutParams.setMargins(0, 0, 0, 0);
                    //将以上的属性赋给LinearLayout
                    tempLinearLayout.setLayoutParams(layoutParams);

                    //实例化一个TextView(回复人)
                    TextView tvNickName = new TextView(context);
                    LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    tvParams.gravity = Gravity.LEFT;
                    tvNickName.setLayoutParams(tvParams);
                    tvNickName.setTextSize(10);
                    tvNickName.setTextColor(context.getResources().getColor(R.color.deepskyblue));
                    tvNickName.setText(basePresenter.nickName);

                    //实例化一个TextView(回复内容)
                    TextView tvComment = new TextView(context);
                    //设置宽高以及权重
                    LinearLayout.LayoutParams tvCommentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置textview垂直居中
                    tvParams.gravity = Gravity.LEFT;
                    tvComment.setLayoutParams(tvParams);
                    tvComment.setTextSize(10);
                    tvComment.setText(":"+this.comment.getText());

                    tempLinearLayout.addView(tvNickName);
                    tempLinearLayout.addView(tvComment);
                    this.layout_reply.setVisibility(View.VISIBLE);
                    this.layout_reply.addView(tempLinearLayout);
                }
                NToast.shortToast(context,CommonResponse4.getMsg());
                this.atId="0";
                break;
        }
    }

    private void InitThumbUp(NewsDetailResponse.DataBean entity) {
        if(entity.getIs_like() != null && (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable compoundThumbUpOn = mActivity.getDrawable(R.drawable.icon_thumb_up_on);
            compoundThumbUpOn.setBounds(0, 0, 40, 40);
            this.txtThumbUp.setCompoundDrawables(null, compoundThumbUpOn, null, null);
        }
        else
        {
            Drawable compoundThumbUpOn = mActivity.getDrawable(R.drawable.icon_thumb_up);
            compoundThumbUpOn.setBounds(0, 0, 40, 40);
            this.txtThumbUp.setCompoundDrawables(null, compoundThumbUpOn, null, null);
        }
    }

    private void ChangeThumbUp(Boolean flag) {
        if(flag) {
            Drawable compoundThumbUpOn = mActivity.getDrawable(R.drawable.icon_thumb_up_on);
            compoundThumbUpOn.setBounds(0, 0, 40, 40);
            this.txtThumbUp.setCompoundDrawables(null, compoundThumbUpOn, null, null);
            this.txtThumbUp.setText((Integer.valueOf(txtThumbUp.getText().toString())+1)+"");
        }
        else
        {
            Drawable compoundThumbUpOn = mActivity.getDrawable(R.drawable.icon_thumb_up);
            compoundThumbUpOn.setBounds(0, 0, 40, 40);
            this.txtThumbUp.setCompoundDrawables(null, compoundThumbUpOn, null, null);
            this.txtThumbUp.setText((Integer.valueOf(txtThumbUp.getText().toString())-1)+"");
        }
    }

    //点赞
    public void thumbUp() {
//        if ("".equals(this.comment.getText().toString().trim()))
//        {
//            NToast.shortToast(context,"请输入评论内容");
//            return;
//        }
        if(!basePresenter.isLogin)
        {
            NToast.shortToast(context,"请先登录。");
            return;
        }
        LoadDialog.show(context);
        atm.request(THUMBUP,this);
    }

    //取消点赞
    public void thumbUpOff() {
        atm.request(THUMBUPOFF,this);
    }

    //评论点赞
    public void CommentThumbUp(String itemId) {
        if(!basePresenter.isLogin)
        {
            NToast.shortToast(context,"请先登录。");
            return;
        }
        this.commentId=itemId;
        LoadDialog.show(context);
        atm.request(COMMENTTHUMBUP,this);
    }

    public void addComment() {
        if(!basePresenter.isLogin)
        {
            NToast.shortToast(context,"请先登录。");
            return;
        }
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
                pageIndex ++;
                atm.request(GETCOMMENT,NewsDetailPresenter.this);
                recyclerView.loadMoreComplete();
            }
        }, 2000);

    }

    /**
     * 弹出底部框
     */
    @TargetApi(23)
    public void showPhotoDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        else
        {

            dialog = new BottomMenuDialog(context);

            dialog.setConfirmListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                        layout_comment.setVisibility(View.VISIBLE);
                    NewsDetailPresenter.this.comment.setHint("回复："+NewsDetailPresenter.this.bean.getNick_name());
                    NewsDetailPresenter.this.comment.requestFocus();        //自动弹出键盘
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    //强制隐藏Android输入法窗口
                    // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);

                    NewsDetailPresenter.this.atId=NewsDetailPresenter.this.bean.getUser_id();
                    NewsDetailPresenter.this.replyId=NewsDetailPresenter.this.bean.getCom_id();
                        dialog.dismiss();
                }
            });
            dialog.setMiddleListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                        ReportActivity.StartActivity(context,NewsDetailPresenter.this.bean);
                        dialog.dismiss();
                }
            });

            dialog.show();
        }


    }


    public void share() {
        String url=XtdConst.SERVERURI+"mobi-dis-showrecommend.php?recommend_id="+shareId;
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

    @Override
    public void onItemClick(int position, CommentResponse.DataBean bean) {
        ReplyDetailActivity.StartActivity(context,bean);
    }

    @Override
    public void onTxtThumbUpClick(int position, CommentResponse.DataBean bean,TextView t) {
        this.bean=bean;
        this.commentTextView=t;
        CommentThumbUp(bean.getCom_id());
    }

    @Override
    public void onContentClick(int position, CommentResponse.DataBean bean, LinearLayout layout_reply) {
        this.bean=bean;
        this.layout_reply=layout_reply;
        showPhotoDialog();
    }

    //回复评论
    public void addReply() {
        if(!basePresenter.isLogin)
        {
            NToast.shortToast(context,"请先登录。");
            return;
        }
        if ("".equals(this.comment.getText().toString().trim()))
        {
            NToast.shortToast(context,"请输入回复内容");
            return;
        }
        this.comment.setHint("回复：");
        LoadDialog.show(context);
        atm.request(ADDREPLY,this);
    }

    public void onDestroy() {
        if(reviewImgGetter !=null)
            reviewImgGetter=null;
    }

//    /**
//     *  解绑ImageGetterSubscription
//     */
//    public void cancelImageGetterSubscription(){
//        if(reviewImgGetter != null){
//            try {
//                reviewImgGetter.unSubscribe();
//            }catch (Exception e){
//                NLog.e("解绑 UrlImageGetter Subscription 异常");
//            }
//        }
//    }

}
