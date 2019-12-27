package com.test.item;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.R;
import com.ms.bottombar.internal.RoundMessageView;
import com.ms.bottombar.item.BaseTabItem;
import com.ms.bottombar.view.RefreshView;

public class NormalItemView extends BaseTabItem {

    private ImageView mIcon;
    private final TextView mTitle;
    private final RoundMessageView mMessages;
    private RefreshView mRefreshView;

    private int mDefaultDrawable;
    private int mCheckedDrawable;
    private boolean mShowRefresh;
    private int mDefaultTextColor = 0x56000000;
    private int mCheckedTextColor = 0x56000000;

    public NormalItemView(Context context) {
        this(context, null);
    }

    public NormalItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.item_normal, this, true);

        mIcon = findViewById(R.id.icon);
        mRefreshView = findViewById(R.id.refresh);
        mTitle = findViewById(R.id.title);
        mMessages = findViewById(R.id.messages);
    }

    /**
     * 方便初始化的方法
     *
     * @param drawableRes        默认状态的图标
     * @param checkedDrawableRes 选中状态的图标
     * @param title              标题
     */
    public void initialize(@DrawableRes int drawableRes, @DrawableRes int checkedDrawableRes, String title, boolean showRefresh) {
        mDefaultDrawable = drawableRes;
        mCheckedDrawable = checkedDrawableRes;
        mShowRefresh = showRefresh;
        mTitle.setText(title);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            if (mShowRefresh) {
                mRefreshView.startAnim();
                mRefreshView.setVisibility(VISIBLE);
                mIcon.setVisibility(GONE);
            } else {
                mIcon.setImageResource(mCheckedDrawable);
                mIcon.setVisibility(VISIBLE);
            }
            mTitle.setTextColor(mCheckedTextColor);
        } else {
            mRefreshView.setVisibility(GONE);
            mIcon.setImageResource(mDefaultDrawable);
            mIcon.setVisibility(VISIBLE);
            mTitle.setTextColor(mDefaultTextColor);
        }
    }

    @Override
    public void onRepeat() {
        if (mShowRefresh) {
            if (mRefreshView != null)
                mRefreshView.startAnim();
        }
    }

    @Override
    public void setMessageNumber(int number) {
        mMessages.setMessageNumber(number);
    }

    @Override
    public void setHasMessage(boolean hasMessage) {
        mMessages.setHasMessage(hasMessage);
    }

    @Override
    public String getTitle() {
        return mTitle.getText().toString();
    }

    public void setTextDefaultColor(@ColorInt int color) {
        mDefaultTextColor = color;
    }

    public void setTextCheckedColor(@ColorInt int color) {
        mCheckedTextColor = color;
    }
}
