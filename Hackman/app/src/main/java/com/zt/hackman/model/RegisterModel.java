package com.zt.hackman.model;

import android.app.Activity;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;
import com.zt.hackman.view.RegisterSucDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/18.
 */
public class RegisterModel implements View.OnClickListener{
    public TextView showBtn,getNumBtn,registerBtn,rightBtn;
    public ImageView leftBtn;
    public EditText phoneText,pwdText,numText;
    private int current = 0;
    private Activity ac;

    private HackRequest request = new HackRequest();
    private boolean isstop= false;
    private int currentNum = 60;

    private Handler mHandler = new Handler() {
        /*
         * edit by yuanjingchao 2014-08-04 19:10
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    // 添加更新ui的代码
                    if (!isstop) {
                        if(currentNum==0){
                            getNumBtn.setClickable(true);
                            getNumBtn.setText("重新发送验证码");
                        }else{
                            getNumBtn.setText("发送验证码("+currentNum+")");
                            currentNum--;
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                        }
                    }
                    break;
                case 2:
                    break;
            }
        }

    };

    /**
     * 清除登录按钮的动画
     */
    private void clearAnim(){
        registerBtn.clearAnimation();
        registerBtn.setBackgroundResource(R.mipmap.login_btn);
    }

    public StringCallback registerCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            clearAnim();
            Toast.makeText(ac,"注册用户失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){

                RegisterSucDialog dialog=new RegisterSucDialog(ac,R.layout.dialog_reigster);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                        clearAnim();
                    }
                });
            }else{
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }

        }
    };

    /**
     * 发送验证码响应对象
     * @param v
     */
    public StringCallback sendcallback = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {

            getNumBtn.setClickable(true);
            //mHandler.removeCallbacksAndMessages(null);
            //mHandler = null;
            currentNum = 60;
            isstop = true;
            getNumBtn.setText("发送验证码");
            clearAnim();
            Toast.makeText(ac, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            //mHandler.removeCallbacksAndMessages(null);
            //mHandler = null;
            clearAnim();
            if(res.code==1) {
                ToastUtils.showSuccess(ac, "发送验证码成功");
                //清除model里的数据
                //ac.finish();

            }else{
                currentNum = 60;
                isstop = true;
                getNumBtn.setText("发送验证码");
                getNumBtn.setClickable(true);
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 注册信息
     */
    private void register(){
        String registerId = phoneText.getText().toString();
        String reigsterNum = numText.getText().toString();
        String pwd = pwdText.getText().toString();
        if(registerId==null||StringUtils.isEmpty(registerId)){
            Toast.makeText(ac,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(reigsterNum==null||StringUtils.isEmpty(reigsterNum)){
            Toast.makeText(ac,"请输入验证码",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pwd==null||StringUtils.isEmpty(pwd)){
            Toast.makeText(ac,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","register");
            jsonObject.put("tel",registerId);
            jsonObject.put("password",pwd);
            jsonObject.put("verCode",reigsterNum);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"json数据转换异常",Toast.LENGTH_SHORT).show();
        }
        request.request(jsonObject.toString(),registerCallBack, Constant.TEST_HOST);
    }

    public void findViewByIds(Activity view){
        this.ac = view;
        getNumBtn = (TextView)view. findViewById(R.id.reigster_getNumBtn);
        numText = (EditText)view. findViewById(R.id.register_num_text);
        phoneText = (EditText)view. findViewById(R.id.register_id_text);
        rightBtn  =(TextView)view. findViewById(R.id.right_btn);
        pwdText =(EditText)view. findViewById(R.id.register_pwd);
        registerBtn = (TextView)view. findViewById(R.id.register_btn);
        showBtn = (TextView)view. findViewById(R.id.register_show_btn);
        leftBtn = (ImageView)view. findViewById(R.id.left_img);

        showBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        getNumBtn .setOnClickListener(this);
        leftBtn.setOnClickListener(this);
    }

    /**13821576236
     * 发送验证码
     * @throws JSONException
     */
    private void sendValid() throws JSONException {
        if(phoneText.getText()==null|| StringUtils.isEmpty(phoneText.getText().toString())){
            Toast.makeText(ac, "请输入您的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        getNumBtn.setClickable(false);
        isstop= false;
        mHandler.sendEmptyMessage(1);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("command","sendMessage");
        jsonObject.put("tel",phoneText.getText().toString());
        request.request(jsonObject.toString(),sendcallback, Constant.TEST_HOST);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.register_show_btn:   //显示密码
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
            case R.id.register_btn:
                registerBtn.setBackgroundResource(R.mipmap.logining);
                registerBtn.startAnimation(AnimationUtils.loadAnimation(ac,R.anim.rotate_anim));
                register();

//                intent = new Intent(this,LoginActivity.class);
//                startActivity(intent);
                break;
            case R.id.reigster_getNumBtn:
                try {
                    sendValid();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ac,"参数转换异常",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.left_img:
                ac.finish();
                break;
        }
    }
}
