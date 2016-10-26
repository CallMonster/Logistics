package com.zt.hackman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.BaseApp;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.RefreshEvent;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.model.PersonalCenterModel;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.HackmanResponse;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class PersonalCenterActivity extends BaseActivity implements LeftListener{
    NavModel navModel;
    PersonalCenterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_personal_center);
        initNavBar();
        findViewByIds();
        initView();
        initData();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("编辑个人资料");
    }

    @Override
    protected void findViewByIds() {
        model = new PersonalCenterModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView() {
        model.init();
    }

    @Subcriber
    public void onEventMain(RefreshEvent event){
        if(event.type == RefreshEvent.TYPE_REFRESH_PERSONAL_CENTER_ACTIVITY){
            model.init();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constant.INTNENTS.IMAGE_PICKER) {
                Toast.makeText(this, "数据已经回来了", Toast.LENGTH_SHORT).show();
               ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage("file://"+images.get(0).path,model.userImage, BaseApp.circleOptions);
                File face = new File(images.get(0).path);
                model.uplaod(face);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void leftClick() {
        onBackPressed();
    }
}
