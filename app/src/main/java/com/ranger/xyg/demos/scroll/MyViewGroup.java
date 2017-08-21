package com.ranger.xyg.demos.scroll;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by xyg on 2017/5/26.
 */

public class MyViewGroup extends FrameLayout {

    private Scroller mScroller;

    public MyViewGroup(@NonNull Context context) {
        super(context);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        TextView textView = new TextView(getContext());
        textView.setText("天下无贼");
        textView.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(textView, params);

        mScroller = new Scroller(getContext());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            setX(mScroller.getCurrX());
            setY(mScroller.getCurrY());
            invalidate();
        }
    }

    public void startScroll(int y) {
        mScroller.startScroll((int) getX(), (int) getY(), 0, y, 300);
        invalidate();
    }
}
