package com.zt.hackman.model;

import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.HomeResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */
public class ActionDetailsModel {
    public RelativeLayout navLayout;
    private BaseActivity ac;
    public ProgressActivity progressActivity;
    public TextView titleView,timeView,contentView;
    private String position;

    public void findViewByIds(BaseActivity ac){
        this.ac = ac;
        navLayout = (RelativeLayout) ac.findViewById(R.id.action_detials_nav);
        progressActivity = (ProgressActivity)ac.findViewById(R.id.action_detail_progressActivity);
        titleView = (TextView)ac.findViewById(R.id.action_details_title) ;
        timeView = (TextView)ac.findViewById(R.id.action_details_time);
        contentView = (TextView)ac.findViewById(R.id.action_details_content);

        navLayout.setBackgroundResource(R.color.colorWhite);
    }

    public void initView(String position){
        this.position = position;
        initData();
    }

    StringCallback callBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            progressActivity.showError("网络请求异常", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressActivity.showLoading();
                    initData();
                }
            });
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            String data = null;
            if(res.code>0){
                try {
                    JSONObject jsonObject = new JSONObject(res.data);
                    data =  jsonObject.getString("InfoArray");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
                }
                List<HomeResponse.LatestInfo> latestInfo = ( List<HomeResponse.LatestInfo>)
                        GsonUtils.jsonToList(data, new TypeToken< List<HomeResponse.LatestInfo>>(){}.getType());
                if(latestInfo==null){
                    progressActivity.showEmpty(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressActivity.showLoading();
                            initData();
                        }
                    });
                }else{    //数据正常显示

                    titleView .setText(latestInfo.get(0).title);
                    if(latestInfo.get(0).content!=null&& StringUtils.isEmpty(latestInfo.get(0).content)){
                        contentView.setText(Html.fromHtml(latestInfo.get(0).content));
                    }

                    timeView.setText(latestInfo.get(0).createTime);
                    progressActivity.showContent();
                }
            }else{
                progressActivity.showError(res.msg, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressActivity.showLoading();
                        initData();
                    }
                });
            }
        }
    };

    private void initData(){
        progressActivity.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","infoDetails");
            jsonObject.put("id",position);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
        }

        new HackRequest().request(jsonObject.toString(),callBack, Constant.TEST_HOST);
    }

    public void clear(){
        navLayout =null;
    }
}
