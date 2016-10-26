package com.zt.hackman.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.activity.MessageCenterAcitivty;
import com.zt.hackman.adapter.OrderFragmentAdapter;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.base.BaseFragment;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.RefreshEvent;
import com.zt.hackman.event.SwitchEvent;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.model.OrderModel;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.OrderRead;
import com.zt.hackman.response.OrderResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.SystemUtils;
import com.zt.hackman.view.SmartTabLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

/**
 * Created by Administrator on 2016/9/13.
 */
public class OrderFragment extends BaseFragment implements RightListener,LeftListener{
    View view;
    private NavModel navModel;
    private OrderModel model;
    private OrderRead read;

    String [] titles = {"待取货","待送达","待结算"};
    String [] badges = new String[3];


    StringCallback readCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            model.progressActivity.showError("网络请求异常"
                    , new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LoginUtils.showDialog(getActivity(),"提示登录","");
                        }
                    });
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,getActivity());
            if(res.code>0){
                read = (OrderRead) GsonUtils
                        .jsonToBean(res.data,OrderRead.class);
                if(read!=null){
                    if("2".equals(read.isOnline)||"3".equals(read.isOnline)){
                        model.switchLineState(1);
                    }else if("1".equals(read.isOnline)||"0".equals(read.isOnline)){
                        model.switchLineState(0);
                    }else{
                        model.switchLineState(2);
                    }
                    if("1".equals(read.isRead)){
                        navModel.setLeftBtn(R.mipmap.navi_message_have);
                    }else{
                        navModel.setLeftBtn(R.mipmap.navi_message);
                    }
                    badges[0] = read.takeGoods;
                    badges[1] = read.delivery;
                    badges[2] = read.closeAnAccount;
                    initFragments();
                    model.progressActivity.showContent();
                }
            }else{
                if(res.code==-10){
                    model.progressActivity.showError("会话已过期,请重新登录"
                            , new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LoginUtils.showDialog(getActivity(),"提示登录","");
                                }
                            });

                }else{
                    model.progressActivity.showError(res.msg
                            , new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initRead();
                                }
                            });
                }
            }
        }
    };

    @Subcriber
    public void onEventMain(RefreshEvent refreshEvent){
        if(refreshEvent.type == RefreshEvent.TYPE_LOGIN_REFRESH){
            initData();
        }
    }

    public void initRead(){
        model.progressActivity.showLoading();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","isRead");
            String sid = PreferencesUtils.getSID(getActivity());
            if(sid==null){
                LoginUtils.showDialog(getActivity(),"提示登录","您还没有登录,请先登录");
            }
            jsonObject.put("sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),readCallBack, Constant.TEST_HOST);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order,container,false);
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(view);
        navModel.setLeftListener(this);
        navModel.setRightListener(this);
        navModel.setTitle("订单任务");
        navModel.setRightBtn(R.mipmap.navi_call_normal);
    }

    private void initFragments(){
        model.tablayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_custom_tab,container,false);
                TextView badgeText = (TextView) view.findViewById(R.id.custom_tab_badge);
                TextView titleText = (TextView)view.findViewById(R.id.custom_tab_text);
                badgeText.setText(badges[position]);
                titleText.setText(titles[position]);
                return view;
            }
        });
        OrderFragmentAdapter adapter = new OrderFragmentAdapter(getActivity().getSupportFragmentManager(),
                titles);
        model.viewPager.setAdapter(adapter);
        model.tablayout.setViewPager(model.viewPager);
    }

    @Override
    protected void findViewByIds() {
        model = new OrderModel();
        model.findViewByIds( getActivity(),view);
    }

    @Subcriber
    public void onEventMain(SwitchEvent switchEvent){
        if(switchEvent.type==SwitchEvent.TYPE_SWITCH_LOGOUT){
            initRead();
        }
    }
    @Override
    protected void initView() {
        initRead();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            initRead();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void initData() {
        //initRead();
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void rightClick() {
        SystemUtils.call("02200001000",getActivity());
    }

    @Override
    public void leftClick() {
        startActivity(MessageCenterAcitivty.class);
    }


}