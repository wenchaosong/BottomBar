package com.test.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.ms.bottombar.internal.RoundMessageView;
import com.ms.bottombar.item.BaseTabItem;
import com.test.R;

/**
 * Created by mjj on 2017/6/3
 */
public class SpecialTabRound extends BaseTabItem {

    private ImageView mIcon;
    private final TextView mTitle;
    private final RoundMessageView mMessages;

    private int mDefaultDrawable;
    private int mCheckedDrawable;

    private int mDefaultTextColor = 0x56000000;
    private int mCheckedTextColor = 0x56000000;

    public SpecialTabRound(Context context) {
        this(context, null);
    }

    public SpecialTabRound(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecialTabRound(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.special_tab_round, this, true);

        mIcon = (ImageView) findViewById(R.id.icon);
        mTitle = (TextView) findViewById(R.id.title);
        mMessages = (RoundMessageView) findViewById(R.id.messages);
    }

    /**
     * 方便初始化的方法
     *
     * @param drawableRes        默认状态的图标
     * @param checkedDrawableRes 选中状态的图标
     * @param title              标题
     */
    public void initialize(@DrawableRes int drawableRes, @DrawableRes int checkedDrawableRes, String title) {
        mDefaultDrawable = drawableRes;
        mCheckedDrawable = checkedDrawableRes;
        mTitle.setText(title);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mIcon.setImageResource(mCheckedDrawable);
            mTitle.setTextColor(mCheckedTextColor);
        } else {
            mIcon.setImageResource(mDefaultDrawable);
            mTitle.setTextColor(mDefaultTextColor);
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
