package com.xtdar.app.widget.scaleIndicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.xtdar.app.R;

/**
 * Created by hmxbanz on 2017/2/28.
 */

public class ScaleIndicatorLayout extends LinearLayout {

    private int minSize, increase;
    private ArrayList<ScaleIndicatorView> indicators = new ArrayList<>();

    public ScaleIndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ScaleIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public ScaleIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ScaleIndicatorLayout, defStyleAttr, defStyleRes);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        minSize = a.getDimensionPixelSize(R.styleable.ScaleIndicatorLayout_indicatorMinSize, -1);
        if (minSize == -1) {
            minSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, dm);
        }
        increase = a.getDimensionPixelSize(R.styleable.ScaleIndicatorLayout_indicatorIncreaseSize, 0);
        a.recycle();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child instanceof ScaleIndicatorView) {
            ScaleIndicatorView id = (ScaleIndicatorView) child;
            id.initSize(minSize, increase);
            if (index == -1) {
                indicators.add(id);
            } else {
                fixCache();
            }
        }
    }

    private void fixCache() {
        indicators.clear();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof ScaleIndicatorView) {
                indicators.add((ScaleIndicatorView) child);
            }
        }
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        if (view instanceof ScaleIndicatorView) {
            indicators.remove(view);
        }
    }

    public void setRadio(int position, float radio) {
        if (position < indicators.size()) {
            ScaleIndicatorView next = indicators.get(position);
            next.setRadio(radio);
        }

    }
}
