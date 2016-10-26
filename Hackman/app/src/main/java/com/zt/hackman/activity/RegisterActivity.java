package com.zt.hackman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.event.RegisterEvent;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.model.RegisterModel;
import com.zt.hackman.utils.StatusUtils;
import com.zt.hackman.view.RegisterSucDialog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * Created by Administrator on 2016/9/13.
 */
public class RegisterActivity extends Activity {
    private RegisterModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusUtils.changeStatus(this);
        setContentView(R.layout.activity_register);
        findViewByIds();
        initNav();
    }

    private void findViewByIds(){
        model = new RegisterModel();
        model.findViewByIds(this);
    }

    private void initNav(){
        model.rightBtn.setVisibility(View.GONE);
    }



    @Subcriber
    public void onEventMain(RegisterEvent event){
        if(event.type == RegisterEvent.TYPE_GO_HACK){
            finish();
            Bundle bundle = new Bundle();
            bundle.putInt("from",0);
            Intent intent = new Intent(this,EditHackmanOneActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(event.type == RegisterEvent.TYPE_GO_MAIN){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        model = null;
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
