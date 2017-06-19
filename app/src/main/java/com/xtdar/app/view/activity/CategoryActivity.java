package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtdar.app.R;
import com.xtdar.app.adapter.RecyclerViewAdapterForShop;
import com.xtdar.app.model.CategoryList;
import com.xtdar.app.presenter.CategoryPresenter;


public class CategoryActivity extends BaseActivity implements View.OnClickListener {

    private CategoryPresenter mCategoryPresenter;
    private TextView mTextBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initViews();
        mCategoryPresenter =new CategoryPresenter(this);
        mCategoryPresenter.init();

    }
    public void initViews(){
        layout_back= (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        mTextBack =(TextView) findViewById(R.id.text_back);
        mTextBack.setText("");

        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("全部分类");


        //头部
        RecyclerView recycleViewTop= (RecyclerView) findViewById(R.id.category_recyclerview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
        recycleViewTop.setLayoutManager(gridLayoutManager);
        RecyclerViewAdapterForShop dataAdapter2 = new RecyclerViewAdapterForShop(CategoryList.getData(), this);
        dataAdapter2.setOnItemClickListener(new RecyclerViewAdapterForShop.ItemClickListener() {
            @Override
            public void onItemClick(int position, String data) {
                Toast.makeText(CategoryActivity.this, position, Toast.LENGTH_LONG).show();

            }
        });
        recycleViewTop.setAdapter(dataAdapter2);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.img_avator:
                break;
        }
    }

}
