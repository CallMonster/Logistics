package com.zt.hackman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseFragment;
import com.zt.hackman.event.RefreshEvent;
import com.zt.hackman.model.MyOrderModel;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyOrderFragment extends BaseFragment {
    private int orderIndex;
    private View view;
    MyOrderModel model;


    public static MyOrderFragment newInstance(int orderIndex) {

        Bundle args = new Bundle();
        args.putInt("orderIndex",orderIndex);
        MyOrderFragment fragment = new MyOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        orderIndex = args.getInt("orderIndex");
        view = inflater.inflate(R.layout.fragment_my_order,container,false);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void initNavBar() {
    }

    @Subcriber
    public void onEventMain(RefreshEvent refreshEvent){
        if(refreshEvent.type==RefreshEvent.TYPE_ARRIVE_REFRESH){  //待送达刷新
            model.init(getActivity(),1);
        }else if(refreshEvent.type==RefreshEvent.TYPE_AFFIRM_PICK_REFRESH){  //带取货刷新
            model.init(getActivity(),0);
        }
    }

    @Override
    protected void findViewByIds() {
        model = new MyOrderModel();
        model.findViewByIds(view,getActivity());
    }

    @Override
    protected void initView() {
        model.init(getActivity(),orderIndex);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().register(this);
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }
}
