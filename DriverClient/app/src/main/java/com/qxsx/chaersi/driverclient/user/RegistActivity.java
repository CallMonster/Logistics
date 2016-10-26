package com.qxsx.chaersi.driverclient.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.Reg_RegistReq;
import com.qxsx.chaersi.driverclient.entry.request.Reg_SmsCodeReq;
import com.qxsx.chaersi.driverclient.entry.request.ValSmsCodeReq;
import com.qxsx.chaersi.driverclient.entry.result.Reg_RegistResult;
import com.qxsx.chaersi.driverclient.entry.result.Reg_SmsCodeResult;
import com.qxsx.chaersi.driverclient.entry.result.ValSmsCodeResult;
import com.qxsx.chaersi.driverclient.index.MainActivity;
import com.qxsx.chaersi.driverclient.utils.CheckInput;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity implements Response.ErrorListener{
    private String TAG="RegistActivity";

    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.smsCodeBtn) TextView smsCodeBtn;
    @BindView(R.id.mobileEdit) EditText mobileEdit;
    @BindView(R.id.smsCodeEdit) EditText smsCodeEdit;
    @BindView(R.id.showPassBtn) TextView showPassBtn;
    @BindView(R.id.passEdit) EditText passEdit;
    @BindView(R.id.registBtn) ImageView registBtn;

    private String mobileStr="",smsCodeStr="",passStr="";
    private boolean isPwdOn=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.leftBtn, R.id.smsCodeBtn, R.id.showPassBtn, R.id.registBtn})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                break;
            case R.id.smsCodeBtn:
                mobileStr=mobileEdit.getText().toString();
                if(!TextUtils.isEmpty(mobileStr)&& CheckInput.isMobile(mobileStr)){
                    showProgressDialog("加载中..");
                    BaseApplication.getInstance().addRequestQueue(smsCodeReq);
                }else{
                    showTips("请输入正确的手机号码后再试");
                }
                break;
            case R.id.showPassBtn:
                isShowPwd();
                break;
            case R.id.registBtn:
                mobileStr=mobileEdit.getText().toString();
                smsCodeStr=smsCodeEdit.getText().toString();
                passStr=passEdit.getText().toString();

                if(!TextUtils.isEmpty(mobileStr)&&CheckInput.isMobile(mobileStr)
                        &&!TextUtils.isEmpty(smsCodeStr)&&!TextUtils.isEmpty(passStr)){
                    showProgressDialog("加载中..");
                    BaseApplication.getInstance().addRequestQueue(regReq);
                }else{
                    showTips("请填写完整正确的信息后再试");
                }
                break;
        }
    }

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

    /*发送验证码*/
    StringRequest smsCodeReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,"验证码:"+response);
                    Reg_SmsCodeResult result= BaseApplication.gson.fromJson(response,Reg_SmsCodeResult.class);
                    if("1".equals(result.getCode())){
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
            Reg_SmsCodeReq regSmsCodeReq=new Reg_SmsCodeReq();
            regSmsCodeReq.setCommand("sendMessage");
            regSmsCodeReq.setTel(mobileStr);
            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(regSmsCodeReq));

            return params;
        }
    };

    /*注册*/
    StringRequest regReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,"注册："+response);
                    Reg_RegistResult result= BaseApplication.gson.fromJson(response,Reg_RegistResult.class);
                    if("102".equals(result.getCode())){
                        BaseApplication.getInstance().loginsid=result.getData().getSid();
                        showTips(result.getMsg());
                        hideProgressDialog();
                        showAlert();
                    }else{
                        showTips(result.getMsg());
                        hideProgressDialog();
                    }
                }
            },this){
        @Override
        protected Map<String, String> getParams() {
            Reg_RegistReq regReq=new Reg_RegistReq();
            regReq.setCommand("register");
            regReq.setTel(mobileStr);
            regReq.setPassword(passStr);
            regReq.setVerCode(smsCodeStr);
            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(regReq));
            return params;
        }
    };

    private void showAlert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("注册成功");
        builder.setMessage("完成司机实名认证，开始接单");
        //监听下方button点击事件
        builder.setPositiveButton("立即认证", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent=new Intent(RegistActivity.this,CertifyStep1_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
            }
        });
        builder.setNegativeButton("稍后再说",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent(RegistActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);

            }
        });

        //设置对话框是可取消的
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i(TAG, "err:" + error);
        showTips("网络连接不畅，请稍后再试");
        hideProgressDialog();
    }
}
