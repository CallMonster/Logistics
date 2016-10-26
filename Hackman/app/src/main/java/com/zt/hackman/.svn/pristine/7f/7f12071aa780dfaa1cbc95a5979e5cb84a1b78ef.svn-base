package com.zt.hackman.model;

import android.view.View;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.view.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class CardTypeWindowModel {
    public WheelView cardTypeWheel,bankTypeWheel;
    public TextView cancelBtn,confirmBtn;

    public void findViewByIds(View view, View.OnClickListener listener){
        bankTypeWheel = (WheelView) view.findViewById(R.id.bank_type_wheel);
        cardTypeWheel = (WheelView)view.findViewById(R.id.card_type_wheel);
        cancelBtn  = (TextView)view.findViewById(R.id.window_card_cancel_btn);
        confirmBtn = (TextView)view.findViewById(R.id.window_card_confirm_btn);

        cancelBtn.setOnClickListener(listener);
        confirmBtn.setOnClickListener(listener);
    }

    public void initView(ArrayList<String> banks, ArrayList<String> cards){
        bankTypeWheel.setData(banks);
        cardTypeWheel.setData(cards);
    }
}
