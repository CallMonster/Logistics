package com.zt.hackman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zt.hackman.R;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.response.HomeResponse;

/**
 * Created by Administrator on 2016/9/14.
 */
public class MovementAdapter extends CyBaseAdapter<HomeResponse.News> {
    private View view;
    public MovementAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_movement,parent,false);
        if(convertView == null){

        }
        return convertView;
    }

    class ViewHolder{

    }
}
