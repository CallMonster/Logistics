package com.zt.hackman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.response.DepositResponse;

/**
 * Created by Administrator on 2016/9/26.
 */
public class BankCardAdapter extends CyBaseAdapter<DepositResponse.BankCard> {

    public BankCardAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.adapter_deposit_layout, parent, false);
            holder.img = (ImageView)convertView.findViewById(R.id.label14);
            holder.textView = (TextView)convertView.findViewById(R.id.deposit_content_text);
            if(position == dataList.size()-1){
                //holder.img.setBackgroundResource(R.mipmap.add_photo);
                holder.textView.setTextColor(context.getResources().getColor(R.color.tab_seleted));
                holder.textView.setText("添加银行卡");
            }
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView textView;
    }
}
