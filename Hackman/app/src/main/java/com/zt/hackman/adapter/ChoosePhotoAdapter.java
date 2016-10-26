package com.zt.hackman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzy.imagepicker.bean.ImageItem;
import com.zt.hackman.R;
import com.zt.hackman.base.BaseApp;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.event.ChooseImageEvent;

import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ChoosePhotoAdapter extends CyBaseAdapter<ImageItem> {
    public ChoosePhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View view = null;

        if(convertView==null) {
            if(position==dataList.size()-1){
                convertView = inflater.inflate(R.layout.adapter_image_choose_container,parent,false);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new ChooseImageEvent(ChooseImageEvent.TYPE_CHOOSE_IMAGE));
                    }
                });
            }else{
                convertView = inflater.inflate(R.layout.adapter_image_choose_img,parent,false);
                holder.img = (ImageView)convertView.findViewById(R.id.photo_container);
                String path = dataList.get(position).path;
                loader.displayImage("file://"+path,holder.img, BaseApp.options);
            }

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        view = convertView;
        return view;
    }

    class ViewHolder{
        ImageView img;
    }
}
