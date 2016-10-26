package com.zt.hackman.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.CyBaseAdapter;
import com.zt.hackman.response.MyOrderResponse;
import com.zt.hackman.response.OrderResponse;
import com.zt.hackman.utils.StringUtils;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MyOrderAdapter extends CyBaseAdapter<OrderResponse> {
    public MyOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            convertView = inflater.inflate(R.layout.adapter_myorder_claim,parent,false);
            holder.statusView = (TextView) convertView.findViewById(R.id.adapter_order_status_text);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        OrderResponse data = dataList.get(position);
        if(data.orderStatus!=null&&!StringUtils.isEmpty(data.orderStatus)) {
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
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView statusView ;
    }
}
