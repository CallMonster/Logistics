package com.zt.hackman.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.BaseFragment;
import com.zt.hackman.event.SetPwdEvent;
import com.zt.hackman.event.SwitchEvent;
import com.zt.hackman.fragment.CodeFragment;
import com.zt.hackman.fragment.HomeFragment;
import com.zt.hackman.fragment.OrderFragment;
import com.zt.hackman.fragment.PersonalFragment;
import com.zt.hackman.view.CyTabView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    private FragmentManager manager;
    private FragmentTransaction transaction;


    private BaseFragment homeFragment,personalFragment,orderFragment,codeFragment;

    private CyTabView tabLayout;

    private BaseFragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        findViewByIds();
        initView();
        initData();
    }

    @Override
    protected void initNavBar() {

    }

    @Override
    protected void findViewByIds() {
        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        personalFragment =new PersonalFragment();
        codeFragment = new CodeFragment();
        manager = getSupportFragmentManager();

        tabLayout = (CyTabView) findViewById(R.id.main_tab_layout);
    }

    @Override
    protected void initView() {
        tabLayout.setOnCheckedChangeListener(this);
        ((RadioButton)tabLayout.getChildAt(0)).setChecked(true);

    }

    @Override
    protected void initData() {

    }

    private void switchContent(BaseFragment to){
        transaction = manager.beginTransaction();
        if(lastFragment !=null) {
            transaction.hide(lastFragment);
        }
        if(!to.isAdded()) {
            transaction.add(R.id.main_fragment_layout, to ,to.getTag());
        }else{
            transaction.show(to);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        lastFragment = to;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.home_radio_btn:
                switchContent(homeFragment);
                //transaction.replace(R.id.main_fragment_layout, homeFragment).commit();
                break;
            case R.id.person_radio_btn:
                switchContent(personalFragment);
                break;

            case R.id.order_radio_btn:
                switchContent(orderFragment);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subcriber
    public void onEventMain(SwitchEvent event){
        if(event.type == SwitchEvent.TYPE_SWITCH_CODE){
            switchContent(codeFragment);
        }else if(event.type== SwitchEvent.TYPE_SUCCESS){
            switchContent(orderFragment);
        }

    }

    @Subcriber
    public void onEventMain(SetPwdEvent event){
        if(event.type == SetPwdEvent.TYPE_BACK){
            //finish();
        }

    }
}
