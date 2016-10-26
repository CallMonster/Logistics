package com.zt.hackman.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.activity.OrderDetailClaimActivity;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.response.OrderResponse;
import com.zt.hackman.utils.StringUtils;

/**
 * Created by Administrator on 2016/10/20.
 */
public class HistoryOrderAdapter extends CyBaseAdapter<OrderResponse> {
    public HistoryOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderResponse data = dataList.get(position);
        ViewHolder holder = new ViewHolder();
        if(convertView==null){
            convertView = inflater.inflate(R.layout.adapter_myorder_claim,parent,false);
            holder.rootView = (LinearLayout)convertView.findViewById(R.id.order_linear_root);
            holder.startView = (TextView) convertView.findViewById(R.id.adapter_start_text);
            holder.endView = (TextView)convertView.findViewById(R.id.adapter_end_text);
            holder.timeView = (TextView)convertView.findViewById(R.id.adapter_claim_time);
            holder.statusView = (TextView)convertView.findViewById(R.id.adapter_order_status_text) ;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(data!=null) {
            if (data.orderStatus != null && !StringUtils.isEmpty(data.orderStatus)) {
                int orderStatus = Integer.parseInt(data.orderStatus);
                switch (orderStatus) {
                    case 0:
                        holder.statusView.setText("待结算");
                        break;
                    case 3:
                        holder.statusView.setText("待取货");
                        break;
                    case 4:
                        holder.statusView.setText("待送达");
                        break;
                    default:
                        holder.statusView.setText("已完成");
                        break;
                }
            }
            if(data.createTime!=null) {
                holder.timeView.setText("取货时间：" + data.createTime);
            }
            holder.startView.setText(data.deliverAddr);
            holder.endView.setText(data.receiptAddr);
        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout rootView;
        TextView startView;
        TextView endView;
        TextView timeView;
        TextView statusView;
    }
}
