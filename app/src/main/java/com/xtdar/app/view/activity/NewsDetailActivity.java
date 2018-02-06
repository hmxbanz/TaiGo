package com.xtdar.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaeger.library.StatusBarUtil;
import com.lzy.ninegrid.NineGridView;
import com.xtdar.app.R;
import com.xtdar.app.presenter.NewsDetailPresenter;
import com.xtdar.app.widget.myRecyclerView.RecyclerViewUpRefresh;

import java.util.ArrayList;
import java.util.List;


public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {
    public static List<?> images=new ArrayList<>();
    private RecyclerViewUpRefresh recycleView;
    public ScrollView scrollView;

    private NewsDetailPresenter presenter;

    private EditText comment;
    private TextView btnSend;
    private TextView txtHead;
    private View layoutComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.titleBlue),0);
        initViews();
        presenter = new NewsDetailPresenter(this);
        presenter.init();
    }

    private void initViews() {
        //mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
//
//        recycleView= (RecyclerViewUpRefresh) findViewById(R.id.recyclerView);
//
//        AppBarLayout app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        txtHead =(TextView) findViewById(R.id.txt_head);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("文章详情");
        txtTitle.setOnClickListener(this);
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


    public static void StartActivity(Context context,String itemId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("recommend_id",itemId);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
//        if(newsContentTextTv != null){
//            newsContentTextTv.cancelImageGetterSubscription();
//        }
        super.onDestroy();
    }
}
