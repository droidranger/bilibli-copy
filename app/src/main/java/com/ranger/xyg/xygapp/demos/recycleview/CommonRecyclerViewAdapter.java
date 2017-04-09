package com.ranger.xyg.xygapp.demos.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.listener.OnRecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by xyg on 2017/4/7.
 */
public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewAdapter.CommonViewHolder> implements View.OnClickListener {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<T> mData;
    private OnRecyclerViewItemClickListener<T> mItemClick;

    public CommonRecyclerViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(getRecyclerItemRes(), parent, false);
        view.setOnClickListener(this);
        return createViewHolder(view);
    }

    protected abstract CommonViewHolder createViewHolder(View view);

    protected abstract int getRecyclerItemRes();

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<T> data) {
        mData = data;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<T> itemClickListener) {
        mItemClick = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (mItemClick != null) {
            T data = (T) v.getTag();
            mItemClick.onItemClick(v, data);
        }
    }

    public static class CommonViewHolder extends RecyclerView.ViewHolder {

        public CommonViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
