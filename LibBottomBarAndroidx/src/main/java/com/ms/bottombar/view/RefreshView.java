package com.ms.bottombar.view;

import android.animation.ValueAnimator;
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

import com.ms.bottombar.R;

public class RefreshView extends View {

    private float mWidth;
    private float mHeight;

    /*
     * 圆点
     */
    private int mCircleColor;       // 颜色
    private Paint mCirclePaint;

    private Paint mPathPaint;
    private int mPathColor;         // 颜色
    private int mPathStroke;        // 大小
    private Path mPath;

    private Path mMiddlePath;
    private RectF mInnerRectF;
    private RectF mMiddleRectF;
    private RectF mOutPathRectF;
    private Paint mOutPathPaint;

    private int mStartAngle;
    private float mNoPaintArcAngle;
    private float mValue;

    private float[] mPos;
    private PathMeasure mPathMeasure;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RefreshView);
        mCircleColor = array.getColor(R.styleable.RefreshView_rv_circle_color, Color.RED);
        mPathColor = array.getColor(R.styleable.RefreshView_rv_arc_color, Color.RED);
        mPathStroke = array.getDimensionPixelSize(R.styleable.RefreshView_rv_arc_stroke, 3);
        mStartAngle = array.getDimensionPixelSize(R.styleable.RefreshView_rv_start_angle, -180);
        mNoPaintArcAngle = array.getDimensionPixelSize(R.styleable.RefreshView_rv_no_paint_angle, 30);
        array.recycle();

        init();
    }

    private void init() {
        mInnerRectF = new RectF();
        mOutPathRectF = new RectF();
        mMiddleRectF = new RectF();

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
        mOutPathPaint.setAlpha(255 / 3);

        mPathMeasure = new PathMeasure();
        mMiddlePath = new Path();
        mPos = new float[2];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mOutPathRectF.set(mPathStroke, mPathStroke, mWidth - mPathStroke, mHeight - mPathStroke);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mWidth / 2, mHeight / 2, mValue * mWidth / 7, mCirclePaint);

        mOutPathPaint.setAlpha((int) (255 / 3 - 255 / 3 * mValue));
        canvas.drawArc(mOutPathRectF, 90 + mNoPaintArcAngle / 2, 360 - mNoPaintArcAngle, false, mOutPathPaint);

        mMiddleRectF.set(mWidth / 4 - mValue * mWidth / 4 + mPathStroke + mWidth / 9,
                mHeight / 4 - mValue * mHeight / 4 + mPathStroke + mWidth / 9,
                mWidth - mWidth / 4 + mValue * mWidth / 4 - mPathStroke - mWidth / 9,
                mHeight - mHeight / 4 + mValue * mHeight / 4 - mPathStroke - mWidth / 9);
        mMiddlePath.arcTo(mMiddleRectF,
                mStartAngle - mStartAngle * mValue + mValue * 30 - 10,
                360 - mNoPaintArcAngle - 10);

        mPathMeasure.setPath(mMiddlePath, false);
        mPathMeasure.getPosTan(1 * mPathMeasure.getLength(), mPos, null);
        float x = mPos[0];
        float y = mPos[1];

        mInnerRectF.set(mWidth / 4 - mValue * mWidth / 4 + mPathStroke,
                mHeight / 4 - mValue * mHeight / 4 + mPathStroke,
                mWidth - mWidth / 4 + mValue * mWidth / 4 - mPathStroke,
                mHeight - mHeight / 4 + mValue * mHeight / 4 - mPathStroke);
        mPath.addArc(mInnerRectF,
                mStartAngle - mStartAngle * mValue + mValue * 30,
                360 - mNoPaintArcAngle);
        mPath.lineTo(x, y);
        canvas.drawPath(mPath, mPathPaint);

        mPath.reset();
        mMiddlePath.reset();
    }

    public void startAnim() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
        anim.setDuration(350);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

}
