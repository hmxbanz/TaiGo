package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.SettingActivity;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.io.File;

import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class SettingPresenter extends BasePresenter {
    SettingActivity mActivity;
    BasePresenter basePresenter;

    public SettingPresenter(Context context) {
        //this.mActivity =(SettingActivity)context;
        super(context);
        basePresenter = BasePresenter.getInstance(context);
    }
    public void init(){
    //mView.initData();
    };
public void logOff()
{
    DialogWithYesOrNoUtils.getInstance().showDialog(context, "确定要退出TaiGo吗?",null,null, new AlertDialogCallback() {
        @Override
        public void executeEvent() {
            //File file = new File(Environment.getExternalStorageDirectory().getPath() + getPackageName());
            //deleteFile(file);
            editor.putBoolean(XtdConst.ISLOGIN, false);//退出改登录标记
            editor.putString(XtdConst.LOGIN_USERNAME, "");
            editor.putString(XtdConst.LOGING_PASSWORD, "");
            editor.commit();
            basePresenter.initData();
            NToast.shortToast(context, "退出成功");
            context.startActivity(new Intent(context, LoginActivity.class));
        }

    });

}

    public void shareTaigo() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("我来分享TaiGo啦");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("TaiGo APP是一款集合智能玩具硬件设备和内容云服务于一体的应用软件，内容包括益智游戏，智能科教、AR应用体验、智能遥控、语音互动等互联功能。");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("https://mmbiz.qlogo.cn/mmbiz_png/ZibhOLLbKWvwS7U0Oz3X5GpqQUfg3tzj51syj6RvCy2iaUbNs9IGhXAeC6xGsywz0FgeELiaWyDQEh4ibKhm9z6WQQ/0?wx_fmt=png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.xtdar.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }
}
