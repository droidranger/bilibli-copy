package com.ranger.xyg.xygapp.ui.view;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ranger.xyg.xygapp.utils.LogUtils;

/**
 * Created by xyg on 2017/3/18.
 */

public class XygDraweeView extends SimpleDraweeView {

    private static final java.lang.String LOG_TAG = XygDraweeView.class.getSimpleName();

    public XygDraweeView(Context context) {
        super(context);
    }

    public XygDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XygDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageURL(String url) {
        if (TextUtils.isEmpty(url)) {
            LogUtils.v(LOG_TAG, "URL is empty.");
            return;
        }
        Uri uri = Uri.parse(url);
        this.setImageURI(uri);
    }

    public void setImageResId(int resId) {
        if (resId <= 0) {
            return;
        }
        setImageURI(Uri.parse("res:///" + resId));
    }
}
