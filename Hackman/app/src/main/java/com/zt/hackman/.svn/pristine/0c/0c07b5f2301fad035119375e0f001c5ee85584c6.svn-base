package com.zt.hackman.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zt.hackman.R;
import com.zt.hackman.view.AlertLoadingDialog;

/**
 * Created by Administrator on 2016/9/27.
 */
public class ToastUtils {

    public static void showSuccess(Context context,String text){
        createToast(context,R.mipmap.success,text);
    }

    public static void showError(Context context,String text){
        createToast(context,R.mipmap.success,text);
    }


    private static void createToast(Context context,int icon,String text){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_toast, null);
        ImageView img = (ImageView) view.findViewById(R.id.toast_icon);
        TextView textView= (TextView) view.findViewById(R.id.toast_content);
        img.setBackgroundResource(icon);
        if(text!=null){
            textView.setText(text);
        }
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.NO_GRAVITY, 0, DisplayUtil.dip2px(70));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public static AlertLoadingDialog showLoading(Context context,String text){
        AlertLoadingDialog dialog = new AlertLoadingDialog(context);
        dialog.show(text);
        return dialog;
    }

}
