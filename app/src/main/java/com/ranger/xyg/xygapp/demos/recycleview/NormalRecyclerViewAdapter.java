package com.ranger.xyg.xygapp.demos.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranger.xyg.xygapp.R;
import com.ranger.xyg.xygapp.ui.listener.OnRecyclerViewItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xyg on 2017/4/7.
 */
public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder> implements View.OnClickListener {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private String[] mTitles;
    private OnRecyclerViewItemClickListener<String> mItemClick;

    public NormalRecyclerViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_text, parent, false);
        view.setOnClickListener(this);
        return new NormalTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.mTitleTv.setText(mTitles[position]);
        holder.itemView.setTag(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    public void setData(String[] stringArray) {
        mTitles = stringArray;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<String> itemClickListener) {
        mItemClick = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (mItemClick != null) {
            String data = (String) v.getTag();
            mItemClick.onItemClick(v, data);
        }
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_name)
        TextView mTitleTv;

        public NormalTextViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
