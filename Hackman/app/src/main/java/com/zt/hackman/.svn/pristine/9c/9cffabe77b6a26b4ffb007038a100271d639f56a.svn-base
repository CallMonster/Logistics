package com.zt.hackman.model;

import android.app.Activity;
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
import com.zt.hackman.activity.NewPhoneValidActivity;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.ExitEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/27.
 */
public class ValidPhoneModel implements View.OnClickListener{

    public TextView getNumberBtn,nextBtn,flagText;
    public EditText numberText;
    public RelativeLayout validPhoneNav;
    private BaseActivity ac;
    private int way;
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
                            getNumberBtn.setClickable(true);
                            getNumberBtn.setText("重新发送验证码");
                        }else{
                            getNumberBtn.setText("发送验证码("+currentNum+")");
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
     * 发送验证码响应对象
     * @param v
     */
    public StringCallback sendcallback = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {

            getNumberBtn.setClickable(true);
            //mHandler.removeCallbacksAndMessages(null);
            //mHandler = null;
            currentNum = 60;
            isstop = true;
            getNumberBtn.setText("发送验证码");
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
                getNumberBtn.setText("发送验证码");
                getNumberBtn.setClickable(true);
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**13821576236
     * 发送验证码
     * @throws JSONException
     */
    private void sendValid() throws JSONException {
        getNumberBtn.setClickable(false);
        isstop= false;
        mHandler.sendEmptyMessage(1);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("command","sendVerifyCode");
        jsonObject.put("sid","1");
        request.request(jsonObject.toString(),sendcallback, Constant.TEST_HOST);
    }



    public void findViewByIds(BaseActivity activity,int way){
        this.ac = activity;
        this.way = way;
        getNumberBtn =(TextView) activity.findViewById(R.id.getNumberBtn);
        nextBtn = (TextView)activity.findViewById(R.id.validPhoneNextBtn);
        flagText = (TextView)activity.findViewById(R.id.validPhoneFlagText);
        validPhoneNav = (RelativeLayout)activity.findViewById(R.id.bank_phone_valid_nav);
        numberText = (EditText)activity.findViewById(R.id.bank_phoneNumberEdit);

        validPhoneNav.setBackgroundResource(R.color.colorWhite);
        nextBtn.setOnClickListener(this);
        getNumberBtn.setOnClickListener(this);
    }

    public void initView(String phoneNum){
        flagText.setText("修改绑定手机后，将使用手机号码重新登录：当前手机号码为"+phoneNum+",请验证!");

    }

    StringCallback validCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            flagText.setText("网络请求异常");
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code >0){
                ac.startActivity(NewPhoneValidActivity.class);
            }else{
                flagText.setText(res.msg);
            }
        }
    };

    private void validNumber(String verCode){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","checkVerifyCode");
            jsonObject.put("verCode",verCode);
            jsonObject.put("sid","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        request.request(jsonObject.toString(),validCallBack,Constant.TEST_HOST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getNumberBtn:
                try {
                    sendValid();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.validPhoneNextBtn:
                if(way==2) {
                    ToastUtils.showSuccess(ac, "修改银行卡成功");
                    ac.finish();
                    EventBus.getDefault().post(new ExitEvent(ExitEvent.TYPE_CLOSE));
                }else if(way==1){

                    String validNumber = numberText.getText().toString();

                    if(validNumber==null||StringUtils.isEmpty(validNumber)){
                        flagText.setText("请输入验证码");
                        return;
                    }

                    validNumber(validNumber);

                }
                break;
        }
    }

    public void clear(){
        validPhoneNav = null;
        nextBtn= null;
        flagText = null;
        numberText = null;
        getNumberBtn= null;
    }
}
