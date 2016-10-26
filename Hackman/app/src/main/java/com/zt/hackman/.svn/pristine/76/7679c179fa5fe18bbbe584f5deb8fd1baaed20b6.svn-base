package com.zt.hackman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.event.SetPwdEvent;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.ForgetModel;
import com.zt.hackman.utils.StatusUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * Created by Administrator on 2016/9/13.
 */
public class ForgetPwdActivity extends Activity {

    ForgetModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.changeStatus(this);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_forget);
        findViewByIds();
        initNav();
    }

    private void findViewByIds() {
        model = new ForgetModel();
        model.findViewByIds(this);
    }

    private void initNav(){
        model.rightBtn.setVisibility(View.GONE);
    }

    @Subcriber
    public void onEventMain(SetPwdEvent event){
        if(event.type == SetPwdEvent.TYPE_BACK){
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        model.clear();
        model = null;
        super.onDestroy();
    }
}
