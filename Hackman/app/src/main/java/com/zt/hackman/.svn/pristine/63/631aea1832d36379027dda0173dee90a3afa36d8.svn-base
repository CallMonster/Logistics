package com.zt.hackman.model;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.adapter.OrderFragmentAdapter;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.RefreshEvent;
import com.zt.hackman.event.SwitchEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.ToastUtils;
import com.zt.hackman.view.SmartTabLayout;
import com.zt.hackman.viewpagerindicator.TabPageIndicator;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * Created by Administrator on 2016/9/21.
 */
public class OrderModel implements View.OnClickListener{
    public RelativeLayout titleBar;
    public SmartTabLayout tablayout;
    public ViewPager viewPager;
    public ImageView unLineButton;
    private int currentState;
    private Activity ac;
    public ProgressActivity progressActivity;

    public void findViewByIds(Activity ac, View view){
        this.ac = ac;
        titleBar = (RelativeLayout)view.findViewById(R.id.fragment_order_nav);
        tablayout = (SmartTabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager)view. findViewById(R.id.orderViewPager);
        unLineButton = (ImageView) view.findViewById(R.id.logout_img);
        progressActivity = (ProgressActivity)view.findViewById(R.id.order_progressActivity);

        titleBar.setBackgroundResource(R.color.colorWhite);
        unLineButton.setOnClickListener(this);
        switchLineState(0);

    }



    /**
     * 切换上线状态
     */
    public void switchLineState(int currentState){
        this.currentState=currentState;
        if(currentState==0){
            unLineButton.setBackgroundResource(R.mipmap.btn_online);
        }else if(currentState==1){
            unLineButton.setBackgroundResource(R.mipmap.btn_logout);
        }else{
            unLineButton.setVisibility(View.GONE);
        }
    }


    /**
     * 对象置空
     */
    public void clear(){
        titleBar = null;
        tablayout = null;
        viewPager = null;
    }
    StringCallback logoutCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(ac,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                EventBus.getDefault().post(new SwitchEvent(SwitchEvent.TYPE_SWITCH_LOGOUT));
            }else{
                if(res.code==-10){
                    Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
                    LoginUtils.toLogin(ac);
                }else {
                    Toast.makeText(ac, res.msg, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /**
     * 上线
     */
    private void onLine(){
        EventBus.getDefault().post(new SwitchEvent(SwitchEvent.TYPE_SWITCH_CODE));
    }


    /**
     * 下线
     */
    private void logout(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","getOff");
            String sid = PreferencesUtils.getSID(ac);
            if(sid==null){
                LoginUtils.showDialog(ac,"提示登录","您尚未登录，请登陆后继续");
            }
            jsonObject.put("sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HackRequest().request(jsonObject.toString(),logoutCallBack, Constant.TEST_HOST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_img:
                if(currentState==0){
                    onLine();
                }else if(currentState==1){
                    logout();
                }else{
                    Toast.makeText(ac,"上下线状态有误，咱不能进行以下操作",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
