package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.OrderDetailClaimActivity;
import com.zt.hackman.adapter.AbsRVAdapter;
import com.zt.hackman.adapter.HistoryOrderAdapter;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.OrderResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.SystemUtils;
import com.zt.hackman.widget.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MyOrderModel{
    public ZrcListView listView;
    public HistoryOrderAdapter adapter;
    private ProgressActivity progressActivity;
    private int current = 1;
    private Activity ac;
    private int orderId;

    private List<OrderResponse> orderResponseses ;
    private int isFresh;   //1为是刷新，0为加载更多

    public void findViewByIds(View view,Activity ac){
        this.ac = ac;
        progressActivity = (ProgressActivity)view.findViewById(R.id.myorder_progressActivity);
        listView = (ZrcListView) view.findViewById(R.id.loadMoreView);
    }

    public void init(final Activity context,int orderId){
        isFresh = 1;
        current = 1;
        SystemUtils.initListView(ac,listView);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                OrderResponse data= orderResponseses.get(position);
                Intent intent = new Intent(ac, OrderDetailClaimActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTNENTS.INTENT_ORDER_ID, data.id);
                if("0".equals(data.orderStatus)) {
                    bundle.putInt(Constant.INTNENTS.INTENT_ORDER_STATUS,1 );
                }else{
                    bundle.putInt(Constant.INTNENTS.INTENT_ORDER_STATUS,0);
                }
                intent.putExtras(bundle);
                ac.startActivity(intent);
            }
        });
        // 下拉刷新事件回调（可选）
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
        initData(orderId);
    }

    StringCallback listCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            progressActivity.showError("网络请求异常", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData(orderId);
                }
            });
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            String content = null;
            if(res.code>0){
                try {
                    JSONObject jsonObject = new JSONObject(res.data);
                    content = jsonObject.getString("orderList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<OrderResponse> orderResponses = (List<OrderResponse>) GsonUtils.jsonToList(content,new TypeToken<List<OrderResponse>>(){}.getType());
                if(isFresh==1){
                    orderResponseses = orderResponses;
                    listView.setRefreshSuccess("加载成功"); // 通知加载成功
                    listView.startLoadMore();
                }else{
                    orderResponseses.addAll(orderResponses);
                    listView.setLoadMoreSuccess();
                }
                if(orderResponseses.size()<Constant.order_pageSize){
                    listView.stopLoadMore();
                }
                if(orderResponseses==null||orderResponseses.size()<=0){
                    progressActivity.showEmpty(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData(orderId);
                        }
                    });
                }else {
                    if(adapter==null){
                        adapter = new HistoryOrderAdapter(ac);
                        listView.setAdapter(adapter);
                    }
                    adapter.refreshData(orderResponseses);
                }
                progressActivity.showContent();
            }else{
                if(res.code==-10){
                    progressActivity.showError("会话已过期,点击重新登录",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //initData();
                                    LoginUtils.toLogin(ac);
                                }
                            });


                }else{
                    progressActivity.showError(res.msg,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    current = 1;
                                    isFresh = 1;
                                    initData(orderId);

                                    //LoginUtils.toLogin(ac);
                                }
                            });
                }
            }
        }
    };

    private void initData(int orderId){
        this.orderId = orderId;
        progressActivity.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","orderList");
            String sid = PreferencesUtils.getSID(ac);
            if(sid!=null){
                jsonObject.put("sid", PreferencesUtils.getSID(ac));
            }

            switch (orderId){
                case 0:
                    jsonObject.put("orderStatus","3");
                    break;
                case 1:
                    jsonObject.put("orderStatus","4");
                    break;
                case 2:
                    jsonObject.put("orderStatus","0");
                    break;
            }
            jsonObject.put("page",""+current);
            jsonObject.put("limit",""+ Constant.order_pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),listCallBack,Constant.TEST_HOST);
    }

    private void loadMore() {
        isFresh = 0;
        current++;
        initData(orderId);
    }

    private void refresh() {
        isFresh =1;
        current =1;
        initData(orderId);
    }
}
