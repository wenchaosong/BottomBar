package com.simple;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bottom.item.BaseTabItem;

/**
 * Created by songwenchao
 * on 2017/12/29 0029.
 * <p>
 * 类名
 * 需要 --
 * 可以 --
 */
public class BottomItemTab extends BaseTabItem {

    private ImageView mIcon;
    private final TextView mTitle;
    private final ImageView mMessages;

    private int mDefaultDrawable;
    private int mCheckedDrawable;

    private int mDefaultTextColor = 0x56000000;
    private int mCheckedTextColor = 0x56000000;
    private boolean isCheck = false;

    public BottomItemTab(Context context) {
        this(context, null);
    }

    public BottomItemTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomItemTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_bottom_tab, this, true);

        mIcon = (ImageView) findViewById(R.id.view_bottom_tab_image);
        mTitle = (TextView) findViewById(R.id.view_bottom_tab_title);
        mMessages = (ImageView) findViewById(R.id.view_bottom_tab_msg);
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

    public void setData(@DrawableRes int drawableRes, @DrawableRes int checkedDrawableRes, String title) {
        if (isCheck) {
            mIcon.setImageResource(checkedDrawableRes);
        } else {
            mIcon.setImageResource(drawableRes);
        }
        mTitle.setText(title);
    }

    @Override
    public void setChecked(boolean checked) {
        isCheck = checked;
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

    }

    @Override
    public void setHasMessage(boolean hasMessage) {
        if (hasMessage)
            mMessages.setVisibility(VISIBLE);
        else
            mMessages.setVisibility(GONE);
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
