package com.ms.bottombar.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import com.ms.bottombar.R;

public class RefreshView extends View {

    private int mWidth;
    private int mHeight;

    /*
     * 圆点
     */
    private int mCircleRadius;      // 大小
    private int mCircleColor;       // 颜色
    private Paint mCirclePaint;

    private Paint mPathPaint;
    private int mPathColor;         // 颜色
    private int mPathStroke;        // 大小
    private Path mPath;

    private Path mInnerPath;
    private RectF mInnerRectF;

    private RectF mOutPathRectF;
    private Paint mOutPathPaint;

    private RectF mMiddleRectF;
    private int mStartAngle;
    private float mCurrentAngle;
    private float mCurrentCircleRadius;
    private int mBeginAlpha;
    private int mNoPaintArcAngle;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RefreshView);
        mWidth = array.getDimensionPixelSize(R.styleable.RefreshView_rv_width, 200);
        mHeight = array.getDimensionPixelSize(R.styleable.RefreshView_rv_height, 200);
        mCircleColor = array.getColor(R.styleable.RefreshView_rv_circle_color, Color.RED);
        mPathColor = array.getColor(R.styleable.RefreshView_rv_arc_color, Color.RED);
        mCircleRadius = array.getDimensionPixelSize(R.styleable.RefreshView_rv_circle_radius, mWidth / 7);
        mPathStroke = array.getDimensionPixelSize(R.styleable.RefreshView_rv_arc_stroke, 4);
        mStartAngle = array.getDimensionPixelSize(R.styleable.RefreshView_rv_start_angle, -180);
        mNoPaintArcAngle = array.getDimensionPixelSize(R.styleable.RefreshView_rv_no_paint_angle, 30);
        array.recycle();

        mBeginAlpha = 255 / 2;
        mCurrentAngle = mStartAngle;
        mCurrentCircleRadius = 0;

        init();
    }

    private void init() {
        mMiddleRectF = new RectF();
        mInnerRectF = new RectF();
        mOutPathRectF = new RectF();

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mPathPaint = new Paint();
        mPathPaint.setColor(mPathColor);
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(mPathStroke);
        mPath = new Path();

        mOutPathPaint = new Paint();
        mOutPathPaint.setColor(mPathColor);
        mOutPathPaint.setAntiAlias(true);
        mOutPathPaint.setStyle(Paint.Style.STROKE);
        mOutPathPaint.setStrokeWidth(mPathStroke);
        mOutPathPaint.setAlpha(mBeginAlpha);
        mInnerPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mOutPathRectF.set(mPathStroke, mPathStroke, mWidth - mPathStroke, mHeight - mPathStroke);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mWidth / 2, mHeight / 2, mCurrentCircleRadius, mCirclePaint);

        mInnerRectF.set(mCircleRadius + mCircleRadius - mCurrentCircleRadius + mPathStroke,
                mCircleRadius + mCircleRadius - mCurrentCircleRadius + mPathStroke,
                mWidth - mCircleRadius - mCircleRadius + mCurrentCircleRadius - mPathStroke,
                mHeight - mCircleRadius - mCircleRadius + mCurrentCircleRadius - mPathStroke);
        mInnerPath.arcTo(mInnerRectF, 360 - mNoPaintArcAngle - mCircleRadius / 2, (float) (mCurrentAngle + 0.5), false);
        float[] coords = new float[]{0f, 0f};
        PathMeasure measure = new PathMeasure(mInnerPath, false);
        measure.getPosTan((1) * measure.getLength() / 1, coords, null);
        int x = (int) coords[0];
        int y = (int) coords[1];

        mMiddleRectF.set(mCircleRadius - mCurrentCircleRadius + mPathStroke,
                mCircleRadius - mCurrentCircleRadius + mPathStroke,
                mWidth - mCircleRadius + mCurrentCircleRadius - mPathStroke,
                mHeight - mCircleRadius + mCurrentCircleRadius - mPathStroke);
        mPath.arcTo(mMiddleRectF, mCurrentAngle, 360 - mNoPaintArcAngle);
        mPath.lineTo(x, y);
        canvas.drawPath(mPath, mPathPaint);

        mOutPathPaint.setAlpha(mBeginAlpha);
        canvas.drawArc(mOutPathRectF, 90 + mNoPaintArcAngle / 2, 360 - mNoPaintArcAngle, false, mOutPathPaint);

        mPath.reset();
        mInnerPath.reset();
    }

    public void startAnim() {
        ValueAnimator pathAnim = ValueAnimator.ofFloat(mStartAngle, mStartAngle + 180 + mNoPaintArcAngle);
        pathAnim.setDuration(600);
        pathAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        pathAnim.start();
        ValueAnimator circleAnim = ValueAnimator.ofFloat(0, mCircleRadius);
        circleAnim.setDuration(600);
        circleAnim.setInterpolator(new AnticipateOvershootInterpolator());
        circleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentCircleRadius = (float) animation.getAnimatedValue();
            }
        });
        circleAnim.start();
        ValueAnimator alphaAnim = ValueAnimator.ofInt(255 / 2, 0);
        alphaAnim.setDuration(300);
        alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBeginAlpha = (int) animation.getAnimatedValue();
            }
        });
        alphaAnim.start();
    }

}
