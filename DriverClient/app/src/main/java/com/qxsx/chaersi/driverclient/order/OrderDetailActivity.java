package com.qxsx.chaersi.driverclient.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.DetailConfirmReq;
import com.qxsx.chaersi.driverclient.entry.request.OrderDetailReq;
import com.qxsx.chaersi.driverclient.entry.result.OrderDetailResult;
import com.qxsx.chaersi.driverclient.entry.result.OrderDetailResult.DataBean;
import com.qxsx.chaersi.driverclient.entry.result.OrderDetailResult.DataBean.OrderdetailBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {
    private String TAG="OrderDetailActivity";
    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.stateView) TextView stateView;
    @BindView(R.id.timeView) TextView timeView;
    @BindView(R.id.sendAddrView) TextView sendAddrView;
    @BindView(R.id.sendName) TextView sendName;
    @BindView(R.id.sendMobile) TextView sendMobile;
    @BindView(R.id.arrivalAddrView) TextView arrivalAddrView;
    @BindView(R.id.arrivalName) TextView arrivalName;
    @BindView(R.id.arrivalMobile) TextView arrivalMobile;
    @BindView(R.id.orderNo) TextView orderNo;
    @BindView(R.id.beginTime) TextView beginTime;
    @BindView(R.id.pieces) TextView pieces;
    @BindView(R.id.needs) TextView needs;
    @BindView(R.id.problemBtn) TextView problemBtn;
    @BindView(R.id.confirmBtn) TextView confirmBtn;
    @BindView(R.id.btnLayout) LinearLayout btnLayout;
    @BindView(R.id.priceTotalView) TextView priceTotalView;

    private String state="";//订单状态
    private String stateFlag="",orderid="",ordertime="";
    private String confirmCommand="";//确认命令
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        showProgressDialog("加载中..");
        Intent intent=getIntent();
        state=intent.getStringExtra("state");
        orderid=intent.getStringExtra("orderid");
        ordertime=intent.getStringExtra("ordertime");
        if("3".equals(state)){
            stateFlag="0";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                confirmBtn.setBackgroundColor(getResources().getColor(R.color.tab_seleted,null));
            }else{
                confirmBtn.setBackgroundColor(getResources().getColor(R.color.tab_seleted));
            }
            confirmBtn.setText("确认取货");
            problemBtn.setText("取货遇到问题");

            stateView.setText("待取货");
            timeView.setText("取货时间："+ordertime);
        }else if("4".equals(state)){
            stateFlag="0";
            confirmBtn.setBackgroundColor(Color.RED);
            confirmBtn.setText("确认送达");
            problemBtn.setText("交货遇到问题");
            stateView.setText("待送达");
            timeView.setText("送达时间："+ordertime);
        }else{
            stateFlag="1";
            btnLayout.setVisibility(View.GONE);
            stateView.setText("待结算");
            timeView.setText("结算时间："+ordertime);
        }

        if(BaseApplication.getInstance().ONLINE_STATE==false){
            btnLayout.setVisibility(View.GONE);
        }

        title.setText("订单详情");

        BaseApplication.getInstance().addRequestQueue(orderDetailReq);
    }

    @OnClick({R.id.leftBtn, R.id.problemBtn, R.id.confirmBtn})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                break;
            case R.id.problemBtn:
                Intent intent=new Intent(this,OrderProblemActivity.class);
                intent.putExtra("state",state);
                intent.putExtra("orderid",orderid);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                finish();
                break;
            case R.id.confirmBtn:
                if("3".equals(state)){//确认取货
                    confirmCommand="affirmPickGoods";
                    BaseApplication.getInstance().addRequestQueue(confirmReq);
                }else if("4".equals(state)){//确认送达
                    confirmCommand="affirmDelivery";
                    BaseApplication.getInstance().addRequestQueue(confirmReq);
                }else{
                    showTips("是能识别的错误码");
                }
                break;
        }
    }

    /*订单详情*/
    StringRequest orderDetailReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.i(TAG,s);
                    OrderDetailResult result=BaseApplication.gson.fromJson(s,OrderDetailResult.class);
                    if("110".equals(result.getCode())){
                        OrderdetailBean detail= result.getData().getOrderdetail();
                        sendAddrView.setText(detail.getDeliverAddr());
                        sendName.setText("发货人："+detail.getDeliver());
                        sendMobile.setText(detail.getDeliverTel());
                        arrivalAddrView.setText(detail.getReceiptAddr());
                        arrivalName.setText("收货人："+detail.getReceipt());
                        arrivalMobile.setText(detail.getReceiptTel());

                        orderNo.setText("订单编号："+detail.getOrderNum());
                        beginTime.setText("出发时间："+detail.getTakersTime());
                        pieces.setText("物品件数："+detail.getGoodsNum());
                        needs.setText("额外需求："+detail.getAddRequir());

                        priceTotalView.setText("订单总金额：¥"+detail.getTotalMoney());
                        if("4".equals(state)||"3".equals(state)){
                            btnLayout.setVisibility(View.VISIBLE);
                        }
                    }else{
                        showTips(result.getMsg());
                    }
                    hideProgressDialog();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(TAG,"err:"+volleyError.getMessage());
                    showTips("网络不畅，请稍后再试");
                    hideProgressDialog();
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            OrderDetailReq req=new OrderDetailReq();
            req.setCommand("orderDetail");
            req.setSid(BaseApplication.getInstance().loginsid);
            req.setOrderId(orderid);
            req.setStatus(stateFlag);

            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(req));
            return params;
        }
    };

    StringRequest confirmReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.i(TAG,"确认："+s);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i(TAG,"错误了："+volleyError.getMessage());
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            DetailConfirmReq req=new DetailConfirmReq();
            req.setCommand(confirmCommand);
            req.setSid(BaseApplication.getInstance().loginsid);
            req.setOrderId(orderid);
            req.setStatus("0");

            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(req));
            return params;
        }
    };

}
