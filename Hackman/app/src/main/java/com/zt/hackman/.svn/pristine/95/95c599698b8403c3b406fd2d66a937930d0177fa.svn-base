package com.zt.hackman.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.loader.PicassoImageLoader;
import com.zt.hackman.model.HackmanModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/18.
 */
public class EditHackmanTwoActivity extends BaseActivity implements LeftListener,RightListener,View.OnClickListener{

    NavModel navModel;
    HackmanModel model;

    private int currentChoose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hackman_two);
        initNavBar();
        findViewByIds();
        initView();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setRightListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("车辆信息");
        navModel.setRightBtn("提交审核");
    }

    @Override
    protected void findViewByIds() {

    }

    @Override
    protected void initView() {
        model = HackmanModel.getInstance();
        model.initTwo(this,this);
        //model = new HackmanModel(this,this,2,-2);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        finish();
    }


    @Override
    public void rightClick() {
        model.uploadCar();
    }

    /**
     * 选择图片
     */
    private void chooseImg(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制

        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, Constant.INTNENTS.IMAGE_PICKER);
    }

    @Override
    protected void onDestroy() {
        model.clearOne();
        model.clearTwo();
        model = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.watch_hackman_clause_btn:
                startActivity(HackmanClauseActivity.class);
                break;
            case R.id.two_first_btn:
                currentChoose = 6;
                chooseImg();
                break;
            case R.id.two_second_btn:
                currentChoose = 7;
                chooseImg();
                break;
            case R.id.two_third_btn:

                currentChoose = 8;
                chooseImg();
                break;
            case R.id.two_four_btn:
                currentChoose = 9;
                chooseImg();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constant.INTNENTS.IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                switch (currentChoose){
                    case 6:      //车辆照片
                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.vehicleBtn);
                        }
                        break;
                    case 7:      //行驶证正面
                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.vehicleLicenseBtn);
                        }
                        break;
                    case 8:      //行驶证反面
                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.vehicleLicenseBtn2);
                        }
                        break;
                    case 9:      //营运证照片
                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.operationLicenseBtn);
                        }
                        break;
                }

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
