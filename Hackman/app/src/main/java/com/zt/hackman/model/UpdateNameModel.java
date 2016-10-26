package com.zt.hackman.model;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.event.RefreshEvent;

import org.simple.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/27.
 */
public class UpdateNameModel implements View.OnClickListener{

    public EditText nameEdit;
    public ImageView clearImg;
    public RelativeLayout navLayout;
    public TextView saveBtn;
    private BaseActivity activity;

    public void findViewByIds(BaseActivity activity){
        this.activity = activity;
        clearImg = (ImageView)activity . findViewById(R.id.clear_name_btn);
        nameEdit = (EditText) activity.findViewById(R.id.user_name_edit);
        navLayout = (RelativeLayout)activity.findViewById(R.id.update_name_nav);
        saveBtn = (TextView)activity.findViewById(R.id.update_name_save_btn);

        navLayout.setBackgroundResource(R.color.colorWhite);
        clearImg.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear_name_btn:
                nameEdit.setText(null);
                break;
            case R.id.update_name_save_btn:
                activity.finish();
                EventBus.getDefault().post(new RefreshEvent
                        (RefreshEvent.TYPE_REFRESH_PERSONAL_CENTER_ACTIVITY));
                break;
        }
    }
}
