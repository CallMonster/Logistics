package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.activity.AddBankTwoActivity;
import com.zt.hackman.activity.ValidPhoneActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.view.ChooseCardTypeWindow;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class AddBankTwoModel implements View.OnClickListener{
    public EditText cardPhoneNO;
    public RelativeLayout addBankNav,typeSelectorLayout,phoneLayout;
    public TextView nextBtn;
    private Activity activity;
    private ArrayList<String> banks,cards;

    public void findViewByIds(Activity activity,ArrayList<String> banks,ArrayList<String> cards){
        this.activity = activity;
        this.banks = banks;
        this.cards = cards;
        typeSelectorLayout = (RelativeLayout)activity.findViewById(R.id.card_type_selector_layout);
        cardPhoneNO = (EditText)activity.findViewById(R.id.card_phone_no);
        phoneLayout = (RelativeLayout)activity.findViewById(R.id.add_bank_phone_layout);
        addBankNav = (RelativeLayout)activity.findViewById(R.id.add_bank_two_nav);
        nextBtn  = (TextView)activity.findViewById(R.id.add_bank_next_two_btn);

        addBankNav.setBackgroundResource(R.color.colorWhite);
        nextBtn.setOnClickListener(this);
        typeSelectorLayout.setOnClickListener(this);
        phoneLayout.setOnClickListener(this);
        cardPhoneNO.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_bank_next_btn:
                //activity.onBackPressed();
                break;
            case R.id.card_type_selector_layout:
                ChooseCardTypeWindow window = new ChooseCardTypeWindow(activity,banks,cards);
                window.showAtLocation(v, Gravity.NO_GRAVITY,0,0);
                break;
            case R.id.card_phone_no:
                cardPhoneNO.requestFocus();
                break;
            case R.id.add_bank_phone_layout:
                Intent intent = new Intent(activity, ValidPhoneActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.INTNENTS.INTENT_VALID_PHONE_WAY,2);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                break;
        }
    }
}
