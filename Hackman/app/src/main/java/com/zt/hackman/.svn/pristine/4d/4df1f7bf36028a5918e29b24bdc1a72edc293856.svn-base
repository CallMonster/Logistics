package com.zt.hackman.model;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zt.hackman.R;

/**
 * Created by Administrator on 2016/9/22.
 */
public class CauseWindowModel{
    public TextView leftBtn,rightBtn;
    public ListView causeListView;
    public RelativeLayout rootView;

    public void findViewByIds(View view, View.OnClickListener listener){
        leftBtn = (TextView)view.findViewById(R.id.window_cancel_btn);
        rightBtn = (TextView)view.findViewById(R.id.window_confirm_btn);
        causeListView = (ListView)view.findViewById(R.id.window_cause_list_ivew);
        rootView = (RelativeLayout)view.findViewById(R.id.window_root_view);

        leftBtn.setOnClickListener(listener);
        rightBtn.setOnClickListener(listener);
        rootView.setOnClickListener(listener);
    }
}
