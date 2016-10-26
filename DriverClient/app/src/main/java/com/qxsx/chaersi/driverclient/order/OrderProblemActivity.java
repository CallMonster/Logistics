package com.qxsx.chaersi.driverclient.order;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.adapter.OrderProblemAdapter;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.DetailConfirmReq;
import com.qxsx.chaersi.driverclient.entry.request.ExceptRequest;
import com.qxsx.chaersi.driverclient.entry.request.Problem_AddrReq;
import com.qxsx.chaersi.driverclient.entry.request.Problem_GoodsReq;
import com.qxsx.chaersi.driverclient.entry.result.ExceptResult;
import com.qxsx.chaersi.driverclient.entry.result.ExceptResult.DataBean.ReasonBean;
import com.qxsx.chaersi.driverclient.entry.result.Problem_GoodsResult;
import com.qxsx.chaersi.driverclient.widget.ListViewShowView;
import com.qxsx.chaersi.driverclient.widget.impl.OnRecyclerViewListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tj.opensrc.selectphoto.SystemAlbumPickerActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class OrderProblemActivity extends BaseActivity {
    private String TAG="OrderProblemActivity";
    public static final int IMAGE1_REQUEST_CODE = 1;
    public static final int IMAGE2_REQUEST_CODE = 2;
    public static final int IMAGE3_REQUEST_CODE = 3;

    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.rightView) TextView rightView;
    @BindView(R.id.rightBtn) View rightBtn;
    @BindView(R.id.chooseProblemBtn) LinearLayout chooseProblemBtn;
    @BindView(R.id.descView) TextView descView;
    @BindView(R.id.descLayout) LinearLayout descLayout;
    @BindView(R.id.image1) ImageView image1;
    @BindView(R.id.image2) ImageView image2;
    @BindView(R.id.image3) ImageView image3;
    @BindView(R.id.priceEdit) EditText priceEdit;
    @BindView(R.id.sendAddrEdit) EditText sendAddrEdit;
    @BindView(R.id.arrivalAddrEdit) EditText arrivalAddrEdit;
    @BindView(R.id.submitBtn) TextView submitBtn;
    @BindView(R.id.problemTitle) TextView problemTitle;

    @BindView(R.id.photoLayout) RelativeLayout photoLayout;
    @BindView(R.id.addPriceLayout) LinearLayout addPriceLayout;

    private String state="",orderid="";
    private Picasso picasso;
    private String IMAGE_PATH1="",IMAGE_PATH2="",IMAGE_PATH3="";
    private String descStr="",sendAddrStr="",arrivalAddrStr="",priceStr="";//提交值
    private String addrProblemStr="";//地址异常提交值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_problem);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        state=intent.getStringExtra("state");
        orderid=intent.getStringExtra("orderid");
        if("3".equals(state)){
            title.setText("取货遇到问题");
        }else if("4".equals(state)){
            title.setText("交货遇到问题");
        }
        rightView.setText("联系客服");
        rightView.setVisibility(View.VISIBLE);
        rightView.setTextColor(getResources().getColor(R.color.bottom_bg_color));
        rightBtn.setVisibility(View.VISIBLE);

        picasso= BaseApplication.getInstance().built;
    }

    @OnClick({R.id.leftBtn, R.id.rightBtn, R.id.chooseProblemBtn, R.id.image1, R.id.image2, R.id.image3,R.id.submitBtn})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                break;
            case R.id.rightBtn:
                break;
            case R.id.chooseProblemBtn:
                showProgressDialog("加载最新异常信息..");
                BaseApplication.getInstance().addRequestQueue(exceptReq);
                break;
            case R.id.image1:
                Intent Intent1 = new Intent(this, SystemAlbumPickerActivity.class);
                Intent1.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(Intent1, IMAGE1_REQUEST_CODE);
                break;
            case R.id.image2:
                Intent Intent2 = new Intent(this, SystemAlbumPickerActivity.class);
                Intent2.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(Intent2, IMAGE2_REQUEST_CODE);
                break;
            case R.id.image3:
                Intent Intent3 = new Intent(this, SystemAlbumPickerActivity.class);
                Intent3.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(Intent3, IMAGE3_REQUEST_CODE);
                break;
            case R.id.cancelBtn:
                problemDialog.dismiss();
                break;
            case R.id.submitBtn:
                if("1".equals(reasonId)){
                    descStr=descView.getText().toString();
                    if(!TextUtils.isEmpty(IMAGE_PATH1)){
                        if(!TextUtils.isEmpty(descStr)){
                            showProgressDialog("提交中...");
                            submit_Goods();
                        }else{
                            showTips("请填写异常问题");
                        }
                    }else{
                        showTips("请添加一张图片后再试");
                    }
                }else if("2".equals(reasonId)){
                    descStr=descView.getText().toString();
                    sendAddrStr=sendAddrEdit.getText().toString();
                    arrivalAddrStr=arrivalAddrEdit.getText().toString();
                    priceStr=priceEdit.getText().toString();
                    if(!TextUtils.isEmpty(descStr)&&!TextUtils.isEmpty(sendAddrStr)
                            && !TextUtils.isEmpty(arrivalAddrStr)
                            && !TextUtils.isEmpty(priceStr)){
                        Problem_AddrReq addrReq=new Problem_AddrReq();
                        addrReq.setCommand("reSendOrder");
                        addrReq.setSid(BaseApplication.getInstance().loginsid);
                        addrReq.setDescribe(descStr);
                        addrReq.setOrderID(orderid);
                        addrReq.setNewPrice(priceStr);
                        addrReq.setNewDeliverAddr(sendAddrStr);
                        addrReq.setNewReceiptAddr(arrivalAddrStr);
                        addrReq.setExceptionReasonId(reasonId);
                        addrProblemStr=BaseApplication.gson.toJson(addrReq);
                        BaseApplication.getInstance().addRequestQueue(submit_AddrReq);
                    }else{
                        showTips("请补全信息后再试");
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE1_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH) {
            IMAGE_PATH1=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(IMAGE_PATH1))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(image1, new Callback() {
                        @Override
                        public void onSuccess() {
                            image1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                image1.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                image1.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
            image2.setVisibility(View.VISIBLE);
        }else if(requestCode == IMAGE2_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            IMAGE_PATH2=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(IMAGE_PATH2))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(image2, new Callback() {
                        @Override
                        public void onSuccess() {
                            image2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                image2.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                image2.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
            image3.setVisibility(View.VISIBLE);
        }else if(requestCode == IMAGE3_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            IMAGE_PATH3=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(IMAGE_PATH3))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(image3, new Callback() {
                        @Override
                        public void onSuccess() {
                            image3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                image3.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                image3.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
        }
    }

    StringRequest exceptReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.i(TAG,"异常列表："+s);
                    ExceptResult result=BaseApplication.gson.fromJson(s,ExceptResult.class);
                    if("1".equals(result.getCode())){
                        reason=result.getData().getReason();
                        hideProgressDialog();
                        showProblemDialog();
                    }else{
                        showTips(result.getMsg());
                        hideProgressDialog();
                    }
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
            ExceptRequest req=new ExceptRequest();
            req.setCommand("exceptionReason");
            req.setSid(BaseApplication.getInstance().loginsid);
            req.setType("1");

            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(req));
            return params;
        }
    };

    private Dialog problemDialog;
    private ListViewShowView problemListView;
    private List<ReasonBean> reason;
    private String reasonId="";
    /**
     * 选择异常类型
     */
    private void showProblemDialog() {
        LinearLayout problemLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_problem, null);
        problemDialog = new Dialog(this, R.style.buttonSheetWindowStyle);
        problemDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        problemDialog.setContentView(problemLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        problemDialog.setCancelable(true);
        problemDialog.setCanceledOnTouchOutside(false);

        Window dialogWindow = problemDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.button_sheet_animstyle);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.x = 0;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(params);
        problemDialog.show();

        problemListView= (ListViewShowView) problemLayout.findViewById(R.id.problemListView);
        OrderProblemAdapter adapter=new OrderProblemAdapter(this,reason,reasonId);
        problemListView.setAdapter(adapter);
        problemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reasonId=reason.get(position).getReasonId();
                if("1".equals(reasonId)){
                    photoLayout.setVisibility(View.VISIBLE);
                    addPriceLayout.setVisibility(View.GONE);
                }else if("2".equals(reasonId)){
                    photoLayout.setVisibility(View.GONE);
                    addPriceLayout.setVisibility(View.VISIBLE);
                }
                problemTitle.setText(reason.get(position).getTitle());
                problemDialog.dismiss();
            }
        });

        TextView cancelBtn= (TextView) problemLayout.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
        TextView confirmBtn= (TextView) problemLayout.findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(this);

    }

    /*取货地址变更*/
    StringRequest submit_AddrReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.i(TAG,"地址变更："+s);
                    Problem_GoodsResult result=BaseApplication.gson.fromJson(s,Problem_GoodsResult.class);
                    if("112".equals(result.getCode())){
                        showTips("问题已提交");
                        OrderProblemActivity.this.finish();
                        overridePendingTransition(R.anim.in_from_left,R.anim.out_from_right);
                    }else{
                        showTips(result.getMsg());
                    }
                    hideProgressDialog();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    showTips("网络连接不畅，请稍后再试");
                    hideProgressDialog();
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> params = new HashMap<>();
            params.put("data", addrProblemStr);
            return params;
        }
    };

    /*取货货物异常*/
    private void submit_Goods(){
        Problem_GoodsReq goodsReq=new Problem_GoodsReq();
        goodsReq.setCommand("pickupProblem");
        goodsReq.setSid(BaseApplication.getInstance().loginsid);
        goodsReq.setType("3");
        goodsReq.setOrderId(orderid);
        goodsReq.setDescribe(descStr);

        PostFormBuilder builder= OkHttpUtils.post();
        if(!TextUtils.isEmpty(IMAGE_PATH1)){
            File imgFile1=new File(IMAGE_PATH1);
            builder.addFile("exceptionImg1",imgFile1.getName(),imgFile1);
            if(!TextUtils.isEmpty(IMAGE_PATH2)){
                File imgFile2=new File(IMAGE_PATH2);
                builder.addFile("exceptionImg2",imgFile2.getName(),imgFile2);
                if(!TextUtils.isEmpty(IMAGE_PATH3)){
                    File imgFile3=new File(IMAGE_PATH3);
                    builder.addFile("exceptionImg3",imgFile3.getName(),imgFile3);
                }
            }
        }
        builder.addParams("data", BaseApplication.gson.toJson(goodsReq))
                .url(InitDesign.BASE_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showTips("网络连接不畅，请稍后再试");
                        hideProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG,"问题提交成功1:"+response);
                        Problem_GoodsResult result=BaseApplication.gson.fromJson(response,Problem_GoodsResult.class);
                        if("112".equals(result.getCode())){
                            showTips("问题已提交");
                            OrderProblemActivity.this.finish();
                            overridePendingTransition(R.anim.in_from_left,R.anim.out_from_right);
                        }else{
                            showTips(result.getMsg());
                        }
                        hideProgressDialog();
                    }
                });
    }

}
