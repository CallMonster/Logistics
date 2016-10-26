package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.activity.DepositActivity;
import com.zt.hackman.adapter.TurnOverAdapter;
import com.zt.hackman.view.LinearGradientDrawable;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyWalletModel implements View.OnClickListener{
    public TextView balanceText,notBalanceText;
    public LinearLayout withdraw_deposit_layout;
    public ListView turnOverListView;
    private Activity activity;

    public void findViewByIds(Activity activity){
        this.activity = activity;
        balanceText = (TextView) activity.findViewById(R.id.my_wallet_nums_text);
        notBalanceText = (TextView)activity.findViewById(R.id.my_wallet_no_nums_text);
        withdraw_deposit_layout = (LinearLayout) activity.findViewById(R.id.withdraw_deposit_layout);
        turnOverListView = (ListView)activity.findViewById(R.id.my_wallet_list_view);

        withdraw_deposit_layout.setOnClickListener(this);
    }

    public void initView(TurnOverAdapter adapter){
        turnOverListView .setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.withdraw_deposit_layout:
                activity.startActivity(new Intent(activity,DepositActivity.class));
                break;
        }
    }
}
