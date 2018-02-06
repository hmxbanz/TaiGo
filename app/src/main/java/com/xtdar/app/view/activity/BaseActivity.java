package com.xtdar.app.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

/**
 * Created by hmx on 2016/5/21.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected TextView txtTitle;
    protected RelativeLayout layout_back;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //x.view().inject(this);
        sp = getSharedPreferences("UserConfig", MODE_PRIVATE);
        editor = sp.edit();
        if(this instanceof DetailActivity)
            return;
        else if(this instanceof ShowDetailActivity)
            return;
        else if(this instanceof NewsDetailActivity)
            return;
        else if(this instanceof ReplyDetailActivity)
            return;
        else
            setStatusBar();
        }


    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
        //StatusBarUtil.setColor(this, getResources().getColor(R.color.titleBlue));
    }

    @Override
    public void onClick(View v) {

    }
}
