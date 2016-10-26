package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.activity.BankCardListActivity;
import com.zt.hackman.adapter.BankCardAdapter;
import com.zt.hackman.response.DepositResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class DepositModel  implements View.OnClickListener {

    public ListView bankListView;
    public RelativeLayout navDepositLayout;
    public TextView  depositBtn;
    public BankCardAdapter adapter;
    private Activity activity;
    public RelativeLayout bankSelectorBtn;

    public void findViewByIds(Activity activity){
        this.activity = activity;
        bankSelectorBtn = (RelativeLayout)activity.findViewById(R.id.bank_selector_btn);
        navDepositLayout = (RelativeLayout)activity.findViewById(R.id.deposit_nav_layout);
        depositBtn = (TextView)activity.findViewById(R.id.deposit_btn);
        navDepositLayout .setBackgroundResource(R.color.colorWhite);

        depositBtn.setOnClickListener(this);
        bankSelectorBtn .setOnClickListener(this);
    }

    public void initView(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bank_selector_btn:
                activity.startActivity(new Intent(activity, BankCardListActivity.class));
                break;
        }
    }
}
