package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.TagResponse;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.MineActivity;
import com.xtdar.app.view.activity.SignInActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class DiscoveryPresenter extends BasePresenter implements OnDataListener {
    private static final int GETTAGS = 1;
    private TabLayout tabLayout;
    private List<TagResponse.DataBean> tagList;
    private final BasePresenter basePresenter;
    //private ContactsActivity mActivity;
    public DiscoveryPresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(TabLayout tabLayout) {
        this.tabLayout=tabLayout;
        //tabLayout.getTabAt(0).setText("设备");//淘设备
        tabLayout.getTabAt(0).setText("视频");//牛人秀//发布
        tabLayout.getTabAt(1).setText("资讯");
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETTAGS:
                return mUserAction.getTags();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        switch (requestCode) {
            case GETTAGS:
                TagResponse tagResponse = (TagResponse) result;
                if (tagResponse.getCode() == XtdConst.SUCCESS) {
                     tagList = tagResponse.getData();
                    try {
                        String cache= JsonMananger.beanToJson(tagList);
                        aCache.put("TagList",cache);
                    } catch (HttpException e) {
                        e.printStackTrace();
                    }
//                    for (int i = 0; i<= tagList.size(); i++) {
//                        tabLayout.getTabAt(i).setText(tagList.get(i).getClass_name());
//                    }
                }
                break;
        }
    }

    public void onMeClick(View v) {
        basePresenter.initData();
        if (!basePresenter.isLogin) {
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", null, "前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            });
        } else {
            switch (v.getId()) {
                case R.id.layout_me:
                    context.startActivity(new Intent(context, MineActivity.class));
                    break;
                case R.id.right_icon:
                    context.startActivity(new Intent(context, SignInActivity.class));
                    break;
            }

        }

    }
}