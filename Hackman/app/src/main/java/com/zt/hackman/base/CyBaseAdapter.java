package com.zt.hackman.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Eugene on 2016/4/28.
 */
public class CyBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> dataList;
    protected LayoutInflater inflater;
    protected ImageLoader loader;
    protected DisplayImageOptions options;

    public CyBaseAdapter(Context context){
        this.context
                = context;
        inflater = LayoutInflater.from(context);
        loader = ImageLoader.getInstance();
        options = BaseApp.options;
    }

    public void refreshData(List<T> list){
        this.dataList = list;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(dataList==null) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return convertView;
    }


}
