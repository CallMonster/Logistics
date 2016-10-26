package com.zt.hackman.model;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zt.hackman.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/27.
 */
public class UpdateSecondPhoneModel implements View.OnClickListener{

    public EditText nameEdit,phoneEdit;
    public ImageView clearNameImg,clearPhoneImg;
    public RelativeLayout navLayout;
    public TextView saveBtn;
    private BaseActivity activity;

    public void findViewByIds(BaseActivity activity,String econtact,String econtactTel){

        this.activity = activity;
        clearNameImg = (ImageView)activity . findViewById(R.id.clear_name_second_btn);
        clearPhoneImg = (ImageView)activity.findViewById(R.id.clear_second_phone_btn);

        nameEdit = (EditText) activity.findViewById(R.id.user_name_second_edit);
        nameEdit.setText(econtact);
        phoneEdit = (EditText)activity.findViewById(R.id.user_second_phone_edit);
        phoneEdit.setText(econtactTel);
        navLayout = (RelativeLayout)activity.findViewById(R.id.update_second_phone_nav);
        saveBtn = (TextView)activity.findViewById(R.id.update_second_phone_save_btn);

        navLayout.setBackgroundResource(R.color.colorWhite);
        clearNameImg.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        clearPhoneImg.setOnClickListener(this);
    }

    StringCallback phoneCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(activity,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,activity);
            if(res.code>0){
                ToastUtils.showSuccess(activity,"修改成功");
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
     * 修改紧急联系人
     */
    public void updatePhone(){
        String econtact = nameEdit.getText().toString();
        String econtactTel = phoneEdit.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","editPersonInfo");
            String sid = PreferencesUtils.getSID(activity);
            if(sid==null){
                LoginUtils.showDialog(activity,"提示登录","您尚未登录,请登录后尝试");
            }
            jsonObject.put("sid",sid);
            jsonObject.put("EcontactTel",econtactTel);
            jsonObject.put("Econtact",econtact);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),phoneCallBack, Constant.TEST_HOST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear_name_second_btn:
                nameEdit.setText(null);
                break;
            case R.id.clear_second_phone_btn:
                phoneEdit.setText(null);
                break;
            case R.id.update_second_phone_save_btn:
                updatePhone();
                break;
        }
    }
}
