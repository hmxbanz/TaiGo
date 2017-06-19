package com.xtdar.app.widget.scaleIndicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hmxbanz on 2017/2/28.
 */

public class ScaleIndicatorView extends View {
    int minSize, increase;
    private int curSize;

    public ScaleIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ScaleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleAttr);
    }

    @TargetApi(21)
    public ScaleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (getBackground() == null) {
            Shape shape = new OvalShape();
            ShapeDrawable draw = new ShapeDrawable(shape);
            draw.getPaint().setColor(0xffffffff);
            if (Build.VERSION.SDK_INT >= 16) {
                setBackground(draw);
            } else {
                setBackgroundDrawable(draw);
            }
        }
    }

    void initSize(int minSize, int increase) {
        this.minSize = minSize;
        this.increase = increase;
        curSize = minSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(curSize, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(curSize, MeasureSpec.EXACTLY)
        );
    }

    void setRadio(float radio) {
        curSize = (int) (minSize + increase * radio);
        requestLayout();
    }
}
