package com.qxsx.chaersi.driverclient.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.LoginReq;
import com.qxsx.chaersi.driverclient.entry.result.LoginResult;
import com.qxsx.chaersi.driverclient.index.MainActivity;
import com.qxsx.chaersi.driverclient.utils.CheckInput;
import com.qxsx.chaersi.driverclient.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    private String TAG="LoginActivity";

    @BindView(R.id.rightView) TextView rightView;
    @BindView(R.id.telEdit) EditText telEdit;
    @BindView(R.id.passEdit) EditText passEdit;
    @BindView(R.id.rightBtn) View rightBtn;
    @BindView(R.id.loginBtn) ImageView loginBtn;
    private String mobileStr="",passStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        rightView.setText("忘记密码");
        rightView.setVisibility(View.VISIBLE);
        rightBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        telEdit.setText("13821576236");
        passEdit.setText("000000");
    }

    @Override
    public void onClickListener(View v) {
        switch (v.getId()){
            case R.id.rightBtn:
                Intent intent=new Intent(this,ForgetPassActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                break;
            case R.id.loginBtn:
                mobileStr = telEdit.getText().toString();
                passStr = passEdit.getText().toString();
                if(TextUtils.isEmpty(mobileStr)||TextUtils.isEmpty(passStr)){
                    showTips("请补全信息后再试");
                }else{
                    if(CheckInput.isMobile(mobileStr)){
                        showProgressDialog("加载中..");
                        BaseApplication.getInstance().addRequestQueue(loginReq);
                    }else{
                        showTips("请输入正确的手机号后再试");
                    }
                }
                break;
        }
    }

    StringRequest loginReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,"succ:"+response);
                    LoginResult result=BaseApplication.gson.fromJson(response,LoginResult.class);
                    if("101".equals(result.getCode())){
                        BaseApplication.getInstance().loginsid=result.getData().getSid();
                        PreferenceUtils preference=new PreferenceUtils(LoginActivity.this);
                        preference.setUserId(result.getData().getSid());

                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                        hideProgressDialog();
                    }else{
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
                LoginReq loginReq=new LoginReq();
                loginReq.setCommand("login");
                loginReq.setTel(mobileStr);
                loginReq.setPassword(passStr);
                HashMap<String, String> params = new HashMap<>();
                params.put("data", BaseApplication.gson.toJson(loginReq));

                return params;
            }
    };

}
