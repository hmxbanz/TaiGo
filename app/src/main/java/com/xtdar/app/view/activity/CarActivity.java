package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.xtdar.app.R;
import com.xtdar.app.common.NLog;
import com.xtdar.app.presenter.CarPresenter;
import com.xtdar.app.widget.RockerView;


public class CarActivity extends BaseActivity  {

    private static final String TAG = "MainActivity";
    private ImageButton btnControlTop,btnControlBottom,btnControlLeft,btnControlRight;
    private Button btnTurnLeft,btnTurnRight,btnGet,btnLeftGo;

    void doLog(String log) {
        Log.e(TAG, log);
    }

    private RockerView rockerView1;
    private RockerView rockerView2;
    int screenWidth;
    int screenHeight;
    private CarPresenter carPresenter;

    private String ServiceId,WriteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 设置全屏		// ,		// 屏幕长亮
        setContentView(R.layout.activity_car);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        rockerView1 = (RockerView) findViewById(R.id.rockerView1);

        btnControlTop = (ImageButton) findViewById(R.id.car_control_top);
        btnControlLeft = (ImageButton) findViewById(R.id.car_control_left);
        btnControlBottom = (ImageButton) findViewById(R.id.car_control_bottom);
        btnControlRight = (ImageButton) findViewById(R.id.car_control_right);

        btnTurnLeft = (Button) findViewById(R.id.turn_left);
        btnTurnRight = (Button) findViewById(R.id.turn_right);
        btnGet = (Button) findViewById(R.id.btn_get);
        btnLeftGo = (Button) findViewById(R.id.btn_left_go);

        Intent intent = getIntent();
        ServiceId=intent.getStringExtra("ServiceId");
        WriteId=intent.getStringExtra("WriteId");

        carPresenter = new CarPresenter(this);
        carPresenter.init(rockerView1,btnControlTop,btnControlLeft,btnControlBottom,btnControlRight
        ,btnTurnLeft,btnTurnRight,btnGet,btnLeftGo,ServiceId,WriteId);

    }

    public void setLayout(View v, int dx, int dy) {
        int left = v.getLeft() + dx;
        int top = v.getTop() + dy;
        int right = v.getRight() + dx;
        int bottom = v.getBottom() + dy;
        if (left < 0) {
            left = 0;
            right = left + v.getWidth();
        }
        if (right > screenWidth) {
            right = screenWidth;
            left = right - v.getWidth();
        }
        if (top < 0) {
            top = 0;
            bottom = top + v.getHeight();
        }
        if (bottom > screenHeight) {
            bottom = screenHeight;
            top = bottom - v.getHeight();
        }
        v.layout(left, top, right, bottom);
    }

}

//    前	w
//    后	s
//    左	a
//    右	d


//   前臂	    上	front
//              下	back
//              松开	stopF
//   后臂	    上	up
//              下	down
//              松开	stop
//   底盘	    左	left
//              右	right
//              松开	stopR
//   抓手	    抓住	catch
//              松开	release