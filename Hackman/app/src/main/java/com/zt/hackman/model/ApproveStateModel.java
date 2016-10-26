package com.zt.hackman.model;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.ApproveInfoActivity;
import com.zt.hackman.activity.ApproveStateActivity;
import com.zt.hackman.activity.EditHackmanOneActivity;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/13.
 */
public class ApproveStateModel {
    private BaseActivity ac;
    private ProgressActivity progressActivity;
    private CheckBox checkBox1,checkBox2,checkBox3;
    private View lineOne,lineTwo;

    public void findViewByIds(BaseActivity ac){
        this.ac = ac;
        progressActivity = (ProgressActivity)ac.findViewById(R.id.approve_state_progressActivity);
        checkBox1 = (CheckBox)ac.findViewById(R.id.state_one_checkbox);
        checkBox2 = (CheckBox)ac.findViewById(R.id.state_two_checkbox);
        checkBox3 = (CheckBox)ac.findViewById(R.id.state_three_checkbox);
        lineOne = (View)ac.findViewById(R.id.line_one_view);
        lineTwo = (View)ac.findViewById(R.id.line_two_view);
    }



    public void initView(int status){
        //progressActivity.showLoading();
        //initData();
        switch (status){
            case -1:
                checkBox1.setChecked(true);
                break;
            case 0:
                checkBox1.setChecked(true);
                lineOne.setBackgroundResource(R.color.tab_seleted);
                break;

            case 1:
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
                lineOne.setBackgroundResource(R.color.tab_seleted);
                break;
            case 2:
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
                checkBox3.setChecked(true);
                lineOne.setBackgroundResource(R.color.tab_seleted);
                lineTwo.setBackgroundResource(R.color.tab_seleted);
                break;
        }

    }


//    private void initData(){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("command","certificationState");
//            String sid = PreferencesUtils.getSID(ac);
//            if(sid==null|| StringUtils.isEmpty(sid)){
//                LoginUtils.showDialog(ac,"提示登录","你还没有登录");
//            }
//            jsonObject.put("sid",sid);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
//        }
//        new HackRequest().request(jsonObject.toString(),stateCallBack, Constant.TEST_HOST);
//    }
}
