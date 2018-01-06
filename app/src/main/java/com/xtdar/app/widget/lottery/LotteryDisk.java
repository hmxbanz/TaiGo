package com.xtdar.app.widget.lottery;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.xtdar.app.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 *
 */
public class LotteryDisk extends View {

    private int[] images = new int[]{R.drawable.loterry_pr_1, R.drawable.loterry_pr_2,
            R.drawable.loterry_pr_3,R.drawable.loterry_pr_4,R.drawable.loterry_pr_5,
            R.drawable.loterry_pr_6,R.drawable.loterry_pr_7,R.drawable.loterry_pr_8,R.drawable.loterry_pr_9,R.drawable.loterry_pr_10};

    private String[] strs = {"Iphone X", "谢谢参与", "精装AR枪", "100积分", "高级AR枪", "50元话费", "OPPO R11s", "U盘", "300积分", "10元话费"};





    private ValueAnimator mAnimtor;
    private AnimationEndListener listener;
    /**
     * 从0开始,偶数圆弧的背景色
     */
    private int mEventArcBgColor = Color.parseColor("#34c3ff");
    /**
     * 奇数圆弧的背景色
     */
    private int mOddArcBgColor = Color.WHITE;
    /**
     * 旋转一圈所需要的时间.默认500ms
     */
    private static int mOneCircleDuration = 600;
    /**
     * 抽奖转盘中条目的个数.默认12个
     */
    private int mItemCount = 12;
    /**
     * 每个弧形跨过的角度
     */
    private float mSweepAngle;
    /**
     * 圆盘的半径
     */
    private int radius = 0;
    /**
     * 圆弧的绘制区域
     */
    private RectF mRectF;
    /**
     * 中奖的position.这个position是根据initAngle(开始的角度)斤算出来的,在转盘转动的过程中不变,其值只与开始的角度有关.
     */
    private int mCriticalPosition;
    /**
     * 中心点X坐标
     */
    private int mCenter;
    /**
     * 最少旋转的圈数
     */
    private int mMinTurnsCount = 15;
    /**
     * 最大旋转的圈数
     */
    private int mMaxTurnsCount = 18;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private Paint mEventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mOddPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mTextColor = Color.parseColor("#935f64");
    private int mTextSize;
    private int mPadding;
    private int mInitAngle = 0;
    private boolean isRotating;

    public LotteryDisk(Context context) {
        this(context, null);
    }

    public LotteryDisk(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotteryDisk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LotteryDisk);
        mEventArcBgColor = typedArray.getColor(R.styleable.LotteryDisk_eventArcBgColor, mEventArcBgColor);
        mOddArcBgColor = typedArray.getColor(R.styleable.LotteryDisk_oddArcBgColor, mOddArcBgColor);
        mTextColor = typedArray.getColor(R.styleable.LotteryDisk_textColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.LotteryDisk_textSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        mMinTurnsCount = typedArray.getInt(R.styleable.LotteryDisk_minTurnsNum, mMinTurnsCount);
        mMaxTurnsCount = typedArray.getInt(R.styleable.LotteryDisk_minTurnsNum, mMaxTurnsCount);
        mOneCircleDuration = typedArray.getInt(R.styleable.LotteryDisk_oneCircleDuration, mOneCircleDuration);
        int itemCountFlag = typedArray.getInt(R.styleable.LotteryDisk_itemCount, 6);
        switch (itemCountFlag) {
            case 6:
                mItemCount = 6;
                mInitAngle = 0;
                break;
            case 8:
                mItemCount = 8;
                mInitAngle = 23;
                break;
            case 10:
                mItemCount = 10;
                mInitAngle = 0;
                break;
            case 12:
                mItemCount = 12;
                mInitAngle = 15;
                break;
        }
        typedArray.recycle();

        mSweepAngle = 360 / mItemCount;

        init();
    }

    private void init() {
        mEventPaint.setColor(mEventArcBgColor);
        mOddPaint.setColor(mOddArcBgColor);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        for (int i = 0; i < mItemCount; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), images[i]);
            bitmaps.add(bitmap);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        // padding值.四个padding值保持一致
        mPadding = getPaddingLeft();

        //设置为正方形
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 中心点X
        mCenter = w / 2;

        //注意:这里的width和height是实际的绘制区域,减去了padding值
        int width = getWidth() - mPadding - mPadding;
        int height = getHeight() - mPadding - mPadding;

        int minValue = Math.min(width, height);
        //半径
        radius = minValue / 2;

        //圆弧的绘制区域
        mRectF = new RectF(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());

        //中奖的position
        mCriticalPosition = (int) ((270 - mSweepAngle / 2 - mInitAngle) / mSweepAngle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int angle = mInitAngle;

        // 绘制圆弧
        for (int i = 0; i < mItemCount; i++) {
            if (i % 2 == 0) {
                canvas.drawArc(mRectF, angle, mSweepAngle, true, mOddPaint);
            } else {
                canvas.drawArc(mRectF, angle, mSweepAngle, true, mEventPaint);
            }
            angle += mSweepAngle;
        }

        //绘制图标
        for (int i = 0; i < mItemCount; i++) {
            drawIcon(radius, mInitAngle, i, canvas);
            mInitAngle += mSweepAngle;
        }

        //绘制文字
        for (int i = 0; i < mItemCount; i++) {
            drawText(mInitAngle, strs[i], getMeasuredWidth() - mPadding * 2, mTextPaint, canvas, mRectF);
            mInitAngle += mSweepAngle;
        }
    }

    /**
     * 绘制文字
     *
     * @param startAngle 开始的角度
     * @param string     要绘制的文字
     * @param radius     文字所在圆弧的直径
     * @param textPaint
     * @param canvas
     * @param range      文字绘制的RectF
     */
    private void drawText(float startAngle, String string, float radius, Paint textPaint, Canvas canvas, RectF range) {

        Path path = new Path();
        path.addArc(range, startAngle, mSweepAngle);
        float textWidth = textPaint.measureText(string);
        // 利用水平偏移让文字居中
        float hOffset = (float) (radius * Math.PI / mItemCount / 2 - textWidth / 2);// 水平偏移
        float vOffset = radius / 2 / 6;// 垂直偏移
        canvas.drawTextOnPath(string, path, hOffset, vOffset, textPaint);
    }

    /**
     * 绘制图标
     *
     * @param radius     圆弧半径
     * @param startAngle
     * @param i
     * @param canvas
     */
    private void drawIcon(int radius, float startAngle, int i, Canvas canvas) {

        // 图片的宽度
        int imgWidth = radius / 4;
        //将角度转为弧度
        float angle = (float) ((360 / mItemCount / 2 + startAngle) * (Math.PI / 180));
        //计算绘制图片的区域的中心点
        int x = (int) (mCenter + radius * 3 / 5 * Math.cos(angle));
        int y = (int) (mCenter + radius * 3 / 5 * Math.sin(angle));

        // 确定绘制图片的位置
        Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth / 2, y + imgWidth / 2);

        canvas.drawBitmap(bitmaps.get(i), null, rect, null);
    }

    /**
     * 开始转动
     *
     * @param pos 如果 pos = -1 则随机，如果指定某个值，则转到某个指定区域
     */
    public void startRotate(int pos) {
        if (isRotating) return;
        if (pos >= mItemCount){
            throw new IllegalArgumentException("the position must not >= item count");
        }

        int lap = (int) (mMinTurnsCount + Math.random() * (mMaxTurnsCount - mMinTurnsCount + 1));//产生15-18之间的int型随机数

        int angle = getLastCircleAngle(pos);

        int DesRotate = getDesRotate(lap, angle);

        long time = (lap + angle / 360) * mOneCircleDuration;

        mAnimtor = ValueAnimator.ofInt(mInitAngle, DesRotate);
        mAnimtor.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimtor.setDuration(time);
        mAnimtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int updateValue = (int) animation.getAnimatedValue();
                mInitAngle = updateValue % 360;
                ViewCompat.postInvalidateOnAnimation(LotteryDisk.this);
            }
        });
        mAnimtor.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isRotating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isRotating = false;
                if (listener != null) {
                    listener.endAnimation(queryPosition());
                }
            }
        });
        mAnimtor.start();
    }

    /**
     * 计算随机旋转到多少角度
     *
     * @param lap   随机数
     * @param angle 最后一圈实际旋转的角度
     * @return
     */
    private int getDesRotate(int lap, int angle) {
        //要旋转的角度
        int increaseDegree = lap * 360 + angle;
        //要旋转到的角度
        int desRotate = increaseDegree + mInitAngle;

        //为了每次都能旋转到转盘的中间位置
        int offRotate = (int) (desRotate % 360 % mSweepAngle);
        desRotate -= offRotate;
        if (mItemCount == 8 || mItemCount == 12) {
            desRotate += mSweepAngle / 2;//改变这个值可以控制转盘停止时指针的位置
        }
        return desRotate;
    }

    /**
     * 根据要到达的position计算最后一圈实际旋转的角度
     *
     * @param pos
     * @return
     */
    private int getLastCircleAngle(int pos) {
        int angle = 0;
        if (pos < 0) {
            angle = (int) (Math.random() * 360);
        } else {
            int initPos = queryPosition();
            if (pos > initPos) {
                angle = 360 - (int) ((pos - initPos) * mSweepAngle);
            } else if (pos < initPos) {
                angle = (int) ((initPos - pos) * mSweepAngle);
            } else {
                //nothing to do.

            }
        }
        return angle;
    }

    private int queryPosition() {
        mInitAngle = mInitAngle % 360;
        int pos = (int) (mInitAngle / mSweepAngle);
        Log.e(TAG, "mInitAngle: " + mInitAngle+"; pos: "+pos);
        if (pos >= 0 && pos <= mCriticalPosition) {
            pos = mCriticalPosition - pos;
        } else {
            pos = (mItemCount - pos) + mCriticalPosition;
        }
        return pos;
    }

    public interface AnimationEndListener {
        void endAnimation(int position);
    }

    public void setAnimationEndListener(AnimationEndListener listener) {
        this.listener = listener;
    }

    public void setImages(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
        this.invalidate();
    }

    public void setStr(String... strs) {
        this.strs = strs;
        this.invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearAnimation();
    }
}
