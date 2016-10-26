package com.qxsx.chaersi.driverclient.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Chaersi on 16/7/1.
 */
public class FragPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentsArr;
    public FragPageAdapter(FragmentManager fm, ArrayList<Fragment> fragmentsArr) {
        super(fm);
        this.fragmentsArr=fragmentsArr;
    }

    @Override
    public int getCount() {
        return fragmentsArr.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsArr.get(position);
    }

}
