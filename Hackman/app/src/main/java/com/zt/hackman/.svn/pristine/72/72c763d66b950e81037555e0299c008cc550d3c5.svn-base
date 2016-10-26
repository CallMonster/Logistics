package com.zt.hackman.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.activity.EditHackmanOneActivity;
import com.zt.hackman.event.RegisterEvent;

import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/18.
 */
public class RegisterSucDialog extends Dialog implements View.OnClickListener {
    private int resId;
    private Context context;
    private TextView leftBtn,rightBtn;
    public RegisterSucDialog(Context context,int resId) {
        super(context,R.style.mydialog);
        this.context = context;
        this.resId = resId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resId);
        findViewByIds();
    }

    private void findViewByIds(){
        leftBtn = (TextView) findViewById(R.id.dialog_left_btn);
        rightBtn = (TextView) findViewById(R.id.dialog_right_btn);

        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_left_btn:
                dismiss();
                EventBus.getDefault().post(new RegisterEvent(RegisterEvent.TYPE_GO_MAIN));
                break;
            case R.id.dialog_right_btn:
                dismiss();
                EventBus.getDefault().post(new RegisterEvent(RegisterEvent.TYPE_GO_HACK));
//                context.startActivity(new Intent(context,EditHackmanOneActivity.class));
                break;
        }
    }
}
