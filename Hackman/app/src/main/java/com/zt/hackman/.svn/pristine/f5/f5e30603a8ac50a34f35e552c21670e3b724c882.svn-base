package com.zt.hackman.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zt.hackman.base.widget.ProgressActivity;
import com.zt.hackman.base.widget.XLButton;


/**
 * Created by Eugene on 2016/4/23.
 */
public abstract class BaseFragment extends Fragment {
	public ProgressActivity mainProgressActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	initNavBar();
        findViewByIds();
        initData();
        initView();
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * #1# 初始化导航栏
     */
    protected abstract void initNavBar();

    /**
     * #2# 绑定控件id 获取控件
     */
    protected abstract void findViewByIds();

    /**
     * #3# 初始化控件 设置监听等
     */
    protected abstract void initView();

    /**
     * #4# 初始化准备数据
     *
     */
    protected abstract void initData();



    public void startActivity(Class clazz){
        ((BaseActivity)getActivity()).startActivity(clazz);
    }

    public void startActivity(Class clazz,Bundle bundle) {
        ((BaseActivity) getActivity()).startActivity(clazz, bundle);
    }
    public void startActivityForResult(Class<?> pClass, Bundle pBundle,
                                       int requestCode) {
            ((BaseActivity)getActivity()).startActivityForResult(pClass, pBundle,
                requestCode);
    }

}
