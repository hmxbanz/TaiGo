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

import com.xtdar.app.common.PhotoUtils;
import com.xtdar.app.view.activity.AlbumActivity;
import com.xtdar.app.view.activity.DeviceActivity;
import com.xtdar.app.view.activity.DownloadActivity;
import com.xtdar.app.view.activity.DynamicActivity;
import com.xtdar.app.view.activity.HistoryActivity;
import com.xtdar.app.view.activity.MeActivity;
import com.xtdar.app.view.activity.SettingActivity;
import com.xtdar.app.view.activity.FavorActivity;
import com.xtdar.app.view.widget.BottomMenuDialog;
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
    private RelativeLayout mLayoutFavor,mLayoutDynamic,mLayoutHistory;
    private LinearLayout mLayoutFriendCondition,mLayoutVisitRecord,mLayoutDevice,mLayoutAblum;
    private View mView;

    private SelectableRoundedImageView mImageView;
    private ImageView mImageSetting;
    private PhotoUtils photoUtils;
    private BottomMenuDialog dialog;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private Uri selectUri;
    private MaterialProgressBar progressBar;
    private TextView mTxtMe;



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
//        initData();
//        BroadcastManager.getInstance(getActivity()).addAction(SealConst.CHANGEINFO, new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String userid = sp.getString("loginid", "");
//                String username = sp.getString("loginnickname", "");
//                String userportrait = sp.getString("loginPortrait", "");
//                mName.setText(username);
//                ImageLoader.getInstance().displayImage(TextUtils.isEmpty(userportrait) ? RongGenerate.generateDefaultAvatar(username, userid) : userportrait, imageView, App.getOptions());
//            }
//        });
//        compareVersion();
        return mView;
    }

    private void initViews() {
        mImageSetting = (ImageView) mView.findViewById(R.id.img_setting);
        mImageSetting.setOnClickListener(this);
        mImageView = (SelectableRoundedImageView) mView.findViewById(R.id.img_avator);
        mImageView.setOnClickListener(this);

        mLayoutFriendCondition= (LinearLayout) mView.findViewById(R.id.layout_friend_condition);
        mLayoutFriendCondition.setOnClickListener(this);

        mLayoutFavor= (RelativeLayout) mView.findViewById(R.id.layout_favor);
        mLayoutFavor.setOnClickListener(this);
        mLayoutHistory= (RelativeLayout) mView.findViewById(R.id.layout_history);
        mLayoutHistory.setOnClickListener(this);
        mLayoutDynamic= (RelativeLayout) mView.findViewById(R.id.layout_dynamic);
        mLayoutDynamic.setOnClickListener(this);
        mLayoutVisitRecord= (LinearLayout) mView.findViewById(R.id.layout_download);
        mLayoutVisitRecord.setOnClickListener(this);

        mLayoutAblum= (LinearLayout) mView.findViewById(R.id.layout_album);
        mLayoutAblum.setOnClickListener(this);
        mLayoutDevice= (LinearLayout) mView.findViewById(R.id.layout_device);
        mLayoutDevice.setOnClickListener(this);
        mTxtMe = (TextView) mView.findViewById(R.id.txt_me);
        mTxtMe.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.txt_me:
                startActivity(new Intent(getActivity(), MeActivity.class));
                break;
            case R.id.layout_favor:
                startActivity(new Intent(getActivity(), FavorActivity.class));
                break;
            case R.id.layout_history:
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;
            case R.id.layout_dynamic:
                startActivity(new Intent(getActivity(), DynamicActivity.class));
                break;
            case R.id.layout_album:
                startActivity(new Intent(getActivity(), AlbumActivity.class));
                break;
            case R.id.layout_device:
                startActivity(new Intent(getActivity(), DeviceActivity.class));
                break;
            case R.id.layout_download:
            startActivity(new Intent(getActivity(), DownloadActivity.class));
            break;
        }
    }



}
