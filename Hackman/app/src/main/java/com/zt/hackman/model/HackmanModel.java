package com.zt.hackman.model;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zt.hackman.R;
import com.zt.hackman.activity.EditHackmanTwoActivity;
import com.zt.hackman.activity.MainActivity;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.BaseApp;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.SwitchEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.HackmanResponse;
import com.zt.hackman.response.HomeResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;
import com.zt.hackman.view.AlertLoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/19.
 */
public class HackmanModel{
    public HackmanResponse response;

    private HackRequest request;
    private int from;
    private HackmanModel(){}
    private static HackmanModel model ;

    public static HackmanModel getInstance(){
        if(model == null){
            model = new HackmanModel();
        }
        return model;
    }

    //界面一
    public RelativeLayout oneFrontBtn,oneReserveBtn,navOne,
            driving_licence_photo_btn,driving_licence_photo_btn2,
            business_certificate_photo_btn;
    BaseActivity ac;
    public EditText userNameEdit,companyNoEdit,idenNoEdit;
    private AlertLoadingDialog dialog;

    //界面二
    public CheckBox checkBox;
    public TextView watchClauseBtn;
    public RelativeLayout navTwo;

    private int isCheck=0;

    public RelativeLayout vehicleBtn ,operationLicenseBtn;
    public RelativeLayout vehicleLicenseBtn,
            vehicleLicenseBtn2;
    /**
     * 初始化司机信息1
     */
    public void initOne(BaseActivity ac, View.OnClickListener listener,int from){

        this.ac = ac;
        this.from = from;
        oneFrontBtn = (RelativeLayout)ac.findViewById(R.id.oneFrontBtn);
        oneReserveBtn = (RelativeLayout)ac.findViewById(R.id.oneReserveBtn);
        navOne=(RelativeLayout)ac.findViewById(R.id.hackman_one_layout) ;
        navOne.setBackgroundResource(R.color.colorWhite);
        driving_licence_photo_btn = (RelativeLayout) ac.findViewById(R.id.driving_licence_photo_btn);
        driving_licence_photo_btn2 = (RelativeLayout)ac.findViewById(R.id.driving_licence_photo_btn_2);
        business_certificate_photo_btn = (RelativeLayout) ac.findViewById(R.id.business_certificate_photo_btn);
        userNameEdit = (EditText)ac.findViewById(R.id.oneUserName);
        companyNoEdit = (EditText)ac.findViewById(R.id.oneEnNumberText);
        idenNoEdit = (EditText)ac.findViewById(R.id.oneIdenNumber);

        oneFrontBtn.setOnClickListener(listener);
        oneReserveBtn.setOnClickListener(listener);
        driving_licence_photo_btn.setOnClickListener(listener);
        driving_licence_photo_btn2.setOnClickListener(listener);
        business_certificate_photo_btn.setOnClickListener(listener);
        initChoose();

    }

    /**
     * 初始化界面
     */
    private void initChoose(){
        initImage(oneFrontBtn,"正面");
        initImage(oneReserveBtn,"反面");
        initImage(driving_licence_photo_btn,"(正本)");
        initImage(driving_licence_photo_btn2,"(副本)");
        initImage(business_certificate_photo_btn,null);
    }

    /**
     * 初始化营业执照
     */
    private void initImage(RelativeLayout btn,String text){
        View view =  LayoutInflater.from(ac).inflate(R.layout.layout_front_one,btn,false);
        ImageView imageView =(ImageView)view.findViewById(R.id.layout_one_img);
        TextView textView = (TextView)view.findViewById(R.id.layout_one_text);
        if(textView==null){
            textView.setVisibility(View.GONE);
        }else{
            textView.setText(text);
        }
        btn.addView(view);
    }

    /**
     * 设置驾驶证副本图片
     */
    public void changeImage(ImageItem item,int current,RelativeLayout btn){

        btn.removeAllViews();
        ImageView imageView = new ImageView(ac);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        btn.addView(imageView);
        ImageLoader.getInstance().displayImage("file://"+item.path,imageView,BaseApp.options);
        switch (current){
            case 1:
                response.idenFrontImage = new File(item.path);
                break;
            case 2:
                response.idenReserveImage = new File(item.path);
                break;
            case 3:
                response.driveImage = new File(item.path);
                break;
            case 4:
                response.driveImage2 = new File(item.path);
                break;
            case 5:
                response.doBusinessImage = new File(item.path);
                break;
            case 6:
                response.vehicleImg = new File(item.path);
                break;
            case 7:
                response.vehicleLicenseImg = new File(item.path);
                break;
            case 8:
                response.vehicleLicenseImg2 = new File(item.path);
                break;
            case 9:
                response.operationLicenseImg = new File(item.path);
                break;


        }

    }

    StringCallback carCallBack = new StringCallback(){

        @Override
        public void onError(Request request, Exception e) {
            dialog.dismiss();
            Toast.makeText(ac,"上传失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            dialog.dismiss();
            Response res = new Response(response, ac);
            if(res.code>0){
                ToastUtils.showSuccess(ac, res.msg);
                ac.finish();
                if(from==0) {
                    ac.startActivity(MainActivity.class);
                }
            }else{
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 上传车辆信息
     */
    public void uploadCar(){
        dialog = new AlertLoadingDialog(ac);
        dialog.show();
        HackmanResponse.FileParams params = new HackmanResponse.FileParams();
        params.file = response.idenFrontImage;
        params.name = "idCardFrontImg";
        ArrayList<HackmanResponse.FileParams> fileParamses
                = new ArrayList<HackmanResponse.FileParams>();
        if(params.file==null){
            Toast.makeText(ac,"请上传身份证正面照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params);
        }

        HackmanResponse.FileParams params1 = new HackmanResponse.FileParams();
        params1.file = response.idenReserveImage;
        params1.name = "idCardBackImg";
        if(params1.file==null){
            Toast.makeText(ac,"请上传身份证反面照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params1);
        }


        HackmanResponse.FileParams params2 = new HackmanResponse.FileParams();
        params2.file = response.driveImage;
        params2.name = "DLImg";
        if(params2.file==null){
            Toast.makeText(ac,"请上传驾驶证正本照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params2);
        }

        HackmanResponse.FileParams params3 = new HackmanResponse.FileParams();
        params3.file = response.driveImage2;
        params3.name = "DLImg2";
        if(params3.file==null){
            Toast.makeText(ac,"请上传驾驶证副本照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params3);
        }

        HackmanResponse.FileParams params4 = new HackmanResponse.FileParams();
        params4.file = response.doBusinessImage;
        params4.name = "professionImg";
        if(params4.file==null){
            //Toast.makeText(ac,"请上传营运证照片照片",Toast.LENGTH_SHORT).show();
            //return;
        }else{
            fileParamses.add(params4);
        }
        HackmanResponse.FileParams params5 = new HackmanResponse.FileParams();
        params5.file = response.vehicleImg;
        params5.name = "vehicleImg";
        if(params5.file==null){
            Toast.makeText(ac,"请上传车辆照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params5);
        }

        HackmanResponse.FileParams params6 = new HackmanResponse.FileParams();
        params6.file = response.vehicleLicenseImg;
        params6.name = "vehicleLicenseImg";
        if(params6.file==null){
            Toast.makeText(ac,"请上传行驶证正本照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params6);
        }

        HackmanResponse.FileParams params7 = new HackmanResponse.FileParams();
        params7.file = response.vehicleLicenseImg2;
        params7.name = "vehicleLicenseImg2";
        if(params7.file==null){
            Toast.makeText(ac,"请上传行驶证副本照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params7);
        }

        HackmanResponse.FileParams params8 = new HackmanResponse.FileParams();
        params8.file = response.operationLicenseImg;
        params8.name = "operationLicenseImg";
        if(params8.file==null){
            Toast.makeText(ac,"请上传营运证照片照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params8);
        }
        String sid = PreferencesUtils.getSID(ac);
        if(sid==null){
            LoginUtils.showDialog(ac,"登录提示","你还未登录，请先登录");
        }
        String userName = response.hackName;
        String idenNo = response.idenNo;
        if(userName==null|| StringUtils.isEmpty(userName)){
            Toast.makeText(ac,"用户姓名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(idenNo==null||StringUtils.isEmpty(idenNo)){
            Toast.makeText(ac,"身份证信息不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sid",sid);
            jsonObject.put("command","driverMessage");
            jsonObject.put("dName",userName);
            jsonObject.put("idCardNum",idenNo);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        request.requestFileAndParams(jsonObject.toString(), fileParamses, Constant.TEST_HOST, carCallBack);
    }

    /**
     * 上传认证
     */
    public void uploadAuditor() throws JSONException {

        String userName = userNameEdit.getText().toString();
        String companyNo = companyNoEdit.getText().toString();
        String idenNo = idenNoEdit.getText().toString();
        if(userName==null|| StringUtils.isEmpty(userName)){
            Toast.makeText(ac,"用户姓名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }else if(idenNo==null||StringUtils.isEmpty(idenNo)){
            Toast.makeText(ac,"身份证信息不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        response.hackName = userName;
        response.companyNo = companyNo;
        response.idenNo = idenNo;
        request = new HackRequest();
        JSONObject jsonObject = new JSONObject();
        String sid = PreferencesUtils.getSID(ac);
        if(sid==null){
            LoginUtils.showDialog(ac,"登录提示","你还未登录，请先登录");
        }
        jsonObject.put("sid",sid);
        jsonObject.put("command","driverMessage");
        jsonObject.put("dName",userName);

        jsonObject.put("idCardNum",idenNo);

        HackmanResponse.FileParams params = new HackmanResponse.FileParams();
        params.file = response.idenFrontImage;
        params.name = "idCardFrontImg";
        ArrayList<HackmanResponse.FileParams> fileParamses
                = new ArrayList<HackmanResponse.FileParams>();
        if(params.file==null){
            Toast.makeText(ac,"请上传身份证正面照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params);
        }

        HackmanResponse.FileParams params1 = new HackmanResponse.FileParams();
        params1.file = response.idenReserveImage;
        params1.name = "idCardBackImg";
        if(params1.file==null){
            Toast.makeText(ac,"请上传身份证反面照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params1);
        }


        HackmanResponse.FileParams params2 = new HackmanResponse.FileParams();
        params2.file = response.driveImage;
        params2.name = "DLImg";
        if(params2.file==null){
            Toast.makeText(ac,"请上传驾驶证正本照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params2);
        }

        HackmanResponse.FileParams params3 = new HackmanResponse.FileParams();
        params3.file = response.driveImage2;
        params3.name = "DLImg2";
        if(params3.file==null){
            Toast.makeText(ac,"请上传驾驶证副本照片",Toast.LENGTH_SHORT).show();
            return;
        }else{
            fileParamses.add(params3);
        }


        HackmanResponse.FileParams params4 = new HackmanResponse.FileParams();
        params4.file = response.doBusinessImage;
        params4.name = "professionImg";
        if(params1.file==null){
            //Toast.makeText(ac,"请上传营运证照片照片",Toast.LENGTH_SHORT).show();
            //return;
        }else{
            fileParamses.add(params4);
        }

        if(companyNo==null||StringUtils.isEmpty(companyNo)){
            ac.startActivity(EditHackmanTwoActivity.class);
        }else {
            dialog = ToastUtils.showLoading(ac, "资料提交中");
            jsonObject.put("cooperationId",companyNo);
            request.requestFileAndParams(jsonObject.toString(), fileParamses, Constant.TEST_HOST, new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    dialog.dismiss();
                    Toast.makeText(ac, "上传失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    Response res = new Response(response, ac);
                    if(res.code>0){
                        ToastUtils.showSuccess(ac, res.msg);
                        ac.finish();
                        if(from==0) {
                            ac.startActivity(MainActivity.class);
                        }
                        EventBus.getDefault().post(new SwitchEvent(SwitchEvent.TYPE_COMPLETE));
                    }else{
                        Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    /**
     * 初始化司机信息2
     * @param listener
     */
    public void initTwo(BaseActivity ac,View.OnClickListener listener){
        this.ac = ac;
        checkBox =(CheckBox) ac.findViewById(R.id.agreeCheck);
        watchClauseBtn = (TextView)ac.findViewById(R.id.watch_hackman_clause_btn);
        navTwo=(RelativeLayout)ac.findViewById(R.id.hackman_two_layout) ;
        navTwo.setBackgroundResource(R.color.colorWhite);

        checkBox.setChecked(true);
        watchClauseBtn.setOnClickListener(listener);
        vehicleBtn = (RelativeLayout) ac.findViewById(R.id.two_first_btn);
        operationLicenseBtn = (RelativeLayout) ac.findViewById(R.id.two_four_btn);

        vehicleLicenseBtn = (RelativeLayout)ac.findViewById(R.id.two_second_btn);
        vehicleLicenseBtn2 = (RelativeLayout)ac.findViewById(R.id.two_third_btn);

        vehicleBtn.setOnClickListener(listener);
        operationLicenseBtn.setOnClickListener(listener);
        vehicleLicenseBtn.setOnClickListener(listener);
        vehicleLicenseBtn2.setOnClickListener(listener);

        initImage(vehicleBtn,null);
        initImage(vehicleLicenseBtn,"(正本)");
        initImage(vehicleLicenseBtn2,"(副本)");
        initImage(operationLicenseBtn,null);

    }

    public void clearOne(){
        oneFrontBtn = null;
        oneReserveBtn = null;
        navOne = null;
        ac = null;
        driving_licence_photo_btn = null;
        business_certificate_photo_btn = null;
        userNameEdit = null;
        companyNoEdit = null;
        idenNoEdit = null;
    }

    public void clearTwo(){
        vehicleBtn = null;
        operationLicenseBtn = null;
        vehicleLicenseBtn = null;
        vehicleLicenseBtn2 = null;
        checkBox = null;
    }


}
