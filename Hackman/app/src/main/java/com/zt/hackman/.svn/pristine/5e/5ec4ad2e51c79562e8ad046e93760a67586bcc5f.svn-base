package com.zt.hackman.model;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/30.
 */
public class NewPhoneValidModel implements View.OnClickListener{
    public RelativeLayout navLayout;
    public TextView updateBtn,getNumberText;
    private BaseActivity ac ;
    private EditText phoneText,numberText;
    private TextView flagText;
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
                            getNumberText.setClickable(true);
                            getNumberText.setText("重新发送验证码");
                        }else{
                            getNumberText.setText("发送验证码("+currentNum+")");
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

    public void findViewByIds(BaseActivity ac){
        this.ac = ac;
        navLayout = (RelativeLayout)ac.findViewById(R.id.new_bank_phone_valid_nav);
        updateBtn = (TextView)ac.findViewById(R.id.new_validPhoneNextBtn);
        phoneText = (EditText) ac.findViewById(R.id.new_bank_phoneNumberEdit);
        numberText = (EditText) ac.findViewById(R.id.new_phone_codeEdit);
        getNumberText = (TextView) ac.findViewById(R.id.new_getNumberBtn);
        flagText = (TextView)ac.findViewById(R.id.new_validPhoneFlagText);

        navLayout.setBackgroundResource(R.color.colorWhite);
        updateBtn.setOnClickListener(this);
        getNumberText.setOnClickListener(this);
    }

    private StringCallback updateCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String response) {

        }
    };

    /**
     * 修改信息
     */
    private void update(){
        String tel = phoneText.getText().toString();
        String verCode = numberText.getText().toString();
        if(tel==null|| StringUtils.isEmpty(tel)){
            Toast.makeText(ac,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if(verCode==null|| StringUtils.isEmpty(verCode)){
            Toast.makeText(ac,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","modifyMobile");
            jsonObject.put("tel",tel);
            jsonObject.put("verCode",verCode);
            jsonObject.put("sid","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HackRequest().request(jsonObject.toString(),updateCallBack, Constant.TEST_HOST);
    }


    /**
     * 发送验证码响应对象
     * @param v
     */
    public StringCallback sendcallback = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {

            getNumberText.setClickable(true);
            //mHandler.removeCallbacksAndMessages(null);
            //mHandler = null;
            currentNum = 60;
            isstop = true;
            getNumberText.setText("发送验证码");
            flagText.setText("网络请求异常");
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            //mHandler.removeCallbacksAndMessages(null);
            //mHandler = null;
            if(res.code==1) {
                flagText.setText("发送验证码成功");
                //清除model里的数据
                //ac.finish();

            }else{
                currentNum = 60;
                isstop = true;
                getNumberText.setText("发送验证码");
                getNumberText.setClickable(true);
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };
    /**13821576236
     * 发送验证码
     * @throws JSONException
     */
    private void sendValid() throws JSONException {
        getNumberText.setClickable(false);
        isstop= false;
        mHandler.sendEmptyMessage(1);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("command","sendVerifyCode");
        jsonObject.put("sid","1");
        request.request(jsonObject.toString(),sendcallback, Constant.TEST_HOST);
    }

    public void initView(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_validPhoneNextBtn:
                update();
                break;
        }
    }
}
