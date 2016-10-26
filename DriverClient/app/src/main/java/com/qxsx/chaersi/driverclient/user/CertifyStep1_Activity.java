package com.qxsx.chaersi.driverclient.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.CertifyStep1;
import com.qxsx.chaersi.driverclient.entry.result.CertifyStep1Result;
import com.qxsx.chaersi.driverclient.index.MainActivity;
import com.qxsx.chaersi.driverclient.utils.SizeFormat;
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

public class CertifyStep1_Activity extends BaseActivity {
    private String TAG="CertifyStep1_Activity";
    public static final int POSITIVE_REQUEST_CODE = 1;
    public static final int NEGATIVE_REQUEST_CODE = 2;
    public static final int DRIVER_REQUEST_CODE = 3;
    public static final int OTHERDRIVER_REQUEST_CODE = 31;
    public static final int COMPANY_REQUEST_CODE = 4;

    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.rightView) TextView rightView;
    @BindView(R.id.rightBtn_img) ImageView rightBtnImg;
    @BindView(R.id.rightBtn) View rightBtn;
    @BindView(R.id.nameEdit) EditText nameEdit;
    @BindView(R.id.idEdit) EditText idEdit;
    @BindView(R.id.positiveLayout) LinearLayout positiveLayout;
    @BindView(R.id.negativeLayout) LinearLayout negativeLayout;
    @BindView(R.id.addDriverBtn) ImageView addDriverBtn;
    @BindView(R.id.companyIdEdit) EditText companyIdEdit;
    @BindView(R.id.addCompanyBtn) ImageView addCompanyBtn;

    @BindView(R.id.negativePic) ImageView negativePic;
    @BindView(R.id.positivePic) ImageView positivePic;
    @BindView(R.id.p_pic) ImageView p_picView;
    @BindView(R.id.p_text) TextView p_textView;
    @BindView(R.id.n_pic) ImageView n_picView;
    @BindView(R.id.n_text) TextView n_textView;
    @BindView(R.id.addOtherDriverBtn) ImageView addOtherDriverBtn;

    private boolean isSubmit=false;//是否提交审核
    private String pathPositive="";//身份证正面照片路径
    private String pathNegative="";//身份证背面照片路径
    private String pathDriver="";//驾驶证照片
    private String pathOtherDriver="";//驾驶证副照片
    private String pathCompany="";//从业资格证照片

    private Picasso picasso;
    private String nameStr="",idStr="",companyIdStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certify_step1_);
        ButterKnife.bind(this);
        title.setText("驾驶员信息（1/2）");
        rightView.setText("下一步");
        rightView.setVisibility(View.VISIBLE);
        rightBtn.setVisibility(View.VISIBLE);

        picasso= BaseApplication.getInstance().built;
        companyIdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    title.setText("驾驶员信息");
                    rightView.setText("提交审核");
                    isSubmit=true;
                }else{
                    title.setText("驾驶员信息（1/2）");
                    rightView.setText("下一步");
                    isSubmit=false;
                }
            }
        });

    }

    @OnClick({R.id.leftBtn, R.id.rightBtn, R.id.positiveLayout, R.id.negativeLayout, R.id.addDriverBtn, R.id.addCompanyBtn,R.id.addOtherDriverBtn})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.in_from_left,R.anim.out_from_right);
                break;
            case R.id.rightBtn:
                nameStr = nameEdit.getText().toString();
                idStr = idEdit.getText().toString();
                companyIdStr = companyIdEdit.getText().toString();
                if (TextUtils.isEmpty(pathPositive) || TextUtils.isEmpty(pathNegative)
                        || TextUtils.isEmpty(pathDriver) || TextUtils.isEmpty(pathOtherDriver)
                        || TextUtils.isEmpty(nameStr) || TextUtils.isEmpty(idStr)) {
                    showTips("请完成信息后再试");
                } else {
                    if (isSubmit) {
                        if (TextUtils.isEmpty(companyIdStr)) {
                            showTips("请完成信息后再试");
                        } else {
                            showProgressDialog("上传中...");
                            submitData();
                        }
                    } else {
                        Intent step2Intent = new Intent(this, CertifyStep2_Activity.class);
                        step2Intent.putExtra("pathPositive", pathPositive);
                        step2Intent.putExtra("pathNegative", pathNegative);
                        step2Intent.putExtra("pathDriver", pathDriver);
                        step2Intent.putExtra("pathOtherDriver", pathOtherDriver);
                        step2Intent.putExtra("nameStr", nameStr);
                        step2Intent.putExtra("idStr", idStr);
                        step2Intent.putExtra("pathCompany", pathCompany);
                        startActivity(step2Intent);
                        finish();
                        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                    }
                }

                break;
            case R.id.positiveLayout:
                Intent pIntent = new Intent(this, SystemAlbumPickerActivity.class);
                pIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(pIntent, POSITIVE_REQUEST_CODE);
                break;
            case R.id.negativeLayout:
                Intent nIntent = new Intent(this, SystemAlbumPickerActivity.class);
                nIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(nIntent, NEGATIVE_REQUEST_CODE);
                break;
            case R.id.addDriverBtn:
                Intent addDriverIntent = new Intent(this, SystemAlbumPickerActivity.class);
                addDriverIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(addDriverIntent, DRIVER_REQUEST_CODE);
                break;
            case R.id.addOtherDriverBtn:
                Intent addOtherDriverIntent = new Intent(this, SystemAlbumPickerActivity.class);
                addOtherDriverIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(addOtherDriverIntent, OTHERDRIVER_REQUEST_CODE);
                break;
            case R.id.addCompanyBtn:
                Intent companyIntent = new Intent(this, SystemAlbumPickerActivity.class);
                companyIntent.putExtra(SystemAlbumPickerActivity.key_appPath,"photo/test");
                startActivityForResult(companyIntent, COMPANY_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POSITIVE_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH) {
            pathPositive=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathPositive))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(positivePic, new Callback() {
                        @Override
                        public void onSuccess() {
                            p_picView.setVisibility(View.GONE);
                            p_textView.setVisibility(View.GONE);
                            positivePic.setVisibility(View.VISIBLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                positiveLayout.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                positiveLayout.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
        }else if(requestCode == NEGATIVE_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            pathNegative=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathNegative))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(negativePic, new Callback() {
                        @Override
                        public void onSuccess() {
                            n_picView.setVisibility(View.GONE);
                            n_textView.setVisibility(View.GONE);
                            negativePic.setVisibility(View.VISIBLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                negativeLayout.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                negativeLayout.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
        }else if(requestCode == DRIVER_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            pathDriver=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathDriver))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(SizeFormat.diptopx(this, 80), SizeFormat.diptopx(this, 100))
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
        }else if(requestCode == COMPANY_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            pathCompany=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathCompany))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(SizeFormat.diptopx(this, 80), SizeFormat.diptopx(this, 100))
                    .into(addCompanyBtn, new Callback() {
                        @Override
                        public void onSuccess() {
                            addCompanyBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                addCompanyBtn.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                addCompanyBtn.setBackgroundColor(getResources().getColor(R.color.background_white));
                            }
                        }

                        @Override
                        public void onError() {
                            showTips("图片加载失败");
                        }
                    });
        }else if(requestCode == OTHERDRIVER_REQUEST_CODE && resultCode == SystemAlbumPickerActivity.resultCode_SINGLE_PATH){
            pathOtherDriver=data.getStringExtra(SystemAlbumPickerActivity.key_singlePath);
            picasso.with(this)
                    .load(new File(pathOtherDriver))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(SizeFormat.diptopx(this, 80), SizeFormat.diptopx(this, 100))
                    .into(addOtherDriverBtn, new Callback() {
                        @Override
                        public void onSuccess() {
                            addOtherDriverBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                addOtherDriverBtn.setBackgroundColor(getResources().getColor(R.color.background_white,null));
                            }else{
                                addOtherDriverBtn.setBackgroundColor(getResources().getColor(R.color.background_white));
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
        File positiveFile=new File(pathPositive);
        File negativeFile=new File(pathNegative);
        File driverFile=new File(pathDriver);
        File otherDriverFile=new File(pathOtherDriver);

        HashMap<String,String> params=new HashMap<>();
        params.put("command","driverMessage");
        params.put("dName",nameStr);
        params.put("idCardNum",idStr);
        params.put("sid",BaseApplication.getInstance().loginsid);
        params.put("cooperationId",companyIdStr);

        PostFormBuilder builder=OkHttpUtils.post();
        if(!TextUtils.isEmpty(pathCompany)){
            File companyFile=new File(pathCompany);
            builder.addFile("professionImg",companyFile.getName(),companyFile);
        }
        builder.addFile("idCardFrontImg", positiveFile.getName(), positiveFile)
                .addFile("idCardBackImg", negativeFile.getName(), negativeFile)
                .addFile("DLImg", driverFile.getName(), driverFile)
                .addFile("DLImg2", otherDriverFile.getName(), otherDriverFile)
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
                            Intent intent=new Intent(CertifyStep1_Activity.this, MainActivity.class);
                            startActivity(intent);
                            CertifyStep1_Activity.this.finish();
                            overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                        }else{
                            showTips(result.getMsg());
                            hideProgressDialog();
                        }
                    }
                });
    }

}
