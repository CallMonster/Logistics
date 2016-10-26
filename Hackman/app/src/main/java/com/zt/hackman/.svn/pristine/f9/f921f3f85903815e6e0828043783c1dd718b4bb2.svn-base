package com.zt.hackman.activity;

import android.os.Bundle;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.event.ChooseBankEvent;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.model.BankModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.response.DepositResponse;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class BankCardListActivity extends BaseActivity implements LeftListener{
    private BankModel model;
    private NavModel navModel;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_bank_list);
        initNavBar();
        findViewByIds();
        initView();
        initData();
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(this);
        navModel.setLeftListener(this);
        navModel.setLeftBtn(R.mipmap.main_back);
        navModel.setTitle("选择银行卡");
    }

    @Override
    protected void findViewByIds() {
        model = new BankModel();
        model.findViewByIds(this);
    }

    @Override
    protected void initView() {
        DepositResponse.BankCard card = new DepositResponse.BankCard();
        DepositResponse.BankCard card1 = new DepositResponse.BankCard();
        DepositResponse.BankCard card2 = new DepositResponse.BankCard();
        List<DepositResponse.BankCard> cards = new ArrayList<DepositResponse.BankCard>();
        cards.add(card);
        cards.add(card1);
        cards.add(card2);
        model.initView(cards);
    }

    @Override
    protected void initData() {

    }

    @Subcriber
    public void onEventMain(ChooseBankEvent bankEvent){
        if(ChooseBankEvent.TYPE_CHOOSE_BANK==bankEvent.type) {
            index = bankEvent.index;
        }
    }

    @Override
    public void leftClick() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
