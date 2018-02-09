package com.xtdar.app.presenter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.NewsReplyAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.response.CommentReplyResponse;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.NewsDetailResponse;
import com.xtdar.app.view.activity.ReplyDetailActivity;
import com.xtdar.app.view.widget.BottomMenuDialog;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.view.widget.SelectableRoundedImageView;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.xtdar.app.widget.myRecyclerView.LoadMoreListener;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ReplyDetailPresenter extends BasePresenter implements View.OnClickListener,LoadMoreListener,NewsReplyAdapter.ItemClickListener {
    private static final int GETREPLY = 3;
    private static final int ADDREPLY = 4;
    private static final int THUMBUP = 5;
    private static final int COMMENTTHUMBUP = 6;
    private static final int COMMENTTHUMBUPOFF =7;
    private final GlideImageLoader glideImageLoader;
    private ReplyDetailActivity mActivity;
    private RecyclerViewUpRefresh recyclerView;
    private GridLayoutManager gridLayoutManager;
    private EditText comment;
    private BasePresenter basePresenter;
    private String lastItemId="0";
    private List<CommentReplyResponse.DataBean> entities=new ArrayList<CommentReplyResponse.DataBean>();
    private String shareId,shareTitle,shareContent,sharePic;
    private final NewsReplyAdapter dataAdapter;
    private BottomMenuDialog dialog;
    private View layout_comment;
    private TextView btnSend;
    private TextView commentTextView;
    private String commentValue,commentId,nickName,avatar,content,createDate,replyCount,replyId,atId="0";
    private SelectableRoundedImageView avatarImg;
    private boolean flag;
    private CommentReplyResponse.DataBean bean;

    public ReplyDetailPresenter(Context context){
        super(context);
        this.mActivity= (ReplyDetailActivity) context;
        glideImageLoader = new GlideImageLoader();
        basePresenter = BasePresenter.getInstance(context);
        dataAdapter = new NewsReplyAdapter(context);
        dataAdapter.setOnItemClickListener(this);
        gridLayoutManager=new GridLayoutManager(context,1);

    }

    public void init() {
        this.recyclerView =(RecyclerViewUpRefresh)mActivity.findViewById(R.id.recyclerView);
        this.recyclerView.setAdapter(dataAdapter);
        this.recyclerView.setCanloadMore(true);
        this.recyclerView.setLoadMoreListener(this);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.comment = (EditText) mActivity.findViewById(R.id.comment);
        this.btnSend = (TextView) mActivity.findViewById(R.id.btn_send);
        this.btnSend.setOnClickListener(this);
        mActivity.findViewById(R.id.layout_back).setOnClickListener(this);

        Intent intent = mActivity.getIntent();
        commentId = intent.getStringExtra("commentId");
        nickName = intent.getStringExtra("nickName");
        avatar = intent.getStringExtra("avatar");
        content = intent.getStringExtra("content");
        createDate = intent.getStringExtra("createDate");
        replyCount = intent.getStringExtra("replyCount");

        ((TextView)mActivity.findViewById(R.id.text_title)).setText("共"+replyCount+"条回复");
        ((TextView)mActivity.findViewById(R.id.nickname)).setText(nickName);
        ((TextView)mActivity.findViewById(R.id.content)).setText(content);
        ((TextView)mActivity.findViewById(R.id.createdate)).setText(createDate);
        mActivity.findViewById(R.id.txt_thumb_up).setVisibility(View.GONE);
        avatarImg = (SelectableRoundedImageView) mActivity.findViewById(R.id.avatar);
        glideImageLoader.displayImage(context, XtdConst.IMGURI+avatar,avatarImg);
        LoadDialog.show(context);
        atm.request(GETREPLY, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                mActivity.finish();
                break;
            case R.id.btn_send:
                addComment();
                break;
            case R.id.txt_comment2:
                this.layout_comment.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_thumb_up:
                break;

        }
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {

        switch (requestCode) {
            case COMMENTTHUMBUP:
                return mUserAction.replyThumbUp(replyId);
            case COMMENTTHUMBUPOFF:
                return mUserAction.replyThumbUpOff(replyId);
            case GETREPLY:
                return mUserAction.getNewsReply(commentId,"10",lastItemId);
            case ADDREPLY:
                return mUserAction.addReply(comment.getText().toString(),commentId,atId);
        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if(result==null)return;
        switch (requestCode) {
            case GETREPLY:
                CommentReplyResponse commentReplyResponse = (CommentReplyResponse) result;
                if (commentReplyResponse.getCode() == XtdConst.SUCCESS) {
                    List<CommentReplyResponse.DataBean> list = commentReplyResponse.getData();
                    lastItemId = list.get(list.size() - 1).getReply_id();
                    entities.addAll(list);
                    dataAdapter.setListItems(entities);
                    dataAdapter.notifyDataSetChanged();

                }
                NToast.shortToast(context,commentReplyResponse.getMsg());
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
                CommonResponse CommonResponse = (CommonResponse) result;
                if (CommonResponse.getCode() == XtdConst.SUCCESS) {
                    DialogWithYesOrNoUtils.getInstance().showDialog(context,"回复成功",null,null,new AlertDialogCallback());
                    //请求评论
                    lastItemId="0";
                    entities.clear();
                    atm.request(GETREPLY,this);
                }
                NToast.shortToast(context,CommonResponse.getMsg());
                this.atId="0";
                break;

        }
    }

    private void ChangeThumbUp(NewsDetailResponse.DataBean entity,Boolean flag) {
        if(flag || (entity.getIs_like() != null && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP))) {
            Drawable compoundThumbUpOn = mActivity.getDrawable(R.drawable.icon_thumb_up_on);
            compoundThumbUpOn.setBounds(0, 0, 40, 40);
            //this.txtThumbUp.setCompoundDrawables(null, compoundThumbUpOn, null, null);
        }
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
              //  pageIndex ++;
                atm.request(GETREPLY,ReplyDetailPresenter.this);
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
                        dialog.dismiss();
                }
            });
            dialog.setMiddleListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                        dialog.dismiss();
                }
            });

            dialog.show();
        }


    }


    @Override
    public void onItemClick(int position, String userId, String commentId,String nickName) {
        this.comment.setHint("回复："+nickName);
        this.comment.requestFocus();        //自动弹出键盘
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //强制隐藏Android输入法窗口
        // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);

        this.atId=userId;
        this.replyId=commentId;
    }

    @Override
    public void onTxtThumbUpClick(int position, CommentReplyResponse.DataBean bean,TextView t) {
        this.bean=bean;
        this.commentTextView=t;
        CommentThumbUp(bean.getReply_id());
    }
    //评论点赞
    public void CommentThumbUp(String itemId) {
        if(!basePresenter.isLogin)
        {
            NToast.shortToast(context,"请先登录。");
            return;
        }
        this.replyId=itemId;
        LoadDialog.show(context);
        atm.request(COMMENTTHUMBUP,this);
    }
    //回复评论
    public void addComment() {
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

}
