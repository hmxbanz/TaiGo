package com.xtdar.app.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.SearchPresenter;


public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private SearchPresenter mLoginPresenter;
    private TextView mTextSearch;
    private Button mBtnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        mLoginPresenter = new SearchPresenter(this);
        mLoginPresenter.init();
    }

    private void initViews() {
        mTextSearch= (TextView) findViewById(R.id.txt_search);
        Drawable icon_search=getResources().getDrawable(R.drawable.icon_search);
        icon_search.setBounds(0,0,50,50);
        //if(Build.VERSION.SDK_INT>+21)
        //icon_search.setTint(getResources().getColor(R.color.wheat));
        mTextSearch.setCompoundDrawables(icon_search,null,null,null);
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("搜索");

        Drawable drawable = this.getResources().getDrawable(R.drawable.icon_circle_arrow);
        drawable.setBounds(0,0,55,55);
//        if(Build.VERSION.SDK_INT>=21)
//            drawable.setTint(getResources().getColor(R.color.white));
        mBtnChange= (Button) findViewById(R.id.btn_change);
        mBtnChange.setCompoundDrawables(drawable,null,null,null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;

        }
    }
}
