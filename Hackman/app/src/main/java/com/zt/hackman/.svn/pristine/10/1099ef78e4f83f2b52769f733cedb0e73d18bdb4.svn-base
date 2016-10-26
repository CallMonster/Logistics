package com.zt.hackman.model;

import android.app.Activity;
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
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/22.
 */
public class IdeaModel implements View.OnClickListener{
    public EditText editText;
    public RelativeLayout ideaNavLayout;
    public TextView ideaCommitBtn;
    private BaseActivity ac;

    public void findViewByIds(BaseActivity activity){
        this.ac = activity;
        editText = (EditText)activity.findViewById(R.id.idea_edit);
        ideaNavLayout = (RelativeLayout)activity.findViewById(R.id.idea_nav_layout);

        ideaNavLayout.setBackgroundResource(R.color.colorWhite);
        ideaCommitBtn = (TextView)activity.findViewById(R.id.idea_commit_btn);
        ideaCommitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idea_commit_btn:
                commitQuestion();
                break;
        }
    }

    StringCallback questionCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(ac,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                ToastUtils.showSuccess(ac,res.msg);
                ac.finish();
            }else{
                if(res.code==-10){
                    LoginUtils.showDialog(ac,"登录提示","会话已过期，请重新登录");
                }else{
                    Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /**
     * 提交建议
     */
    public void commitQuestion(){
        String idea = editText.getText().toString();
        if(idea==null|| StringUtils.isEmpty(idea)){
            Toast.makeText(ac,"请输入建议内容",Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","feedMsg");
            String sid = PreferencesUtils.getSID(ac);
            if(sid==null){
                LoginUtils.showDialog(ac,"登录提示","你尚未登录，请登录后继续");
            }
            jsonObject.put("sid",sid);
            jsonObject.put("userType","1");
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),questionCallBack, Constant.TEST_HOST);
    }
}
