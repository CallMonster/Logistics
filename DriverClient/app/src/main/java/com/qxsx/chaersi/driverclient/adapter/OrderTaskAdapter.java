package com.qxsx.chaersi.driverclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.entry.result.Order_StateResult.DataBean.OrderListBean;
import com.qxsx.chaersi.driverclient.widget.impl.OnRecyclerViewListener;

import java.util.List;

/**
 * Created by Chaersi on 16/10/24.
 */
public class OrderTaskAdapter extends RecyclerView.Adapter<OrderTaskAdapter.ItemViewHolder> {

    private Context context;
    private List<OrderListBean> orderList;
    private String stateStr;
    public OrderTaskAdapter(Context context,List<OrderListBean> orderList,int state){
        this.context=context;
        this.orderList=orderList;
        if(state==3){
            stateStr="待取货";
        }else if(state==4){
            stateStr="待送达";
        }else{
            stateStr="待结算";
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ordertask, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.position=position;
        OrderListBean item=orderList.get(position);
        holder.stateView.setText(stateStr);
        holder.timeView.setText("取货时间："+item.getCallTime());
        holder.sendAddrView.setText(item.getDeliverAddr());
        holder.receiverAddrView.setText(item.getReceiptAddr());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView stateView;
        public TextView timeView;
        public TextView sendAddrView;
        public TextView receiverAddrView;
        public RelativeLayout parentPanel;
        public int position;
        public ItemViewHolder(View itemView) {
            super(itemView);
            stateView= (TextView) itemView.findViewById(R.id.stateView);
            timeView= (TextView) itemView.findViewById(R.id.timeView);
            sendAddrView= (TextView) itemView.findViewById(R.id.sendAddrView);
            receiverAddrView= (TextView) itemView.findViewById(R.id.receiverAddrView);
            parentPanel= (RelativeLayout) itemView.findViewById(R.id.parentPanel);
            parentPanel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.parentPanel:
                    listener.onItemClickListener(position);
                    break;
            }
        }
    }

    public static OnRecyclerViewListener listener;
    public void addItemClickListener(OnRecyclerViewListener listener) {
        this.listener = listener;
    }

}
