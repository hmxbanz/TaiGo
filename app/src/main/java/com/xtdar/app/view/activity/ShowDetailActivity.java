package com.xtdar.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.NineGridView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.RecyclerViewAdapter;
import com.xtdar.app.presenter.ShowDetailPresenter;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;

import java.util.ArrayList;
import java.util.List;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;


public class ShowDetailActivity extends BaseActivity implements RecyclerViewAdapter.ItemClickListener,View.OnClickListener {
    public static List<?> images=new ArrayList<>();
    private RecyclerViewUpRefresh recycleView;
    public ScrollView scrollView;
    private View view;

    ///////////////////////////
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private final String[] mTabTitles = {"个人资料", "条件", "动态", "相册"};
    private ViewPager mViewPager;
    private ShowDetailPresenter presenter;
    private StandardGSYVideoPlayer videoPlayer;

    private TextView title;
    private LinearLayout layoutShare;
    private EditText comment;
    private TextView btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_detail);

        initViews();
        //initDatas();
        presenter = new ShowDetailPresenter(this);
        presenter.init(videoPlayer,title,recycleView);

    }

    private void initViews() {
        //mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);

        recycleView= (RecyclerViewUpRefresh) findViewById(R.id.recyclerView);

        AppBarLayout app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        videoPlayer= (StandardGSYVideoPlayer) findViewById(R.id.detail_player);
        title = (TextView) findViewById(R.id.title);
        comment = (EditText) findViewById(R.id.comment);
        btnSend = (TextView) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        layoutShare = (LinearLayout) findViewById(R.id.layout_share);
        layoutShare.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_share:
                presenter.share();
                break;
            case R.id.btn_send:
                presenter.addComment(comment);
                break;

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_report:
                startActivity(new Intent(this,FeedbackActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /** Glide 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_color)//
                    .error(R.drawable.ic_default_color)//
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    private void resolveNormalVideoUI() {
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        videoPlayer.getTitleTextView().setText("测试视频");
        videoPlayer.getBackButton().setVisibility(View.GONE);
    }
    @Override
    public void onItemClick(int position, String data) {
        startActivity(new Intent(this, Main2Activity.class));
        Toast.makeText(this,"你点击了位置："+String.valueOf(position)+"-标题："+data,Toast.LENGTH_SHORT).show();
    }

    public static void StartActivity(Context context,String itemId,String classId) {
        Intent intent = new Intent(context, ShowDetailActivity.class);
        intent.putExtra("show_id",itemId);
        intent.putExtra(XtdConst.CLASSID,classId);
        context.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }
    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        finish();
    }
}
