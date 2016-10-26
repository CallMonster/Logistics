package com.zt.hackman.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.OrderDetailClaimActivity;
import com.zt.hackman.adapter.AbsRVAdapter;
import com.zt.hackman.adapter.HistoryOrderAdapter;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.MyOrderResponse;
import com.zt.hackman.response.OrderResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.SystemUtils;
import com.zt.hackman.widget.SimpleFooter;
import com.zt.hackman.widget.SimpleHeader;
import com.zt.hackman.widget.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class HistoryOrderModel {
    ZrcListView listView;
    public RelativeLayout navLaout;
    ProgressActivity progressActivity;
    HistoryOrderAdapter adapter;
    private BaseActivity ac;
    List<OrderResponse> datas;
    private int isFresh;  //1为是刷新,0为不是刷新
    private int current =1;

    public void findViewByIds(BaseActivity activity){
        this.ac =activity;
        listView = (ZrcListView) activity.findViewById(R.id.loadMoreView);
        navLaout = (RelativeLayout)activity.findViewById(R.id.history_order_nav);
        navLaout.setBackgroundResource(R.color.colorWhite);
        progressActivity = (ProgressActivity)activity.findViewById(R.id.history_order_list_progressActivity);
    }

    StringCallback listCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            progressActivity.showError("网络请求异常", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressActivity.showLoading();
                    initData();
                }
            });
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            String content = null;
            try {
                JSONObject jsonObject  = new JSONObject( res.data);
                content = jsonObject.getString("orderList");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            List<OrderResponse> orders = (List<OrderResponse>) GsonUtils.jsonToList(content
                     ,new TypeToken<List<OrderResponse>>(){}.getType());
            if(isFresh==1){
                datas = orders;
                listView.setRefreshSuccess("加载成功"); // 通知加载成功
                listView.startLoadMore();
            }else{
                if(orders!=null) {
                    datas.addAll(orders);
                    listView.setLoadMoreSuccess();
                }else{
                    listView.stopLoadMore();
                }
            }
            if(datas.size()<Constant.order_pageSize){
                listView.stopLoadMore();
            }
            if(res.code>0){
                if(adapter==null){
                    adapter = new HistoryOrderAdapter(ac);
                    listView.setAdapter(adapter);
                }
                adapter.refreshData(datas);
                progressActivity.showContent();
            }else{
                if(res.code==-10){
                    progressActivity.showError(res.msg,
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                LoginUtils.toLogin(ac);
                            }
                        });
                }else{
                    progressActivity.showError(res.msg,
                            new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    progressActivity.showLoading();
                                    initData();
                                }
                            });
                }
            }

        }
    };

    public void init(){
        isFresh = 1;
        current = 1;
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SystemUtils.initListView(ac,listView);

        // 下拉刷新事件回调（可选）
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                OrderResponse data= datas.get(position);
                Intent intent = new Intent(ac, OrderDetailClaimActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTNENTS.INTENT_ORDER_ID, data.id);
                bundle.putInt(Constant.INTNENTS.INTENT_ORDER_STATUS,1 );
            }
        });
        initData();
    }

    private void refresh(){
        current = 1;
        isFresh = 1;
        initData();
    }

    private void loadMore(){
        current++;
        isFresh = 0;
        initData();
    }

    private void initData(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","historyOfOrder");
            String sid = PreferencesUtils.getSID(ac);
            if(sid==null){
                LoginUtils.showDialog(ac,"提示登录","你尚未登录,请先登录");
            }
            jsonObject.put("sid", sid);
            //jsonObject.put("status","1");
            jsonObject.put("page",""+current);
            jsonObject.put("perPage",""+Constant.order_pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),listCallBack, Constant.TEST_HOST);

    }


}
