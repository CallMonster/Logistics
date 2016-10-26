package com.zt.hackman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseApp;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.response.FriendsResponse;

/**
 * Created by Administrator on 2016/10/14.
 */
public class FriendsAdapter extends CyBaseAdapter<FriendsResponse> {
    public FriendsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_friends_layout,parent,false);
            holder.iconView = (ImageView) convertView.findViewById(R.id.adapter_friends_img);
            holder.nameView = (TextView)convertView.findViewById(R.id.adapter_friends_name);
            holder.phoneView = (TextView)convertView.findViewById(R.id.adapter_friends_phone);
            holder.inventBtn = (TextView)convertView.findViewById(R.id.invent_friends_btn);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if(dataList!=null&&dataList.size()>0){
            FriendsResponse friends =dataList.get(position);
            if(friends!=null){
                holder.nameView.setText(friends.name);
                holder.phoneView.setText(friends.phone);
                loader.displayImage("file://"+friends.image,holder.iconView, BaseApp.circleOptions);

            }
        }

        return super.getView(position, convertView, parent);
    }
    class ViewHolder{
        ImageView iconView;
        TextView nameView,phoneView,inventBtn;
    }
}
