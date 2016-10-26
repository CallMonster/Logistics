package com.qxsx.chaersi.driverclient.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.result.CertifyStep1Result;
import com.qxsx.chaersi.driverclient.index.MainActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tj.opensrc.selectphoto.SystemAlbumPickerActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class CertifyStep2_Activity extends BaseActivity {
    private String TAG="CertifyStep2_Activity";
    public static final int CAR_REQUEST_CODE = 1;
    public static final int DRIVERCARD_REQUEST_CODE = 2;
    public static final int DRIVERCARD_OTHER_REQUEST_CODE = 3;
    public static final int OPERATION_REQUEST_CODE = 4;

    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.rightView) TextView rightView;
    @BindView(R.id.rightBtn) View rightBtn;
    @BindView(R.id.addDriverBtn) ImageView addDriverBtn;
    @BindView(R.id.driverLayout) LinearLayout driverBtn;
    @BindView(R.id.negativeLayout) LinearLayout driverOtherBtn;
    @BindView(R.id.busiCardBtn) ImageView busiCardBtn;
    @BindView(R.id.checkBtn) ImageView checkBtn;
    @BindView(R.id.selectFile) TextView selectFile;

    @BindView(R.id.positivePic) ImageView positivePic;
    @BindView(R.id.negativePic) ImageView negativePic;
    @BindView(R.id.p_pic) ImageView p_picView;
    @BindView(R.id.p_text) TextView p_textView;
    @BindView(R.id.n_pic) ImageView n_picView;
    @BindView(R.id.n_text) TextView n_textView;

    private boolean isCheck=true;
    private String pathCar="";//车辆照片
    private String pathDriverCard="";//行驶证照片
    private String pathAuxDriverCard="";//行驶证副本
    private String pathOperation="";//运营证照片
    private Picasso picasso;

    private String path_dName="";
    private String path_idCardNum="";
    private String path_idCardFrontImg="";
    private String path_idCardBackImg="";
    private String path_DLImg="";
    private String path_DLImg2="";
    private String path_professionImg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certify_step2_);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        path_idCardFrontImg=intent.getStringExtra("pathPositive");
        path_idCardBackImg=intent.getStringExtra("pathNegative");
        path_DLImg=intent.getStringExtra("pathDriver");
        path_DLImg2=intent.getStringExtra("pathOtherDriver");
        path_dName=intent.getStringExtra("nameStr");
        path_idCardNum=intent.getStringExtra("idStr");
        path_professionImg=intent.getStringExtra("pathCompany");

        title.setText("车辆信息（2/2）");
        rightView.setText("提交审核");
        rightView.setVisibility(View.VISIBLE);
        rightBtn.setVisibility(View.VISIBLE);

        picasso= BaseApplication.getInstance().built;
    }

    @OnClick({R.id.leftBtn, R.id.rightBtn, R.id.addDriverBtn, R.id.driverLayout, R.id.negativeLayout, R.id.busiCardBtn, R.id.checkBtn, R.id.selectFile})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                finish();
                overridePendingTransition(R.anim.in_from_left,R.anim.out_from_right);
                break;
            case R.id.rightBtn:
                if(TextUtils.isEmpty(pathCar)||TextUtils.isEmpty(pathDriverCard)
                        ||TextUtils.isEmpty(pathDriverCard)||!isCheck){
                    showTips("请完善信息后再试");
                }else{
                    submitData();
                }
                break;
            case R.id.addDriverBtn:
                Intent pIntent = new Intent(this, SystemAlbumPickerActivity.class);
                pIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(pIntent, CAR_REQUEST_CODE);
                break;
            case R.id.driverLayout:
                Intent driverIntent = new Intent(this, SystemAlbumPickerActivity.class);
                driverIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(driverIntent, DRIVERCARD_REQUEST_CODE);
                break;
            case R.id.negativeLayout:
                Intent driverOtherIntent = new Intent(this, SystemAlbumPickerActivity.class);
                driverOtherIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(driverOtherIntent, DRIVERCARD_OTHER_REQUEST_CODE);
                break;
            case R.id.busiCardBtn:
                Intent busiCardIntent = new Intent(this, SystemAlbumPickerActivity.class);
                busiCardIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(busiCardIntent, OPERATION_REQUEST_CODE);
                break;
            case R.id.checkBtn:
                if(isCheck){
                    isCheck=false;
                    checkBtn.setImageResource(R.mipmap.checkbox_normal);
                }else{
                    isCheck=true;
                    checkBtn.setImageResource(R.mipmap.checkbox_selected);
                }
                break;
            case R.id.selectFile:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAR_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            pathCar=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathCar))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(addDriverBtn, new Callback() {
                        @Override
                        public void onSuccess() {
                            addDriverBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                addDriverBtn.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                addDriverBtn.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
        }else if(requestCode == DRIVERCARD_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            pathDriverCard=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathDriverCard))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(positivePic, new Callback() {
                        @Override
                        public void onSuccess() {
                            p_picView.setVisibility(View.GONE);
                            p_textView.setVisibility(View.GONE);
                            positivePic.setVisibility(View.VISIBLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                driverBtn.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                driverBtn.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
        }else if(requestCode == DRIVERCARD_OTHER_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            pathAuxDriverCard=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathAuxDriverCard))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(negativePic, new Callback() {
                        @Override
                        public void onSuccess() {
                            n_picView.setVisibility(View.GONE);
                            n_textView.setVisibility(View.GONE);
                            negativePic.setVisibility(View.VISIBLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                driverOtherBtn.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                driverOtherBtn.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
        }else if(requestCode == OPERATION_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            pathOperation=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathOperation))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(busiCardBtn, new Callback() {
                        @Override
                        public void onSuccess() {
                            busiCardBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                busiCardBtn.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                busiCardBtn.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
        }
    }

    /**
     * 提交审核
     */
    private void submitData(){
        File file_idCardFrontImg=new File(path_idCardFrontImg);
        File file_idCardBackImg=new File(path_idCardBackImg);
        File file_DLImg=new File(path_DLImg);
        File file_DLImg2=new File(path_DLImg2);

        File file_vehicleImg=new File(pathCar);
        File file_vehicleLicenseImg=new File(pathDriverCard);
        File file_vehicleLicenseImg2=new File(pathAuxDriverCard);

        HashMap<String,String> params=new HashMap<>();
        params.put("command","driverMessage");
        params.put("dName",path_dName);
        params.put("idCardNum",path_idCardNum);
        params.put("sid",BaseApplication.getInstance().loginsid);

        PostFormBuilder builder= OkHttpUtils.post();
        if(!TextUtils.isEmpty(path_professionImg)){
            File file_professionImg=new File(path_professionImg);
            builder.addFile("professionImg",file_professionImg.getName(),file_professionImg);
        }
        if(!TextUtils.isEmpty(pathOperation)){
            File file_operationLicenseImg=new File(pathOperation);
            builder.addFile("professionImg",file_operationLicenseImg.getName(),file_operationLicenseImg);
        }
        builder.addFile("idCardFrontImg", file_idCardFrontImg.getName(), file_idCardFrontImg)
                .addFile("idCardBackImg", file_idCardBackImg.getName(), file_idCardBackImg)
                .addFile("DLImg", file_DLImg.getName(), file_DLImg)
                .addFile("DLImg2", file_DLImg2.getName(), file_DLImg2)
                .addFile("vehicleImg", file_vehicleImg.getName(), file_vehicleImg)
                .addFile("vehicleLicenseImg", file_vehicleLicenseImg.getName(), file_vehicleLicenseImg)
                .addFile("vehicleLicenseImg2", file_vehicleLicenseImg2.getName(), file_vehicleLicenseImg2)
                .addParams("data", BaseApplication.gson.toJson(params))
                .url(InitDesign.BASE_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG,"err:"+e);
                        showTips("网络连接不畅，请稍后再试");
                        hideProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG,response);
                        CertifyStep1Result result=BaseApplication.gson.fromJson(response,CertifyStep1Result.class);
                        if("1".equals(result.getCode())){
                            hideProgressDialog();
                            Intent intent=new Intent(CertifyStep2_Activity.this, MainActivity.class);
                            startActivity(intent);
                            CertifyStep2_Activity.this.finish();
                            overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                        }else{
                            showTips(result.getMsg());
                            hideProgressDialog();
                        }
                    }
                });


    }

}
