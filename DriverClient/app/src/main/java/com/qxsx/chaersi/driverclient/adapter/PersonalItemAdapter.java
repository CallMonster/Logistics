package com.qxsx.chaersi.driverclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qxsx.chaersi.driverclient.R;

/**
 * Created by Chaersi on 16/10/25.
 */
public class PersonalItemAdapter extends BaseAdapter {

    private int logoid[] = {
            R.mipmap.my_icon, R.mipmap.my_idea_icon,
            R.mipmap.my_tel, R.mipmap.my_about_icon
    };

    private String name[] = {"邀请好友", "建议反馈","联系我们","关于"};

    private Context context;
    public PersonalItemAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        return name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_personal, null,false);
            holder=new ViewHolder();
            holder.itemIcon=(ImageView) convertView.findViewById(R.id.itemIcon);
            holder.itemName=(TextView) convertView.findViewById(R.id.itemName);
            holder.line_grey=convertView.findViewById(R.id.line_grey);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        if(position==logoid.length-1){
            holder.line_grey.setVisibility(View.INVISIBLE);
        }else{
            holder.line_grey.setVisibility(View.VISIBLE);
        }

        holder.itemIcon.setImageResource(logoid[position]);
        holder.itemName.setText(name[position]);

        return convertView;
    }

    private class ViewHolder{
        ImageView itemIcon;
        TextView itemName;
        View line_grey;
    }

}
