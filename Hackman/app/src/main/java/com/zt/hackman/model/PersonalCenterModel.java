package com.zt.hackman.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.ApproveInfoActivity;
import com.zt.hackman.activity.ApproveStateActivity;
import com.zt.hackman.activity.EditHackmanOneActivity;
import com.zt.hackman.activity.UpdateAddressActvitiy;
import com.zt.hackman.activity.UpdateNameActivity;
import com.zt.hackman.activity.UpdatePhoneActivity;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.BaseApp;
import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.loader.PicassoImageLoader;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.HackmanResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.response.UserResponse;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;
import com.zt.hackman.view.AlertLoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class PersonalCenterModel implements View.OnClickListener{
    public RelativeLayout userHeadLayout,userNameLayout,
            userPhoneLayout,userSecondPhoneLayout,
            userAddressLayout,userNav,userStateLayout;
    public ImageView userImage,userStateImage;
    public TextView userNameText,userPhoneText
            ,userSecondPhoneText,userAddressText,userStateText;
    private ProgressActivity personCenter_progressActivity;
    private UserResponse userResponse;
    private int status;
    private BaseActivity ac;
    AlertLoadingDialog dialog;

    StringCallback uploadCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            dialog.dismiss();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            dialog.dismiss();
            if(res.code>0){
                ToastUtils.showSuccess(ac,res.msg);
            }else{
                Toast.makeText(ac,"上传头像失败",Toast.LENGTH_SHORT).show();
            }
        }
    };


    /**
     * 上传
     * @param face
     */
    public  void uplaod(File face){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","upServerHeadPic");
            String sid = PreferencesUtils.getSID(ac);
            jsonObject.put("sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
        }

        HackmanResponse.FileParams params = new HackmanResponse.FileParams();
        params.name = "face";
        params.file = face;
        ArrayList<HackmanResponse.FileParams> fileParamses = new ArrayList<HackmanResponse.FileParams>();
        fileParamses.add(params);
        dialog = ToastUtils.showLoading(ac,"头像上传中...");
        new HackRequest().requestFileAndParams(jsonObject.toString(),fileParamses
                ,Constant.TEST_HOST,uploadCallBack);

    }

    public void findViewByIds(BaseActivity activity){
        this.ac = activity;
        userHeadLayout = (RelativeLayout)activity.findViewById(R.id.user_head_layout);
        userNameLayout = (RelativeLayout)activity.findViewById(R.id.user_name_layout);
        userPhoneLayout = (RelativeLayout)activity.findViewById(R.id.user_phone_layout);
        userSecondPhoneLayout = (RelativeLayout)activity.findViewById(R.id.user_second_phone_layout);
        userAddressLayout = (RelativeLayout)activity.findViewById(R.id.user_address_layout);
        userNameText = (TextView)activity.findViewById(R.id.user_name_text);
        userPhoneText = (TextView)activity.findViewById(R.id.user_phone_text);
        userSecondPhoneText = (TextView)activity.findViewById(R.id.user_second_phone_text);
        userAddressText = (TextView)activity.findViewById(R.id.user_address_text);
        userStateText = (TextView)activity.findViewById(R.id.user_state_text);
        userImage = (ImageView)activity.findViewById(R.id.user_head_img);
        userStateImage = (ImageView)activity.findViewById(R.id.user_state_img);
        userStateLayout = (RelativeLayout) activity.findViewById(R.id.hackman_state_layout);

        userNav = (RelativeLayout)activity.findViewById(R.id.personal_center_activity_nav);
        userNav.setBackgroundResource(R.color.colorWhite);
        personCenter_progressActivity = (ProgressActivity)activity
                .findViewById(R.id.personCenter_progressActivity);

        userHeadLayout.setOnClickListener(this);
        userNameLayout.setOnClickListener(this);
        userPhoneLayout.setOnClickListener(this);
        userSecondPhoneLayout.setOnClickListener(this);
        userAddressLayout.setOnClickListener(this);
        userStateLayout.setOnClickListener(this);
    }


    private void chooseImg(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(80);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(80);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(100);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(100);//保存文件的高度。单位像素

        Intent intent = new Intent(ac, ImageGridActivity.class);
        ac.startActivityForResult(intent, Constant.INTNENTS.IMAGE_PICKER);
    }

    public void init(){
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView(String statusStr){
        ImageLoader.getInstance().displayImage(userResponse.face,userImage, BaseApp.circleOptions);
        if(statusStr!=null&&! StringUtils.isEmpty(statusStr)){
            status = Integer.parseInt(statusStr.trim());
            switch (status){
                case -1:
                    userStateImage.setBackgroundResource(R.mipmap.state_failed);
                    userStateText.setText("认证失败");
                    break;
                case 0:
                    userStateImage.setBackgroundResource(R.mipmap.state_no_approve);
                    userStateText.setText("尚未认证");
                    break;

                case 1:
                    userStateImage.setBackgroundResource(R.mipmap.state_approving);
                    userStateText.setText("认证中");
                    break;
                case 2:
                    userStateImage.setBackgroundResource(R.mipmap.state_already_approve);
                    userStateText.setText("已认证");
                    break;
            }
        }
        userNameText.setText(userResponse.dName);
        userPhoneText.setText(userResponse.tel);
        if(userResponse.Econtact!=null||userResponse.EcontactTel!=null) {
            userSecondPhoneText.setText(userResponse.Econtact + " " + userResponse.EcontactTel);
        }
        userAddressText.setText(userResponse.addr);
        personCenter_progressActivity.showContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_head_layout:
                chooseImg();
                break;

            case R.id.user_name_layout:
                if(status!=-1||status!=0){
                    Toast.makeText(ac,"实名制状态的名称不能更改",Toast.LENGTH_LONG).show();
                }else {
                    ac.startActivity(UpdateNameActivity.class);
                }
                break;

            case R.id.user_address_layout:
                ac.startActivity(UpdateAddressActvitiy.class);
                break;
            case R.id.user_phone_layout:
                Toast.makeText(ac,"手机号码不能修改",Toast.LENGTH_LONG).show();
                break;

            case R.id.user_second_phone_layout:
                Bundle bundle = new Bundle();
                bundle.putString("econtactTel",userResponse.EcontactTel);
                bundle.putString("econtact",userResponse.Econtact);
                ac.startActivity(UpdatePhoneActivity.class,bundle);
                break;

            case R.id.hackman_state_layout:
                Bundle extra = new Bundle();
                switch (status){
                    case -1:
                        extra.putInt("certification_state",status);
                        extra.putInt("from",1);
                        ac.startActivity(EditHackmanOneActivity.class,extra);
                        break;
                    case 0:
                        extra.putInt("certification_state",status);
                        extra.putInt("from",1);
                        ac.startActivity(EditHackmanOneActivity.class,extra);
                        break;

                    case 1:
                        extra.putInt("certification_state",status);
                        ac.startActivity(ApproveStateActivity.class,extra);
                        break;
                    case 2:
                        extra.putInt("certification_state",status);
                        ac.startActivity(ApproveInfoActivity.class,extra);
                        break;
                }

                break;
        }
    }

    StringCallback callBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            personCenter_progressActivity.showError("网络请求异常",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                String content = null;
                String status = null;
                try {
                    JSONObject jsonObject = new JSONObject(res.data);
                    content = jsonObject.getString("dInfo");
                    status = jsonObject.getString("reviewStatus");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
                }
                userResponse = (UserResponse) GsonUtils.jsonToBean(content,UserResponse.class);
                if(userResponse==null){
                    personCenter_progressActivity.showEmpty(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
                }else{
                    initView(status);
                }

            }else{
                if(res.code==-10){
                    personCenter_progressActivity.showError(res.msg,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LoginUtils.toLogin(ac);
                                }
                            });
                }else{
                    personCenter_progressActivity.showError(res.msg,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initData();
                                }
                            });
                }
            }
        }
    };

    /**
     * 初始化数据
     */
    private void initData(){
        personCenter_progressActivity.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","certificationState");
            String sid = PreferencesUtils.getSID(ac);
            if(sid==null|| StringUtils.isEmpty(sid)){
                LoginUtils.showDialog(ac,"提示登录","你还没有登录");
            }
            jsonObject.put("sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }

        new HackRequest().request(jsonObject.toString(),callBack,Constant.TEST_HOST);
    }
}
