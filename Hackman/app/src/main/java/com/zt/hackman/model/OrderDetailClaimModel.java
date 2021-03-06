package com.zt.hackman.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.MapView;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.HappenQuestionClaimActivity;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.RefreshEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.OrderResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;
import com.zt.hackman.view.AlertLoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class OrderDetailClaimModel implements View.OnClickListener{
    public RelativeLayout orderDetailNav,totalLayout;
    public TextView happenQuestionBtn,completeBtn,arriveTimeText,startAddressText
            ,endAddressText,startPersonPhoneText,endPersonPhoneText,startPersonName,endPersonName
            ,requireText,orderIdText,goodNumText,createTimeText,totalMoney,statusText;
    public LinearLayout bottomLayout;

    public MapView mapView;
    private ProgressActivity order_detail_claim_progressActivity;
    private BaseActivity ac;
    private String orderId;
    private int requestOrderStatus;
    private OrderResponse orderResponse = null;
    AlertLoadingDialog dialog;
    private int orderStatus;

    public void findViewByIds(final BaseActivity activity, String orderId,int orderStatus){
        this.requestOrderStatus = orderStatus;
        this.orderId = orderId;
        if(orderId==null|| StringUtils.isEmpty(orderId)||"0".equals(orderId)){
            order_detail_claim_progressActivity.showError("无效订单，点击关闭",
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });
        }
        this.ac = activity;
        orderDetailNav = (RelativeLayout)activity.findViewById(R.id.order_detail_claim_title);
        happenQuestionBtn = (TextView)activity.findViewById(R.id.claim_happen_question_btn);
        completeBtn = (TextView)activity.findViewById(R.id.claim_confirm_complete_btn);
        orderDetailNav.setBackgroundResource(R.color.colorWhite);

        mapView  = (MapView)activity. findViewById(R.id.orderDetailClaimMap);
        statusText = (TextView)activity.findViewById(R.id.order_detail_status_text);
        totalMoney = (TextView)activity.findViewById(R.id.order_detail_money_text);
        totalLayout = (RelativeLayout) activity.findViewById(R.id.order_detail_total_layout);
        arriveTimeText = (TextView)activity.findViewById(R.id.order_detail_claim_arrive_time);
        startAddressText = (TextView)activity.findViewById(R.id.detail_claim_start_address_text);
        endAddressText = (TextView)activity.findViewById(R.id.detail_claim_end_address_text);
        startPersonPhoneText = (TextView)activity.findViewById(R.id.detail_claim_start_person_phone_text);
        endPersonPhoneText = (TextView)activity.findViewById(R.id.detail_claim_end_person_phone_text);
        startPersonName = (TextView)activity.findViewById(R.id.detail_claim_start_person_name);
        endPersonName = (TextView)activity.findViewById(R.id.detail_claim_end_person_name);
        requireText = (TextView)activity.findViewById(R.id.detail_claim_require_text);
        orderIdText = (TextView)activity.findViewById(R.id.detail_claim_order_id_text);
        goodNumText = (TextView)activity.findViewById(R.id.detail_claim_goods_num_text);
        createTimeText = (TextView)activity.findViewById(R.id.detail_claim_start_time_text);
        bottomLayout = (LinearLayout)activity.findViewById(R.id.order_detail_bottom_view);
        order_detail_claim_progressActivity = (ProgressActivity)
                activity.findViewById(R.id.order_detail_claim_progressActivity);

        completeBtn.setOnClickListener(this);
        happenQuestionBtn.setOnClickListener(this);

        bottomLayout.setVisibility(View.GONE);
    }

    /**
     * 取货遇到问题回调
     */
    StringCallback detailCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            order_detail_claim_progressActivity.showError("网络请求错误",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    order_detail_claim_progressActivity.showLoading();
                    orderDetail();
                }
            });
        }

        @Override
        public void onResponse(String response) {

            Response res = new Response(response,ac);
            if(res.code>0){
                String content = null;
                try {
                    JSONObject jsonObject = new JSONObject(res.data);
                    content = jsonObject.getString("orderdetail");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
                }
                orderResponse = (OrderResponse) GsonUtils.jsonToBean(content,OrderResponse.class);

                if (orderResponse == null) {
                    order_detail_claim_progressActivity.showEmpty(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            order_detail_claim_progressActivity.showLoading();
                            orderDetail();
                        }
                    });
                } else {
                    String orderStatusStr = orderResponse.orderStatus;
                    if(orderStatusStr==null||StringUtils.isEmpty(orderStatusStr)){
                        order_detail_claim_progressActivity.showError("无效订单,点击关闭"
                                ,new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ac.finish();
                            }
                        });
                    }

                    orderStatus = Integer.parseInt(orderStatusStr);
                    switch (orderStatus){
                        case 0:
                            statusText.setText("待结算");
                            totalLayout.setVisibility(View.VISIBLE);
                            totalMoney.setText("￥"+orderResponse.totalMoney);
                            bottomLayout.setVisibility(View.GONE);
                            break;
                        case 3:
                            statusText.setText("带取货");
                            totalLayout.setVisibility(View.GONE);
                            bottomLayout.setVisibility(View.VISIBLE);
                            completeBtn.setBackgroundResource(R.color.tab_seleted);
                            completeBtn.setText("确认取货");
                            break;
                        case 4:
                            statusText.setText("待送达");
                            totalLayout.setVisibility(View.GONE);
                            bottomLayout.setVisibility(View.VISIBLE);
                            completeBtn.setBackgroundResource(R.color.red_66);
                            completeBtn.setText("确认送达");
                            break;
                        default:
                            statusText.setText("已完成");
                            totalLayout.setVisibility(View.VISIBLE);
                            bottomLayout.setVisibility(View.GONE);
                            break;
                    }

                    if(!"2".equals(orderResponse.driverOnline)){
                        bottomLayout.setVisibility(View.GONE);
                    }else if("-1".equals(orderResponse.driverOnline)&&orderStatus==3){
                        bottomLayout.setVisibility(View.GONE);
                    }
                    startAddressText.setText(orderResponse.deliverAddr);
                    endAddressText.setText(orderResponse.receiptAddr);
                    startPersonName.setText(orderResponse.deliver);
                    endPersonName.setText(orderResponse.receipt);
                    startPersonPhoneText.setText(orderResponse.deliverTel);
                    endPersonPhoneText.setText(orderResponse.receiptTel);
                    arriveTimeText.setText(orderResponse.leadTime);
                    createTimeText.setText("出发时间："+orderResponse.createTime);
                    requireText.setText("额外需求："+orderResponse.addRequir);
                    orderIdText.setText("订单编号："+orderResponse.id);
                    goodNumText.setText("商品数量："+orderResponse.goodsNum);
                    order_detail_claim_progressActivity.showContent();
                }
            } else{
                if(res.code==-10){
                    order_detail_claim_progressActivity.showError(res.msg, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LoginUtils.toLogin(ac);
                        }
                    });
                }else{
                    order_detail_claim_progressActivity.showError(res.msg, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            order_detail_claim_progressActivity.showLoading();
                            orderDetail();
                        }
                    });
                }
            }
        }
    };

    /**
     * 请求网络详情
     */
    private void orderDetail(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","orderDetail");
            String sid = PreferencesUtils.getSID(ac);
            jsonObject.put("sid", sid);
            jsonObject.put("status",""+requestOrderStatus);
            jsonObject.put("orderId",orderId);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),detailCallBack, Constant.TEST_HOST);

    }

    /**
     * 初始化视图
     */
    public  void initView(){
        order_detail_claim_progressActivity.showLoading();
        orderDetail();
    }

    /**
     * 确认交货的请求回调
     */
    StringCallback affirmPickCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dialog.dismiss();
            Toast.makeText(ac, "网络请求异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            dialog.dismiss();
            Response res = new Response(response,ac);
            if(res.code>0){
                ac.finish();
                //EventBus.getDefault().post(new RefreshEvent(RefreshEvent.TYPE_ARRIVE_REFRESH));
            }else{
                if(res.code == -10){
                    LoginUtils.showDialog(ac,"提示登录","会话已过期，请重新登录");
                }else{
                    Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    /**
     * 确认交货
     */
    private void affirmPickGoods(){
        dialog = new AlertLoadingDialog(ac);
        dialog.show("交货中...");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","affirmPickGoods");
            String sid = PreferencesUtils.getSID(ac);
            if(sid!=null) {
                jsonObject.put("sid", sid);
            }else{
                LoginUtils.toLogin(ac);
            }
            jsonObject.put("orderId",orderId);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),affirmPickCallBack,Constant.TEST_HOST);
    }

    /**
     * 确认送达的回调
     */
    StringCallback affirmDeliveryCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dialog.dismiss();
            Toast.makeText(ac, "网络请求异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            dialog.dismiss();
            Response res = new Response(response,ac);
            if(res.code>0){
                ac.finish();
                //EventBus.getDefault().post(new RefreshEvent(RefreshEvent.TYPE_AFFIRM_PICK_REFRESH));
            }else{
                if(res.code == -10){
                    LoginUtils.showDialog(ac,"提示登录","会话已过期，请重新登录");
                }else{
                    Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /**
     * 确认送达
     */
    private void affirmDelivery(){
        dialog = new AlertLoadingDialog(ac);
        dialog.show("交割中...");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","affirmDelivery");
            String sid = PreferencesUtils.getSID(ac);
            if(sid!=null) {
                jsonObject.put("sid", sid);
            }else{
                LoginUtils.toLogin(ac);
            }
            jsonObject.put("orderId",orderId);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),affirmDeliveryCallBack,Constant.TEST_HOST);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.claim_happen_question_btn:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTNENTS.INTENT_ORDER_ID,orderResponse.id);
                bundle.putString("start_addr",orderResponse.deliverAddr);
                bundle.putString("end_addr",orderResponse.receiptAddr);
                bundle.putInt(Constant.INTNENTS.INTENT_ORDER_STATUS,orderStatus);
                ac.startActivity(HappenQuestionClaimActivity.class,bundle);
                break;
            case R.id.claim_confirm_complete_btn:
                if(orderStatus==3){
                    affirmPickGoods();
                }else if(orderStatus==4){
                    affirmDelivery();
                }
                break;
        }
    }

}
