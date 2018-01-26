package com.duoyi.drawguess.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView适配器
 *
 * Created by ymlion on 16/6/28.
 */

public abstract class RvMultiBaseAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mDatas;
    protected int[] mLayoutRes;
    private OnItemClickListener mListener;

    public RvMultiBaseAdapter(List<T> list, int layoutRes) {
        mDatas = list;
        mLayoutRes = new int[1];
        mLayoutRes[0] = layoutRes;
    }

    public RvMultiBaseAdapter(List<T> list, int... layoutRes) {
        mDatas = list;
        mLayoutRes = layoutRes;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(parent, mLayoutRes[viewType], viewType);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (mListener != null) {
            ((ViewHolder) holder).getConvertView()
                    .setOnClickListener(view -> mListener.onItemClick(view, position,
                            getItemViewType(position)));
        }

        onBind(viewHolder, mDatas.get(position), getItemViewType(position));
    }

    public abstract void onBind(ViewHolder holder, T model, int viewType);

    @Override public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * data list
     *
     * @return the dataList
     */

    protected List<T> getDataList() {
        return mDatas;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setmDatas(List<T> dataList) {
        if (dataList == null) {
            return;
        }
        this.mDatas = dataList;
        notifyDataSetChanged();
    }

    /**
     * @param dataList the data to set
     */
    public void setDatas(T[] dataList) {
        if (dataList == null) {
            return;
        }
        mDatas = new ArrayList<>(Arrays.asList(dataList));
        notifyDataSetChanged();
    }

    /**
     * Interface definition for a callback to be invoked when an item in this RecyclerView has been
     * clicked.
     */
    public interface OnItemClickListener {
        /**
         * Callback method to be invoked when an item in this RecyclerView has been clicked.
         *
         * @param view The view within the RecyclerView that was clicked (this will be a view
         * provided by the adapter)
         * @param position The position of the view in the adapter
         * @param viewType The view type of the view in the adapter.
         */
        void onItemClick(View view, int position, int viewType);
    }
}
