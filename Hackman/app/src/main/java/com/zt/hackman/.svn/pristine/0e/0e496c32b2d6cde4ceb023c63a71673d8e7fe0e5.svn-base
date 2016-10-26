package com.zt.hackman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.response.MessageResponse;

/**
 * Created by Administrator on 2016/10/20.
 */
public class MessageAdapter extends CyBaseAdapter<MessageResponse>{
    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_message,parent,false);
            holder. iconView = (ImageView)convertView.findViewById(R.id.adapter_message_icon);
            holder.titleView = (TextView)convertView.findViewById(R.id.adapter_message_title);
            holder.timeView = (TextView)convertView.findViewById(R.id.adapter_message_time);
            holder.contentView = (TextView) convertView.findViewById(R.id.adapter_message_content);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        MessageResponse data = dataList.get(position);
        if(data!=null){
            if("0".equals(data.isRead)) {
                holder.iconView.setBackgroundResource(R.mipmap.ic_message_list);
            }else{
                holder.iconView.setBackgroundResource(R.mipmap.ic_message_list);
            }
            String title=data.title;
            String content = data.content;
            if(title.length()>10){
                title = title.substring(0,10);
            }
            if(content.length()>20){
                content = content.substring (0,20)+"...";
            }
            holder.titleView.setText(title);
            holder.contentView.setText(content);
            holder.timeView.setText(data.issueTime);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iconView ;
        TextView titleView;
        TextView timeView;
        TextView contentView ;
    }
}
