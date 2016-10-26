package com.zt.hackman.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecycleView 的Adapter
 * Created by zhangan on 2016/1/27.
 */
public abstract class AbsRVAdapter<T> extends RecyclerView.Adapter<AbsRVAdapter.ViewHolder> {

    private List<T> data;
    private LayoutInflater inflater;
    private final int layoutId;
    private boolean isScrolling;
    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    /**
     * 点击事件的接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    /**
     * 长按事件的接口
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
    public void setOnItemLongListener(OnItemLongClickListener listener) {
        this.longListener = listener;
    }

    public AbsRVAdapter(RecyclerView view, List<T> datas, int itemLayoutId) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.inflater = LayoutInflater.from(view.getContext());
        this.data = datas;
        layoutId = itemLayoutId;
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
                if (!isScrolling) {
                    notifyDataSetChanged();
                }
            }
        });
    }

    public void refreshData( List<T> datas){
        this.data = datas;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(inflater.inflate(layoutId, parent, false));
        return holder;
    }

    /**
     * 绑定数据
     *
     * @param holder   ViewHolder
     * @param position 位置
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        showData(holder, data.get(position),position);
        if (listener != null) {
            holder.itemView.setOnClickListener(getOnClickListener(position));
        }
        if (longListener != null) {
            holder.itemView.setOnLongClickListener(getOnLongClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 抽象方法
     * @param vHolder ViewHolder
     * @param data    数据
     * @param position 位置
     */
    public abstract void showData(ViewHolder vHolder, T data,int position);

    /**
     * 点击事件
     *
     * @param position 位置
     * @return OnClickListener
     */
    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(v, position);
                }
            }
        };
    }

    /**
     * 长按事件
     *
     * @param position 位置
     * @return OnLongClickListener
     */
    public View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longListener != null && v != null) {
                    longListener.onItemLongClick(v, position);
                }
                return false;
            }
        };
    }

    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> cacheViews;
        private View itemView;// item布局对象

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cacheViews = new SparseArray<>();
        }

        public View getView(int id) {
            View view = cacheViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                if (view != null) {
                    // 将已经查找到的控件放到控件缓存中去，以被直接获取，不需要再次调用findViewById中查找
                    cacheViews.put(id, view);
                }
            }
            return view;
        }
    }
}