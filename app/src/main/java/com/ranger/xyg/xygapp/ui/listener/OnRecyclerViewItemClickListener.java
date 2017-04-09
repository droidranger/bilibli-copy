package com.ranger.xyg.xygapp.ui.listener;

import android.view.View;

/**
 * Created by xyg on 2017/4/7.
 */

public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View view, T data);
}
