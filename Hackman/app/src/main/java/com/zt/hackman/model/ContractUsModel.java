package com.zt.hackman.model;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zt.hackman.R;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ContractUsModel {
    public RelativeLayout navContractLayout,callLayout;

    public void findViewByIds(Activity activity, View.OnClickListener listener){
        navContractLayout = (RelativeLayout) activity.findViewById(R.id.contract_us_nav);
        callLayout =(RelativeLayout)activity.findViewById(R.id.contract_us_call_layout);

        navContractLayout.setBackgroundResource(R.color.colorWhite);
        callLayout.setOnClickListener(listener);

    }
}
