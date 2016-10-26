package com.qxsx.chaersi.driverclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.entry.result.ExceptResult.DataBean.ReasonBean;
import com.qxsx.chaersi.driverclient.widget.impl.OnRecyclerViewListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Chaersi on 16/10/25.
 */
public class OrderProblemAdapter extends BaseAdapter {

    private Context context;
    private List<ReasonBean> reason;
    private HashMap<Integer,Boolean> chooseMap;
    public OrderProblemAdapter(Context context,List<ReasonBean> reason,String reasonId){
        this.context=context;
        this.reason=reason;

        chooseMap=new HashMap<>();
        for(int i=0;i<reason.size();i++){
            if(reasonId.equals(reason.get(i).getReasonId())){
                chooseMap.put(i,true);
            }else{
                chooseMap.put(i,false);
            }
        }
    }

    @Override
    public int getCount() {
        return reason.size();
    }

    @Override
    public Object getItem(int position) {
        return reason.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_order_problem, null,false);
            holder=new ViewHolder();
            holder.errName=(TextView) convertView.findViewById(R.id.errName);
            holder.chooseTag=(ImageView) convertView.findViewById(R.id.chooseFlag);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        holder.errName.setText(reason.get(position).getTitle());

        if(chooseMap.get(position)){
            holder.chooseTag.setVisibility(View.VISIBLE);
        }else{
            holder.chooseTag.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private class ViewHolder{
        TextView errName;
        ImageView chooseTag;
    }

    public static OnRecyclerViewListener listener;
    public void addItemClickListener(OnRecyclerViewListener listener) {
        this.listener = listener;
    }


}
