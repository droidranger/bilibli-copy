package com.ranger.xyg.music.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ranger.xyg.music.R;

import butterknife.ButterKnife;

/**
 * Created by xingyugang on 2017/11/24.
 */

public class MenuItemLayout extends FrameLayout {

    @butterknife.BindView(R.id.tv_menu_item_name)
    TextView mMenuItemNameTv;
    @butterknife.BindView(R.id.tv_menu_item_tip)
    TextView mMenuItemTipTv;

    public MenuItemLayout(@NonNull Context context) {
        super(context);
        initViews(context, null);
    }

    public MenuItemLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public MenuItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_left_menu_item, this);
        ButterKnife.bind(this);
        if (attrs != null) {
            @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuItemStyle);
            String nameText = typedArray.getString(R.styleable.MenuItemStyle_nameText);
            String tipText = typedArray.getString(R.styleable.MenuItemStyle_tipText);
            mMenuItemNameTv.setText(nameText);
            mMenuItemTipTv.setText(tipText);
        }
    }


}
