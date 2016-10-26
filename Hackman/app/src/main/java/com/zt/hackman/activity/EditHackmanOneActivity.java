package com.zt.hackman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.BaseApp;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.SwitchEvent;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.loader.PicassoImageLoader;
import com.zt.hackman.model.HackmanModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.response.HackmanResponse;
import com.zt.hackman.utils.ToastUtils;

import org.json.JSONException;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/18.
 */
public class EditHackmanOneActivity extends
        BaseActivity implements LeftListener,RightListener,View.OnClickListener {
    NavModel navModel;
    HackmanModel model;
    private int currentChoose = 0 ;
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_hackman_one);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            from =  bundle.getInt("from",-1);
        }
        findViewByIds();

        initNavBar();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setRightListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("驾驶员信息");
        navModel.setRightBtn("下一步");
    }

    @Override
    protected void findViewByIds() {
        model = HackmanModel.getInstance();
        model.initOne(this,this,from);
        //;new HackmanModel(this,this,1,from);
        model.response = new HackmanResponse();
    }

    @Override
    protected void initView() {

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
        try {
            model.uploadAuditor();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"json数据格式有误",Toast.LENGTH_SHORT).show();
        }
        //startActivity(EditHackmanTwoActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.oneFrontBtn:
                currentChoose = 1;
                chooseImg();

                break;
            case R.id.oneReserveBtn:
                currentChoose = 2;
                chooseImg();
                break;

            case R.id.driving_licence_photo_btn:
                currentChoose = 3;
                chooseImg();
                break;
            case R.id.driving_licence_photo_btn_2:
                currentChoose = 4;
                chooseImg();
                break;
            case R.id.business_certificate_photo_btn:
                currentChoose = 5;
                chooseImg();
                break;
        }
    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constant.INTNENTS.IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                switch (currentChoose){
                    case 1:      //身份证正面
                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.oneFrontBtn);
                        }
                        break;
                    case 2:      //身份证反面
                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.oneReserveBtn);
                        }
                        break;
                    case 3:      //驾驶证正本
                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.driving_licence_photo_btn);
                        }
                        break;
                    case 4:      //驾驶证副本
                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.driving_licence_photo_btn2);
                        }
                        break;
                    case 5:   //营业许可证

                        if(images!=null){
                            model.changeImage(images.get(0),currentChoose,model.business_certificate_photo_btn);
                        }
                        break;
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Subcriber
    public void onEventMain(SwitchEvent event){
        if(event.type == SwitchEvent.TYPE_COMPLETE){
            model.clearOne();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
