package com.zt.hackman.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.activity.AboutActivity;
import com.zt.hackman.activity.ContractUsActivity;
import com.zt.hackman.activity.HistoryOrderActivity;
import com.zt.hackman.activity.IdeaActivity;
import com.zt.hackman.activity.InventFriendsActivity;
import com.zt.hackman.activity.MessageCenterAcitivty;
import com.zt.hackman.activity.MyWalletActivity;
import com.zt.hackman.activity.PersonalCenterActivity;
import com.zt.hackman.activity.SettingActivity;
import com.zt.hackman.base.BaseFragment;
import com.zt.hackman.listener.LeftListener;
import com.zt.hackman.listener.MyListener;
import com.zt.hackman.listener.RightListener;
import com.zt.hackman.model.MyModel;
import com.zt.hackman.model.NavModel;

/**
 * Created by Administrator on 2016/9/13.
 */
public class PersonalFragment extends BaseFragment implements MyListener,LeftListener,RightListener{
    private View view;
    private NavModel navModel;
    private MyModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal,container,false);
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(view);
        navModel.setLeftListener(this);
        navModel.setRightListener(this);
        navModel.setRightBtn("编辑");
        navModel.rightBtn.setTextColor(getResources().getColor(R.color.colorWhite));
        navModel.setLeftBtn(R.mipmap.navi_message);
    }

    @Override
    protected void findViewByIds() {
        model = new MyModel();
        model.findViewByIds(view,getActivity());
        model.setListener(this);
    }

    @Override
    protected void initView() {
        model.init();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void orderClick() {
        startActivity(HistoryOrderActivity.class);
    }

    @Override
    public void friendsClick() {
        startActivity(InventFriendsActivity.class);
    }

    @Override
    public void aboutClick() {
        startActivity(AboutActivity.class);
    }

    @Override
    public void contractClick() {
        startActivity(ContractUsActivity.class);
    }

    @Override
    public void settingClick() {
        startActivity(SettingActivity.class);
    }

    @Override
    public void ideaClick() {
        startActivity(IdeaActivity.class);
    }

    @Override
    public void leftClick() {
        startActivity(MessageCenterAcitivty.class);
    }

    @Override
    public void rightClick() {
        startActivity(PersonalCenterActivity.class);
    }

    @Override
    public void walletClick() {
        startActivity(MyWalletActivity.class);
    }
}
