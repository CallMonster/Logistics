package com.qxsx.chaersi.driverclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.adapter.OrderTask_SettleAdapter;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.BaseFragment;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.Order_StateReq;
import com.qxsx.chaersi.driverclient.entry.result.Order_StateResult;
import com.qxsx.chaersi.driverclient.order.OrderDetailActivity;
import com.qxsx.chaersi.driverclient.widget.OnBottomScrollListener;
import com.qxsx.chaersi.driverclient.widget.impl.OnRecyclerViewListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.qxsx.chaersi.driverclient.entry.result.Order_StateResult.DataBean.OrderListBean;


/**
 * Created by Chaersi on 16/10/24.
 */
public class Order_SettleFragment  extends BaseFragment {
    private String TAG="Order_SettleFragment";
    private int PULL_STATE=0;//1：为加载，0:刷新

    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.settleRecyclerView) RecyclerView settleRecyclerView;
    private List<OrderListBean> orderlist;
    private OrderTask_SettleAdapter adapter;
    private int page=1;
    private boolean isLoadMord=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_settle, null);
        ButterKnife.bind(this, view);

        orderlist=new ArrayList<>();
        settleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        settleRecyclerView.setLayoutManager(layoutManager);
        adapter = new OrderTask_SettleAdapter(getActivity(), orderlist,5);
        settleRecyclerView.setAdapter(adapter);
        adapter.addItemClickListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent=new Intent(getActivity(), OrderDetailActivity.class);
                Bundle mBundle=new Bundle();
                mBundle.putString("state","5");
                mBundle.putString("orderid",orderlist.get(position).getId());
                mBundle.putString("ordertime",orderlist.get(position).getCallTime());
                intent.putExtras(mBundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);

            }

            @Override
            public void onItemViewClickListener(int position) {

            }
        });
        BaseApplication.getInstance().addRequestQueue(settleRequest);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressDialog("正在读取最新数据...");
                refreshLayout.setRefreshing(true);
                PULL_STATE=0;
                page=1;
                BaseApplication.getInstance().addRequestQueue(settleRequest);
            }
        });

        settleRecyclerView.addOnScrollListener(new OnBottomScrollListener(){
            @Override
            public void onBottom() {
                super.onBottom();
                if(isLoadMord){
                    showProgressDialog("加载更多...");
                    PULL_STATE=1;
                    page++;
                    BaseApplication.getInstance().addRequestQueue(settleRequest);
                }else{
                    showTips("没有更多的任务了");
                }
            }
        });
        return view;
    }

    @Override
    public void onClickListener(View v) {

    }

    StringRequest settleRequest=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,"succ:"+response);
                    Order_StateResult result= BaseApplication.gson.fromJson(response,Order_StateResult.class);
                    if("1".equals(result.getCode())){
                        List<Order_StateResult.DataBean.OrderListBean> templist=result.getData().getOrderList();
                        if(PULL_STATE==0){
                            if(templist.size()>0){
                                orderlist.clear();
                                orderlist.addAll(templist);
                                adapter.notifyDataSetChanged();
                            }else{
                                orderlist.clear();
                                adapter.notifyDataSetChanged();
                                showTips("暂时没有待取货的任务");
                            }
                        }else{
                            if(templist.size()>0){
                                orderlist.addAll(templist);
                                adapter.notifyDataSetChanged();
                            }else{
                                isLoadMord=false;
                                showTips("没有更多的任务了");
                            }
                        }
                    }else{
                        showTips(result.getMsg());
                    }
                    refreshLayout.setRefreshing(false);
                    hideProgressDialog();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showTips("网络链接不畅，请稍后再试");
                    refreshLayout.setRefreshing(false);
                    hideProgressDialog();
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Order_StateReq orderState=new Order_StateReq();
            orderState.setCommand("orderList");
            orderState.setSid(BaseApplication.getInstance().loginsid);
            orderState.setOrderStatus("5");
            orderState.setLimit("5");
            orderState.setPage(page+"");

            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(orderState));

            return params;
        }
    };
}