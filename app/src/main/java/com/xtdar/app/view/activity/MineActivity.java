package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.MinePresenter;
import com.xtdar.app.presenter.SettingPresenter;
import com.xtdar.app.server.broadcast.BroadcastManager;
import com.xtdar.app.view.widget.DragPointView;
import com.xtdar.app.view.widget.SelectableRoundedImageView;


public class MineActivity extends BaseActivity implements View.OnClickListener {
    private MinePresenter presenter;
    private TextView nickName;
    private SelectableRoundedImageView mImageView;
    private DragPointView unreadNumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        initViews();
        presenter = new MinePresenter(this);
        presenter.init(mImageView,nickName,unreadNumView);
    }
    public void initViews(){
        findViewById(R.id.layout_back).setOnClickListener(this);
        findViewById(R.id.layout_lottery).setOnClickListener(this);
        nickName=(TextView)findViewById(R.id.nick_name);

        mImageView = (SelectableRoundedImageView)findViewById(R.id.img_avator);
        mImageView.setOnClickListener(this);

        findViewById(R.id.layout_dynamic).setOnClickListener(this);
        findViewById(R.id.layout_message).setOnClickListener(this);
        findViewById(R.id.layout_my_video).setOnClickListener(this);
        findViewById(R.id.layout_favor).setOnClickListener(this);

        findViewById(R.id.layout_comment).setOnClickListener(this);
        findViewById(R.id.layout_setting).setOnClickListener(this);
        unreadNumView =(DragPointView) findViewById(R.id.unread_num);

        findViewById(R.id.layout_shop_car).setOnClickListener(this);
        findViewById(R.id.layout_order).setOnClickListener(this);
        findViewById(R.id.layout_title).setOnClickListener(this);
        findViewById(R.id.layout_score).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.layout_lottery:
                startActivity(new Intent(this,LotteryActivity.class));
                break;
            case R.id.layout_dynamic:
                startActivity(new Intent(this, DynamicActivity.class));
                break;
            case R.id.layout_title:
                startActivity(new Intent(this, MeActivity.class));
                break;
            case R.id.layout_message:
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.layout_my_video:
                startActivity(new Intent(this, MyVideoActivity.class));
                break;
            case R.id.layout_comment:
                startActivity(new Intent(this, MyCommentActivity.class));
                break;
            case R.id.layout_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.layout_favor:
                startActivity(new Intent(this, FavorActivity.class));
                break;
            case R.id.layout_score:
                startActivity(new Intent(this, ScoreActivity.class));
                break;
            case R.id.layout_shop_car:
                presenter.openShopCar();
                break;
            case R.id.layout_order:
                presenter.openOrder();
                break;

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        BroadcastManager.getInstance(this).destroy(MinePresenter.UPDATEUNREAD);
    }

}
