package com.zt.hackman.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.activity.LoginActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.view.LoginDialog;

/**
 * Created by Administrator on 2016/10/18.
 */
public class LoginUtils {

    public static void toLogin(Activity ac){
        Intent intent = new Intent(ac,LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle .putInt(Constant.INTNENTS.INTENT_LOGIN_STATE,1);
        intent.putExtras(bundle);
        ac.startActivity(intent);
    }

    public static void showDialog(Activity ac,String title,String content){
        if(title==null||StringUtils.isEmpty(title)){
            title = "登录";
        }

        if(content==null&&StringUtils.isEmpty(content)) {
            content = "您还没有登录";
        }

        LoginDialog dialog = new LoginDialog(ac,
                R.layout.dialog_is_login,title, content);
        dialog.show();

    }
}
