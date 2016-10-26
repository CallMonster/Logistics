package com.zt.hackman.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.event.RefreshEvent;
import com.zt.hackman.event.RegisterEvent;
import com.zt.hackman.utils.LoginUtils;

import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/18.
 */
public class LoginDialog extends Dialog implements View.OnClickListener {

    private int resId;
    private Activity context;
    private String content,title;
    private TextView leftBtn,rightBtn,contentView,titleView;
    public LoginDialog(Activity context, int resId,String title,String content) {
        super(context,R.style.mydialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.resId = resId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resId);
        findViewByIds();
    }

    private void findViewByIds(){
        leftBtn = (TextView) findViewById(R.id.dialog_login_left_btn);
        rightBtn = (TextView) findViewById(R.id.dialog_login_right_btn);
        contentView = (TextView) findViewById(R.id.dialog_login_content);
        titleView = (TextView)findViewById(R.id.dialog_login_title);

        titleView.setText(title);
        contentView.setText(content);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_login_left_btn:
                dismiss();
                EventBus.getDefault().post(new RefreshEvent(RefreshEvent.TYPE_LOGIN_CANCEL));
                break;
            case R.id.dialog_login_right_btn:
                dismiss();
                LoginUtils.toLogin(context);
                break;
        }
    }
}
