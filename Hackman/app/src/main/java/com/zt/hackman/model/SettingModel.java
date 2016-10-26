package com.zt.hackman.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.LoginActivity;
import com.zt.hackman.activity.UpdatePhoneActivity;
import com.zt.hackman.activity.ValidPhoneActivity;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.SetPwdEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/29.
 */
public class SettingModel implements View.OnClickListener{

    public RelativeLayout updatePhoneLayout,navLayout,checkUpdateLayout,updatePwdLayout;

    public ImageView switchBtn;
    public TextView logoutBtn;
    private BaseActivity activity;

    public void findViewByIds(BaseActivity activity){
        this.activity = activity;
        updatePhoneLayout = (RelativeLayout)activity.findViewById(R.id.setting_update_phone_layout);
        navLayout = (RelativeLayout)activity.findViewById(R.id.setting_nav_layout);
        navLayout.setBackgroundResource(R.color.colorWhite);
        checkUpdateLayout = (RelativeLayout)activity.findViewById(R.id.setting_check_update_layout);
        updatePwdLayout = (RelativeLayout)activity.findViewById(R.id.setting_update_pwd_layout);

        switchBtn = (ImageView)activity.findViewById(R.id.setting_switch_btn);
        logoutBtn = (TextView)activity.findViewById(R.id.setting_loginout_btn);

        updatePhoneLayout.setOnClickListener(this);
        checkUpdateLayout.setOnClickListener(this);
        updatePwdLayout.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    public void initView(){

    }

    StringCallback logoutCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(activity,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,activity);
            if(res.code>0){
                PreferencesUtils.putString(activity, Constant.PREFERENCE_KEY.KEY_SID,null);
                activity.startActivity(LoginActivity.class);
                activity.finish();
                EventBus.getDefault().post(new SetPwdEvent(SetPwdEvent.TYPE_BACK));
            }else{
                Toast.makeText(activity,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void logout(){
        //{"command":"logout","sid":"22"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","logout");
            String sid = PreferencesUtils.getSID(activity);
            if(sid==null){
                Toast.makeText(activity,"您尚未登录，无需退出",Toast.LENGTH_SHORT).show();
            }
            jsonObject.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),logoutCallBack,Constant.TEST_HOST);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.setting_check_update_layout:

                break;

            case R.id.setting_update_phone_layout:
                Intent intent = new Intent(activity,ValidPhoneActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.INTNENTS.INTENT_VALID_PHONE_WAY,1);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                break;

            case R.id.setting_switch_btn:

                break;

            case R.id.setting_loginout_btn:
                logout();
                break;

            case R.id.setting_update_pwd_layout:

                break;
        }
    }
}
