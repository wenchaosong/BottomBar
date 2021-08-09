package com.ms.bottombar.item;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ms.bottombar.R;
import com.ms.bottombar.internal.RoundMessageView;
import com.ms.bottombar.internal.Utils;

/**
 * 材料设计风格项
 */
public class MaterialItemView extends BaseTabItem {

    private final RoundMessageView mMessages;
    private final TextView mLabel;
    private final ImageView mIcon;

    private Drawable mDefaultDrawable;
    private Drawable mCheckedDrawable;

    private int mDefaultColor;
    private int mCheckedColor;

    private final float mTranslation;
    private final float mTranslationHideTitle;

    private final int mTopMargin;
    private final int mTopMarginHideTitle;

    private boolean mHideTitle;
    private boolean mChecked;

    private ValueAnimator mAnimator;
    private float mAnimatorValue = 1f;

    private boolean mIsMeasured = false;

    public MaterialItemView(@NonNull Context context) {
        this(context, null);
    }

    public MaterialItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final float scale = context.getResources().getDisplayMetrics().density;

        mTranslation = scale * 2;
        mTranslationHideTitle = scale * 10;
        mTopMargin = (int) (scale * 8);
        mTopMarginHideTitle = (int) (scale * 16);

        LayoutInflater.from(context).inflate(R.layout.item_material, this, true);

        mIcon = (ImageView) findViewById(R.id.icon);
        mLabel = (TextView) findViewById(R.id.label);
        mMessages = (RoundMessageView) findViewById(R.id.messages);
    }

    public void initialization(String title, Drawable drawable, Drawable checkedDrawable, int color, int checkedColor) {

        mDefaultColor = color;
        mCheckedColor = checkedColor;

        mDefaultDrawable = Utils.tint(drawable, mDefaultColor);
        mCheckedDrawable = Utils.tint(checkedDrawable, mCheckedColor);

        mLabel.setText(title);
        mLabel.setTextColor(color);

        mIcon.setImageDrawable(mDefaultDrawable);

        mAnimator = ValueAnimator.ofFloat(1f);
        mAnimator.setDuration(115L);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                if (mHideTitle) {
                    mIcon.setTranslationY(-mTranslationHideTitle * mAnimatorValue);
                } else {
                    mIcon.setTranslationY(-mTranslation * mAnimatorValue);
                }
                mLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f + mAnimatorValue * 2f);
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mIsMeasured = true;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked == checked) {
            return;
        }

        mChecked = checked;

        if (mHideTitle) {
            mLabel.setVisibility(mChecked ? View.VISIBLE : View.INVISIBLE);
        }

        if (mIsMeasured) {
            // 切换动画
            if (mChecked) {
                mAnimator.start();
            } else {
                mAnimator.reverse();
            }
        } else if (mChecked) { // 布局还未测量时选中，直接转换到选中的最终状态
            if (mHideTitle) {
                mIcon.setTranslationY(-mTranslationHideTitle);
            } else {
                mIcon.setTranslationY(-mTranslation);
            }
            mLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        } else { // 布局还未测量并且未选中，保持未选中状态
            mIcon.setTranslationY(0);
            mLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
        }

        // 切换颜色
        if (mChecked) {
            mIcon.setImageDrawable(mCheckedDrawable);
            mLabel.setTextColor(mCheckedColor);
        } else {
            mIcon.setImageDrawable(mDefaultDrawable);
            mLabel.setTextColor(mDefaultColor);
        }
    }

    @Override
    public void setMessageNumber(int number) {
        mMessages.setVisibility(View.VISIBLE);
        mMessages.setMessageNumber(number);
    }

    @Override
    public void setHasMessage(boolean hasMessage) {
        mMessages.setVisibility(View.VISIBLE);
        mMessages.setHasMessage(hasMessage);
    }

    @Override
    public String getTitle() {
        return mLabel.getText().toString();
    }

    /**
     * 获取动画运行值[0,1]
     */
    public float getAnimValue() {
        return mAnimatorValue;
    }

    /**
     * 设置是否隐藏文字
     */
    public void setHideTitle(boolean hideTitle) {
        mHideTitle = hideTitle;

        FrameLayout.LayoutParams iconParams = (FrameLayout.LayoutParams) mIcon.getLayoutParams();

        if (mHideTitle) {
            iconParams.topMargin = mTopMarginHideTitle;
        } else {
            iconParams.topMargin = mTopMargin;
        }

        mLabel.setVisibility(mChecked ? View.VISIBLE : View.INVISIBLE);

        mIcon.setLayoutParams(iconParams);
    }

    /**
     * 设置消息圆形的颜色
     */
    public void setMessageBackgroundColor(@ColorInt int color) {
        mMessages.tintMessageBackground(color);
    }

    /**
     * 设置消息数据的颜色
     */
    public void setMessageNumberColor(@ColorInt int color) {
        mMessages.setMessageNumberColor(color);
    }
}
