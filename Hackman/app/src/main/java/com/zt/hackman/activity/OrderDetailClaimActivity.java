package com.zt.hackman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.model.OrderDetailClaimModel;

/**
 * Created by Administrator on 2016/9/21.
 */
public class OrderDetailClaimActivity extends BaseActivity implements LeftListener,RightListener{

    NavModel navModel;

    OrderDetailClaimModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_claim);
        initNavBar();
        findViewByIds();
        initMap(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setRightListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("订单详情");
    }

    @Override
    protected void findViewByIds() {
        model = new OrderDetailClaimModel();
        Bundle bundle = getIntent().getExtras();
        if(bundle==null){
            model.findViewByIds(this,"0",-1);
        }else{
            String orderId = bundle.getString(Constant.INTNENTS.INTENT_ORDER_ID);
            int orderStatus = bundle.getInt(Constant.INTNENTS.INTENT_ORDER_STATUS);
            model.findViewByIds(this,orderId,orderStatus);
        }
    }

    private void initMap(Bundle bundle){
        model.mapView.onCreate(bundle);

    }
    @Override
    protected void initView() {
        model.initView();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        model.mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        model.mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        model.mapView.onSaveInstanceState(outState);
    }


}
