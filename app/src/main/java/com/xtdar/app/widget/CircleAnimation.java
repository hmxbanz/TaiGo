package com.xtdar.app.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.xtdar.app.R;


/**
 * Created by Administrator on 2018/1/6.
 */

public class CircleAnimation extends ConstraintLayout {
    private final ImageView p03,p02,p01;

    public CircleAnimation(Context context, AttributeSet attrs) {
        super(context);

        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.circle_animation, this);

        // 获取控件
        p03 = (ImageView) findViewById(R.id.p03);
        p02 = (ImageView) findViewById(R.id.p02);
        p01 = (ImageView) findViewById(R.id.p01);


        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f
                , Animation.RELATIVE_TO_SELF,
                0.5f
        );
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        //rotateAnimation.setFillAfter(true);//停在最后
        //rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setDuration(7000);

        RotateAnimation rotateAnimation2 = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f
                , Animation.RELATIVE_TO_SELF,
                0.5f
        );
        rotateAnimation2.setInterpolator(new LinearInterpolator());
        rotateAnimation2.setRepeatCount(ValueAnimator.INFINITE);
        //rotateAnimation.setFillAfter(true);//停在最后
        //rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation2.setDuration(6000);

        RotateAnimation rotateAnimation3 = new RotateAnimation(
                360,
                0,
                Animation.RELATIVE_TO_SELF,
                0.5f
                , Animation.RELATIVE_TO_SELF,
                0.5f
        );
        rotateAnimation3.setInterpolator(new LinearInterpolator());
        rotateAnimation3.setRepeatCount(ValueAnimator.INFINITE);
        //rotateAnimation.setFillAfter(true);//停在最后
        //rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation3.setDuration(5000);

        p03.startAnimation(rotateAnimation);
        p02.startAnimation(rotateAnimation3);
        p01.startAnimation(rotateAnimation2);


    }

}
