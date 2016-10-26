package com.zt.hackman.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class AbsAdapter<T> extends BaseAdapter {

	private Context context;
	private List<T> datas;
	private int layoutId;// item布局资源的标识

	public AbsAdapter(Context context, List<T> datas, int layoutId) {
		super();
		this.context = context;
		this.datas = datas;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	/**
	 * 返回数据源中指定位置的数据
	 * @param position 位置
	 * @return 数据
	 */
	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	/**
	 * 返回数据源中指定位置数据的ID
	 * @param position 位置
	 * @return id
	 */
	@Override
	public long getItemId(int position) {
		// TODO
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
			vHolder = new ViewHolder(convertView);
			convertView.setTag(vHolder);
		} else {
			vHolder = (ViewHolder) convertView.getTag();
		}
		showData(vHolder, getItem(position));// 显示数据
		return convertView;
	}

	public abstract void showData(ViewHolder vHolder, T data);

	public static class ViewHolder {
		private SparseArray<View> cacheViews;
		private View itemView;// item布局对象

		public ViewHolder(View itemVeiw) {
			this.itemView = itemVeiw;
			cacheViews = new SparseArray<View>();
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
