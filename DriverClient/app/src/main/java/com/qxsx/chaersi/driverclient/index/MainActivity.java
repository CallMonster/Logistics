package com.qxsx.chaersi.driverclient.index;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.FragPageAdapter;
import com.qxsx.chaersi.driverclient.fragment.IndexFragment;
import com.qxsx.chaersi.driverclient.fragment.OrderFragment;
import com.qxsx.chaersi.driverclient.fragment.PersonalFragment;
import com.qxsx.chaersi.driverclient.widget.ShadowColorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {

    @BindView(R.id.frag_viewpager) ViewPager frag_viewpager;
    @BindView(R.id.id_indicator_one) ShadowColorView indicator_one;
    @BindView(R.id.id_indicator_two) ShadowColorView indicator_two;
    @BindView(R.id.id_indicator_three) ShadowColorView indicator_three;

    private ArrayList<ShadowColorView> indicatorsArr;
    private ArrayList<Fragment> fragmentsArr;
    private OrderFragment orderFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        indicatorsArr=new ArrayList<ShadowColorView>();
        indicatorsArr.add(indicator_one);
        indicatorsArr.add(indicator_two);
        indicatorsArr.add(indicator_three);

        indicator_one.setOnClickListener(this);
        indicator_two.setOnClickListener(this);
        indicator_three.setOnClickListener(this);

        indicator_one.setIconAlpha(1.0f);

        fragmentsArr=new ArrayList<Fragment>();
        IndexFragment indexFrag=new IndexFragment();
        fragmentsArr.add(indexFrag);
        orderFrag=new OrderFragment();
        fragmentsArr.add(orderFrag);
        PersonalFragment personalFrag=new PersonalFragment();
        fragmentsArr.add(personalFrag);

        FragPageAdapter adapter=new FragPageAdapter(getSupportFragmentManager(),fragmentsArr);
        frag_viewpager.setAdapter(adapter);
        frag_viewpager.addOnPageChangeListener(this);
//        frag_viewpager.setOffscreenPageLimit(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_indicator_one:
                resetTabBtn();
                indicatorsArr.get(0).setIconAlpha(1.0f);
                frag_viewpager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_two:
                resetTabBtn();
                indicatorsArr.get(1).setIconAlpha(1.0f);
                frag_viewpager.setCurrentItem(1, false);
                if(orderFrag!=null){
                    orderFrag.RunReq();
                }
                break;
            case R.id.id_indicator_three:
                resetTabBtn();
                indicatorsArr.get(2).setIconAlpha(1.0f);
                frag_viewpager.setCurrentItem(2, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ShadowColorView left = indicatorsArr.get(position);
            ShadowColorView right = indicatorsArr.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if(orderFrag!=null){
            orderFrag.RunReq();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 重置所有btn
     */
    private void resetTabBtn(){
        for(ShadowColorView item:indicatorsArr){
            item.setIconAlpha(0);
        }
    }

    private long mExitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                // 如果两次按键时间间隔大于2000毫秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();// 更新mExitTime
            } else {
                System.exit(0);// 否则退出程序
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
