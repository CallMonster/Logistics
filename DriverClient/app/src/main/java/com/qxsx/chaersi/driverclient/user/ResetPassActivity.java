package com.qxsx.chaersi.driverclient.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.ResetPassReq;
import com.qxsx.chaersi.driverclient.entry.request.ValSmsCodeReq;
import com.qxsx.chaersi.driverclient.entry.result.ResetPassResult;
import com.qxsx.chaersi.driverclient.entry.result.ValSmsCodeResult;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPassActivity extends BaseActivity {
    private String TAG="ResetPassActivity";

    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.showPassBtn) TextView showPassBtn;
    @BindView(R.id.passEdit) EditText passEdit;
    @BindView(R.id.confirmEdit) EditText confirmEdit;
    @BindView(R.id.updatePassBtn) ImageView updatePassBtn;

    private boolean isPwdOn=true;
    private String passStr="",confirmPassStr="";
    private String mobileStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        mobileStr=intent.getStringExtra("tel");

    }

    @OnClick({R.id.leftBtn, R.id.showPassBtn, R.id.updatePassBtn})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                break;
            case R.id.showPassBtn:
                isShowPwd();
                break;
            case R.id.updatePassBtn:
                passStr=passEdit.getText().toString();
                confirmPassStr=confirmEdit.getText().toString();
                if(!TextUtils.isEmpty(passStr)&&!TextUtils.isEmpty(confirmPassStr)){
                    if(passStr.equals(confirmPassStr)){
                        showProgressDialog("加载中..");
                        resetPassReq.setRetryPolicy(new DefaultRetryPolicy(20000, 0, 1f));
                        BaseApplication.getInstance().addRequestQueue(resetPassReq);
                    }else{
                        showTips("两次输入密码不一致");
                    }
                }else{
                    showTips("请将信息填写完整后再试");
                }
                break;
        }
    }

    StringRequest resetPassReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "succ:" + response);
                    ResetPassResult result = BaseApplication.gson.fromJson(response, ResetPassResult.class);
                    if ("103".equals(result.getCode())) {
                        showTips(result.getMsg());
                        hideProgressDialog();
                        ResetPassActivity.this.finish();
                        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                    } else {
                        showTips(result.getMsg());
                        hideProgressDialog();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "err:" + error);
                    showTips("网络连接不畅，请稍后再试");
                    hideProgressDialog();
                }
            }){
        @Override
        protected Map<String, String> getParams() {
            ResetPassReq resetPass=new ResetPassReq();
            resetPass.setCommand("Resetpassword");
            resetPass.setTel(mobileStr);
            resetPass.setPassword(passStr);
            resetPass.setConfirmpassword(confirmPassStr);
            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(resetPass));

            return params;
        }
    };



    /**
     * 显示和隐藏密码
     */
    private void isShowPwd() {
        if (isPwdOn) {
            showPassBtn.setText("隐藏");
            passEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            showPassBtn.setText("显示");
            passEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        isPwdOn = !isPwdOn;
        passEdit.postInvalidate();

        //切换后将EditText光标置于末尾
        CharSequence charSequence = passEdit.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

}
