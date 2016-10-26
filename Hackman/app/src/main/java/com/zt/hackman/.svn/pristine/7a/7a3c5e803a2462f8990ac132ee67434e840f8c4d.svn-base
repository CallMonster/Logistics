package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.MessageCenterAcitivty;
import com.zt.hackman.activity.PersonalCenterActivity;
import com.zt.hackman.base.BaseApp;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.customshape.widget.CircleImageView;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.MyListener;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.response.UserResponse;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/14.
 */
public class MyModel implements View.OnClickListener,LeftListener {
    public RelativeLayout myOrderLayout,myFriendsLayout,
            myAboutLayout,myContractLayout,myIdeaLayout,mySettingLayout,myWalletLayout;
    private CircleImageView headImageView;
    private ImageView headStateImageView;
    private TextView userNameText,plateNumberText,my_comment;
    private MyListener listener;
    private Activity ac;
    private UserResponse userResponse;
    private NavModel navModel;

    /**
     *
     * @param view
     * @param ac
     */
    public void findViewByIds(View view, Activity ac){
        this.ac = ac;
        navModel = new NavModel(view);
        headImageView = (CircleImageView)view.findViewById(R.id.my_head_img) ;
        headStateImageView = (ImageView)view.findViewById(R.id.head_flag);
        userNameText = (TextView)view.findViewById(R.id.userName);
        plateNumberText = (TextView)view.findViewById(R.id.my_plate_number);
        my_comment = (TextView)view.findViewById(R.id.my_comment);

        myOrderLayout = (RelativeLayout)view.findViewById(R.id.my_order_relative);
        myFriendsLayout = (RelativeLayout)view.findViewById(R.id.my_friends_relative);
        myAboutLayout = (RelativeLayout)view.findViewById(R.id.my_about_relative);
        myContractLayout = (RelativeLayout)view.findViewById(R.id.my_contract_relatvie);
        myIdeaLayout = (RelativeLayout)view.findViewById(R.id.my_idea_relative);
        mySettingLayout = (RelativeLayout)view.findViewById(R.id.my_setting_relative);
        myWalletLayout = (RelativeLayout)view.findViewById(R.id.my_wallet_relative);
        headImageView.setOnClickListener(this);
    }

    public void setListener(MyListener myListener){
        this.listener = myListener;
        myOrderLayout.setOnClickListener(this);
        myFriendsLayout.setOnClickListener(this);
        myAboutLayout.setOnClickListener(this);
        myContractLayout.setOnClickListener(this);
        myIdeaLayout.setOnClickListener(this);
        mySettingLayout.setOnClickListener(this);
        myWalletLayout.setOnClickListener(this);
    }

    public void init(){
        initData();
    }


    StringCallback callBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(ac,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                userResponse = (UserResponse) GsonUtils.jsonToBean(res.data,UserResponse.class);
                if(userResponse==null){
                    Toast.makeText(ac,"相应数据为空",Toast.LENGTH_SHORT).show();
                }else{
                    initView();
                }

            }else{
                if(res.code==-10){
                    LoginUtils.showDialog(ac,"登录提示",res.msg);
                }else{
                    Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void initView(){
        ImageLoader.getInstance().displayImage(userResponse.face,headImageView, BaseApp.circleOptions);
        if(userResponse.reviewStatus!=null&&! StringUtils.isEmpty(userResponse.reviewStatus)){
            int status = Integer.parseInt(userResponse.reviewStatus.trim());
                switch (status){
                    case -1:
                        headStateImageView.setBackgroundResource(R.mipmap.state_failed);
                        break;
                    case 0:
                        headStateImageView.setBackgroundResource(R.mipmap.state_no_approve);
                        break;

                    case 1:
                        headStateImageView.setBackgroundResource(R.mipmap.state_approving);
                        break;
                    case 2:
                        headStateImageView.setBackgroundResource(R.mipmap.state_already_approve);
                        break;
            }
        }
        userNameText.setText(userResponse.dName);
        my_comment.setText(userResponse.favorableRate);
        plateNumberText.setText(userResponse.driverId);
        if("1".equals(userResponse.hasNewMsg)){
            navModel.setLeftBtn(R.mipmap.conversation);
        }else if("0".equals(userResponse.hasNewMsg)){
            navModel.setLeftBtn(R.mipmap.navi_message);
        }
        navModel.setLeftListener(this);
        //ImageLoader.getInstance().displayImage();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","driverPersonCenter");
            String sid = PreferencesUtils.getSID(ac);
            jsonObject.put("sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }

        new HackRequest().request(jsonObject.toString(),callBack, Constant.TEST_HOST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_order_relative:
                listener.orderClick();
                break;
            case R.id.my_contract_relatvie:
                listener.contractClick();
                break;
            case R.id.my_about_relative:
                listener.aboutClick();
                break;
            case R.id.my_idea_relative:
                listener.ideaClick();
                break;
            case R.id.my_setting_relative:
                listener.settingClick();
                break;
            case R.id.my_friends_relative:
                listener.friendsClick();
                break;
            case R.id.my_wallet_relative:
                listener.walletClick();
                break;
            case R.id.my_head_img:
                ac.startActivity(new Intent(ac, PersonalCenterActivity.class));
                break;
        }
    }

    @Override
    public void leftClick() {
        ac.startActivity(new Intent(ac,MessageCenterAcitivty.class));
    }
}
