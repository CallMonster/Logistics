package com.zt.hackman.model;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zt.hackman.R;
import com.zt.hackman.activity.AddBankActivity;
import com.zt.hackman.adapter.BankCardAdapter;
import com.zt.hackman.event.ChooseBankEvent;
import com.zt.hackman.response.DepositResponse;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class BankModel {
    public ListView bankListView;
    private Activity activity;
    public BankCardAdapter adapter;
    public RelativeLayout navBank;


    public void findViewByIds(Activity activity){
        this.activity= activity;
        bankListView = (ListView)activity.findViewById(R.id.bank_list_view);
        navBank  = (RelativeLayout)activity.findViewById(R.id.bank_list_nav);

        navBank.setBackgroundResource(R.color.colorWhite);
    }

    public void initView(final List<DepositResponse.BankCard> cards){
        adapter = new BankCardAdapter(activity);
        bankListView.setAdapter(adapter);
        adapter.refreshData(cards);

        bankListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==cards.size()-1){
                    activity.startActivity(new Intent(activity,AddBankActivity.class));
                }else {
                    EventBus.getDefault().post(new ChooseBankEvent(ChooseBankEvent.TYPE_CHOOSE_BANK,position));
                }
            }
        });
    }

    public void clear(){
        bankListView = null;
        activity = null;
        navBank = null;
        adapter = null;
    }
}
