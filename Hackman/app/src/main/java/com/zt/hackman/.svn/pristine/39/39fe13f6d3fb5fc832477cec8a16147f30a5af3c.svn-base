package com.zt.hackman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.response.HomeResponse;

/**
 * Created by Administrator on 2016/9/14.
 */
public class NewsAdapter extends CyBaseAdapter<HomeResponse.LatestInfo> {
    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if(convertView==null){
            convertView = inflater.inflate(R.layout.adapter_news,parent,false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.adapter_latest_img);
            holder.titleView = (TextView)convertView.findViewById(R.id.adapter_latest_title);
            holder.contentView = (TextView)convertView.findViewById(R.id.adapter_latest_content);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeResponse.LatestInfo latestInfo = dataList.get(position);
        if(latestInfo!=null){
            String content =latestInfo.content;
            holder.titleView.setText(latestInfo.title);
            if(content.length()>30){
                content = content.substring(0,30)+"...";
            }
            holder.contentView.setText(content);
            loader.displayImage(latestInfo.smallImg,holder.imageView,options);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView titleView,contentView;
    }
}
