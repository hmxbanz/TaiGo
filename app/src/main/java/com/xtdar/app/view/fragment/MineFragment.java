package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.common.NLog;
import com.xtdar.app.common.PhotoUtils;
import com.xtdar.app.presenter.MinePresenter;
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.view.activity.MessageActivity;
import com.xtdar.app.view.activity.MyCommentActivity;
import com.xtdar.app.view.activity.DynamicActivity;
import com.xtdar.app.view.activity.MeActivity;
import com.xtdar.app.view.activity.FavorActivity;
import com.xtdar.app.view.activity.MyVideoActivity;
import com.xtdar.app.view.activity.ScoreActivity;
import com.xtdar.app.view.activity.SettingActivity;
import com.xtdar.app.view.widget.BottomMenuDialog;
import com.xtdar.app.view.widget.DragPointView;
import com.xtdar.app.view.widget.SelectableRoundedImageView;
import com.xtdar.app.widget.progressBar.MaterialProgressBar;

import com.xtdar.app.R;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private static final int COMPAREVERSION = 54;
    public static final String SHOWRED = "SHOWRED";
    public static MineFragment mFragment = null;
    private RelativeLayout mLayoutAr, mLayoutMyVideo, mLayoutMsg;
    private LinearLayout mLayoutTitle, mLayoutFavor, mLayoutSetting, mLayoutComment,mLayoutShopCar,mLayoutOrder,mLayoutScore;
    private View mView;

    private SelectableRoundedImageView mImageView;
    private ImageView mImageSetting;
    private PhotoUtils photoUtils;
    private BottomMenuDialog dialog;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private Uri selectUri;
    private MaterialProgressBar progressBar;
    private LinearLayout mTxtMe;
    private RelativeLayout layout_back;
    private TextView title;
    private MinePresenter presenter;
    private TextView nickName;
    private DragPointView unreadNumView;

    public static MineFragment getInstance() {
        if (mFragment == null) {
            mFragment = new MineFragment();
        }
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, null);
        initViews();
        presenter = new MinePresenter(getActivity());
        presenter.init(mImageView,nickName,unreadNumView);
        //initData();
//        compareVersion();
        NLog.d("fragment-----","onCreateView");
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //presenter.getInfo();
        NLog.d("fragment-----","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        NLog.d("fragment-----","onResume");
    }

    private void initViews() {
        layout_back=(RelativeLayout)mView.findViewById(R.id.layout_back);
        layout_back.setVisibility(View.INVISIBLE);
        title=(TextView)mView.findViewById(R.id.text_title);
        title.setText("我的");
//        mImageSetting = (ImageView) mView.findViewById(R.id.img_setting);
//        mImageSetting.setOnClickListener(this);
        mImageView = (SelectableRoundedImageView) mView.findViewById(R.id.img_avator);
        mImageView.setOnClickListener(this);
        nickName=(TextView)mView.findViewById(R.id.nick_name);

        mLayoutAr = (RelativeLayout) mView.findViewById(R.id.layout_dynamic);
        mLayoutAr.setOnClickListener(this);
        mLayoutMsg = (RelativeLayout) mView.findViewById(R.id.layout_message);
        mLayoutMsg.setOnClickListener(this);
        mLayoutMyVideo = (RelativeLayout) mView.findViewById(R.id.layout_my_video);
        mLayoutMyVideo.setOnClickListener(this);
        mLayoutFavor = (LinearLayout) mView.findViewById(R.id.layout_favor);
        mLayoutFavor.setOnClickListener(this);

        mLayoutComment = (LinearLayout) mView.findViewById(R.id.layout_comment);
        mLayoutComment.setOnClickListener(this);
        mLayoutSetting = (LinearLayout) mView.findViewById(R.id.layout_setting);
        mLayoutSetting.setOnClickListener(this);
        unreadNumView =(DragPointView) mView.findViewById(R.id.unread_num);

        mLayoutShopCar = (LinearLayout) mView.findViewById(R.id.layout_shop_car);
        mLayoutShopCar.setOnClickListener(this);
        mLayoutOrder = (LinearLayout) mView.findViewById(R.id.layout_order);
        mLayoutOrder.setOnClickListener(this);
        mLayoutTitle = (LinearLayout) mView.findViewById(R.id.layout_title);
        mLayoutTitle.setOnClickListener(this);
        mLayoutScore = (LinearLayout) mView.findViewById(R.id.layout_score);
        mLayoutScore.setOnClickListener(this);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BroadcastManager.getInstance(getActivity()).destroy(MinePresenter.UPDATEUNREAD);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.layout_dynamic:
                startActivity(new Intent(getActivity(), DynamicActivity.class));
                break;
            case R.id.layout_title:
                startActivity(new Intent(getActivity(), MeActivity.class));
                break;
            case R.id.layout_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.layout_my_video:
                startActivity(new Intent(getActivity(), MyVideoActivity.class));
                break;
            case R.id.layout_comment:
                startActivity(new Intent(getActivity(), MyCommentActivity.class));
                break;
            case R.id.layout_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.layout_favor:
            startActivity(new Intent(getActivity(), FavorActivity.class));
            break;
            case R.id.layout_score:
                startActivity(new Intent(getActivity(), ScoreActivity.class));
                break;
            case R.id.layout_shop_car:
                presenter.openShopCar();
                break;
            case R.id.layout_order:
                presenter.openOrder();
                break;
        }
    }



}
