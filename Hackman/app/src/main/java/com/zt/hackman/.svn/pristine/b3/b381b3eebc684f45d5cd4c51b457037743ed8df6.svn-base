package com.zt.hackman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.zt.hackman.R;
import com.zt.hackman.activity.MessageCenterAcitivty;
import com.zt.hackman.adapter.MovementAdapter;
import com.zt.hackman.adapter.NewsAdapter;
import com.zt.hackman.adapter.RecommendAdapter;
import com.zt.hackman.base.BaseFragment;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.model.HomeModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.response.HomeResponse;
import com.zt.hackman.utils.DisplayUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public class HomeFragment extends BaseFragment implements LeftListener{

    private View view;
    private NavModel navModel;
    private HomeModel homeModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(view);
        navModel.setTitle(R.mipmap.logo);
        navModel.setLeftBtn(R.mipmap.navi_message);
        navModel.setLeftListener(this);
    }
    @Override
    protected void findViewByIds() {
        homeModel = new HomeModel();
        homeModel.findViewByIds(view,getActivity());

    }
    @Override
    protected void initData() {

    }
    @Override
    protected void initView() {
        try {
            homeModel.initData();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"JSON数据转换异常",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void leftClick() {
        startActivity(MessageCenterAcitivty.class);
    }
}
