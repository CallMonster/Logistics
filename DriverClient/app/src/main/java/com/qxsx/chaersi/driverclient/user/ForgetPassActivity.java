package com.qxsx.chaersi.driverclient.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.qxsx.chaersi.driverclient.entry.request.CheckSmsCodeReq;
import com.qxsx.chaersi.driverclient.entry.request.LoginReq;
import com.qxsx.chaersi.driverclient.entry.request.ValSmsCodeReq;
import com.qxsx.chaersi.driverclient.entry.result.CheckSmsCodeResult;
import com.qxsx.chaersi.driverclient.entry.result.LoginResult;
import com.qxsx.chaersi.driverclient.entry.result.ValSmsCodeResult;
import com.qxsx.chaersi.driverclient.utils.CheckInput;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPassActivity extends BaseActivity implements Response.ErrorListener{
    private String TAG="ForgetPassActivity";

    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.smsCodeBtn) TextView smsCodeBtn;
    @BindView(R.id.mobileEdit) EditText mobileEdit;
    @BindView(R.id.smsCodeEdit) EditText smsCodeEdit;
    @BindView(R.id.updatePassBtn) ImageView updatePassBtn;

    private String mobileStr="",smsCodeStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);

        leftBtn.setOnClickListener(this);
        smsCodeBtn.setOnClickListener(this);
        updatePassBtn.setOnClickListener(this);

        mobileEdit.setText("18222933753");
    }

    @Override
    public void onClickListener(View v) {
        switch (v.getId()){
            case R.id.leftBtn:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                break;
            case R.id.smsCodeBtn:
                mobileStr=mobileEdit.getText().toString();
                if(CheckInput.isMobile(mobileStr)){
                    showProgressDialog("加载中..");
                    BaseApplication.getInstance().addRequestQueue(smsCodeReq);
                }else{
                    showTips("请输入正确的手机号码后再试");
                }
                break;
            case R.id.updatePassBtn:
                smsCodeStr=smsCodeEdit.getText().toString();
                mobileStr=mobileEdit.getText().toString();
                if(!TextUtils.isEmpty(smsCodeStr)&&!TextUtils.isEmpty(mobileStr)){
                    showProgressDialog("加载中..");
                    BaseApplication.getInstance().addRequestQueue(isValCodeReq);
                }else{
                    showTips("请输入正确的信息后再试");
                }
                break;
        }
    }

    /*发送验证码*/
    StringRequest smsCodeReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,"succ:"+response);
                    ValSmsCodeResult result= BaseApplication.gson.fromJson(response,ValSmsCodeResult.class);
                    if("104".equals(result.getCode())){
                        showTips(result.getMsg());
                        mobileEdit.setEnabled(false);
                        hideProgressDialog();
                    }else{
                        showTips(result.getMsg());
                        hideProgressDialog();
                    }
                }
            },this){
        @Override
        protected Map<String, String> getParams() {
            ValSmsCodeReq smsCodeReq=new ValSmsCodeReq();
            smsCodeReq.setCommand("sendMessageForResetPWD");
            smsCodeReq.setTel(mobileStr);
            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(smsCodeReq));

            return params;
        }
    };

    /*验证验证码*/
    StringRequest isValCodeReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,response);
                    CheckSmsCodeResult result= BaseApplication.gson.fromJson(response,CheckSmsCodeResult.class);
                    if("105".equals(result.getCode())){
                        showTips(result.getMsg());
                        hideProgressDialog();
                        Intent intent=new Intent(ForgetPassActivity.this,ResetPassActivity.class);
                        intent.putExtra("tel",mobileStr);
                        startActivity(intent);
                        ForgetPassActivity.this.finish();
                        overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                    }else{
                        showTips(result.getMsg());
                        hideProgressDialog();
                    }
                }
            },this){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            CheckSmsCodeReq checkCodeReq=new CheckSmsCodeReq();
            checkCodeReq.setCommand("checkVerifyCodeNoSs");
            checkCodeReq.setTel(mobileStr);
            checkCodeReq.setVerCode(smsCodeStr);
            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(checkCodeReq));

            return params;
        }
    };

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i(TAG, "err:" + error);
        showTips("网络连接不畅，请稍后再试");
        hideProgressDialog();
    }

}
