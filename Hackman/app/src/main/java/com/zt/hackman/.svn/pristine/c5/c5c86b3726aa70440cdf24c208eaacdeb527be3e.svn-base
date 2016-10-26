package com.zt.hackman.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zt.hackman.fragment.MyOrderFragment;
import com.zt.hackman.fragment.OrderFragment;

/**
 * Created by Administrator on 2016/9/13.
 */
public class OrderFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private FragmentManager fm;

    public OrderFragmentAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.fm = fm;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return MyOrderFragment.newInstance(position );
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % titles.length].toUpperCase();
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
