package com.ranger.xyg.music.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.ranger.xyg.music.R;

/**
 * Created by xingyugang on 2017/11/24.
 */

public class HomeLeftMenuLayout extends FrameLayout {

    public HomeLeftMenuLayout(@NonNull Context context) {
        super(context);
        initViews(context);
    }

    public HomeLeftMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public HomeLeftMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_home_left_menu, this);
    }


}
