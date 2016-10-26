package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.LoginActivity;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.SetPwdEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/18.
 */
public class SetpwdModel implements View.OnClickListener{
    public TextView setpwdBtn,showBtn,rightBtn;
    public ImageView leftImage;
    public EditText pwdText,repwdText;
    private  String tel;
    private Activity ac;
    private int current = 0;

    public void findViewByIds(Activity activity, String tel){
        this.tel = tel;
        this.ac = activity;
        setpwdBtn = (TextView)activity. findViewById(R.id.setpwd_btn);
        showBtn = (TextView)activity.findViewById(R.id.setpwd_show_btn);
        pwdText = (EditText) activity.findViewById(R.id.setpwd_new_text);
        repwdText = (EditText)activity.findViewById(R.id.setpwd_repeat_text);
        rightBtn = (TextView)activity.findViewById(R.id.right_btn);
        leftImage = (ImageView)activity.findViewById(R.id.left_img);

        setpwdBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
        leftImage.setOnClickListener(this);
    }

    StringCallback setPwdCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            clearAnim();
            Toast.makeText(ac,"手机号码不能为空",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            clearAnim();

            Response res = new Response(response,ac);
            if(res.code>0){
                ToastUtils.showSuccess(ac, res.msg);
                EventBus.getDefault().post(new SetPwdEvent(SetPwdEvent.TYPE_BACK));
                ac.finish();
            }else{
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };
    /**
     * 设置密码
     */
    private void setPwd(){
        //{"command":"Resetpassword","tel":"18330219027","password":"183302","confirmpassword":"183302"}
        String pwd = pwdText.getText().toString();
        String repwd = repwdText.getText().toString();
        if(tel==null|| StringUtils.isEmpty(tel)){
            Toast.makeText(ac,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pwd==null|| StringUtils.isEmpty(pwd)){
            Toast.makeText(ac,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(repwd==null|| StringUtils.isEmpty(repwd)){
            Toast.makeText(ac,"重复密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!pwd.equals(repwd)){
            Toast.makeText(ac,"两次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","Resetpassword");
            jsonObject.put("tel",tel);
            jsonObject.put("password",pwd);
            jsonObject.put("confirmpassword",repwd);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),setPwdCallBack, Constant.TEST_HOST);
    }
    /**
     * 清除登录按钮的动画
     */
    private void clearAnim(){
        setpwdBtn.clearAnimation();
        setpwdBtn.setBackgroundResource(R.mipmap.login_btn);
    }
    public void clear(){
        setpwdBtn =null;
        showBtn = null;
        pwdText = null;
        repwdText = null;
        rightBtn = null;
        leftImage = null;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.setpwd_show_btn:
                if(current==0){  //密码状态改为非密码状态
                    pwdText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    current =1;
                    showBtn.setText("隐藏");
                }else if(current ==1){  //非密码状态改为密码状态
                    pwdText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    current = 0;
                    showBtn.setText("显示");
                }
                break;
            case R.id.setpwd_btn:
                setpwdBtn.setBackgroundResource(R.mipmap.logining);
                setpwdBtn.startAnimation(AnimationUtils.loadAnimation(ac,R.anim.rotate_anim));
                setPwd();


                break;
            case R.id.left_img:
                ac.finish();
                break;
        }
    }
}
