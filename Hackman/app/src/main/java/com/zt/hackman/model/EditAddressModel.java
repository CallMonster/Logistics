package com.zt.hackman.model;

import android.app.Activity;
import android.view.Gravity;
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
import com.zt.hackman.event.RefreshEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;
import com.zt.hackman.view.ChooseAreaWindow;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class EditAddressModel implements View.OnClickListener{
    public RelativeLayout navLayout,areaChooseLayout;
    public EditText detailAdressText,codeText;
    public TextView saveBtn,areaText;
    private BaseActivity activity;
    private ArrayList<String> provinces,areas,citys;
    private String area;
    private String detailAddr;
    private ChooseAreaWindow chooseAreaWindow;

    public void findViewByIds(BaseActivity activity){
        this.activity = activity;
        navLayout = (RelativeLayout)activity.findViewById(R.id.address_nav_layout);
        areaChooseLayout = (RelativeLayout)activity.findViewById(R.id.choose_area_layout);
        detailAdressText = (EditText)activity.findViewById(R.id.address_content_text);
        codeText = (EditText)activity.findViewById(R.id.address_code);
        saveBtn = (TextView)activity.findViewById(R.id.update_address_save_btn);
        areaText = (TextView)activity.findViewById(R.id.area_text);
        areaChooseLayout.setOnClickListener(this);

        navLayout.setBackgroundResource(R.color.colorWhite);
        saveBtn.setOnClickListener(this);
    }

    public void initView(ArrayList<String> provinces,ArrayList<String> citys,ArrayList<String> areas){
        this.provinces = provinces;
        this.areas = areas;
        this.citys = citys;
    }

    public void setArea(String area){
        this.area = area;
        areaText.setText(area);
    }

    /**
     * 地址信息回调
     */
    StringCallback addrCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(activity,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,activity);
            if(res.code>0){
                ToastUtils.showSuccess(activity,"修改地址成功");
                EventBus.getDefault().post(new RefreshEvent
                        (RefreshEvent.TYPE_REFRESH_PERSONAL_CENTER_ACTIVITY));
                activity.finish();
            }else{
                if(res.code == -10){
                    LoginUtils.showDialog(activity,"提示登录","会话过期，请重新登录");
                }else{
                    Toast.makeText(activity,res.msg,Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /**
     * 修改地址
     */
    private void updateAddr(){
        String detailAddr = detailAdressText.getText().toString();
        if (detailAddr==null|| StringUtils.isEmpty(detailAddr)){
            Toast.makeText(activity,"详细地址不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","editPersonInfo");
            String sid = PreferencesUtils.getSID(activity);
            if(sid==null){
                LoginUtils.showDialog(activity,"提示登录","您尚未登录,请登录后尝试");
            }
            jsonObject.put("sid",sid);
            jsonObject.put("addr",area+detailAddr);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),addrCallBack, Constant.TEST_HOST);
    }

    public void clear (){
        navLayout = null;
        areaChooseLayout = null;
        detailAdressText = null;
        codeText = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_area_layout:
                activity.hideKeyboard();
                chooseAreaWindow =new ChooseAreaWindow(
                        activity,provinces,citys,areas);
                chooseAreaWindow.showAtLocation(v, Gravity.NO_GRAVITY,0,0);
                break;
            case R.id.update_address_save_btn:
                updateAddr();
                break;
        }
    }
}
