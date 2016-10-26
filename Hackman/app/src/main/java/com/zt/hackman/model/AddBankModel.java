package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.activity.AddBankActivity;
import com.zt.hackman.activity.AddBankTwoActivity;

/**
 * Created by Administrator on 2016/9/26.
 */
public class AddBankModel implements View.OnClickListener{
    public EditText cardUserName,cardNo;
    public RelativeLayout addBankNav;
    public TextView nextBtn;
    private Activity activity;

    public void findViewByIds(Activity activity){
        this.activity = activity;
        cardUserName = (EditText)activity.findViewById(R.id.card_name);
        cardNo = (EditText)activity.findViewById(R.id.card_no_edit);

        addBankNav = (RelativeLayout)activity.findViewById(R.id.add_bank_nav);
        nextBtn  = (TextView)activity.findViewById(R.id.add_bank_next_btn);

        addBankNav.setBackgroundResource(R.color.colorWhite);
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_bank_next_btn:
                activity.startActivity(new Intent(activity, AddBankTwoActivity.class));
                break;
        }
    }
}
