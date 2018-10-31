package com.xtdar.app.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taigo.calendar.CollapseCalendarView;
import com.xtdar.app.R;
import com.xtdar.app.presenter.SignInPresenter;


public class SignInActivity extends BaseActivity {

    private CollapseCalendarView calendarView;
    private boolean show = false;
    private SignInPresenter signInPresenter;
    private TextView txtSignIn,txtSignAll,txtSignContinue,txtSignToday;
    private LinearLayout layoutSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
//
//        txtSignIn=(TextView) findViewById(R.id.txt_sign_in);
//        layoutSignIn=(LinearLayout) findViewById(R.id.layout_sign_in);
//        layoutSignIn.setOnClickListener(this);
//        txtSignAll=(TextView) findViewById(R.id.txt_sign_all);
//        txtSignContinue=(TextView) findViewById(R.id.txt_sign_continue);
//        txtSignToday=(TextView) findViewById(R.id.txt_sign_today);
//
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setTitle("我的积分");
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        calendarView = (CollapseCalendarView) findViewById(R.id.calendar);
//
//        txtSignIn = (TextView) findViewById(R.id.txt_sign_in);
//
//        signInPresenter=new SignInPresenter(this);
//        signInPresenter.init(txtSignIn,txtSignAll,txtSignContinue,txtSignToday,calendarView);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.layout_sign_in:
//                    signInPresenter.signIn();
//                break;
//            case R.id.layout_lottery:
//                break;
//            case R.id.layout_singin:
//                break;
//        }
//    }
}
