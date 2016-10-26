package com.qxsx.chaersi.driverclient.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.user.LoginActivity;
import com.qxsx.chaersi.driverclient.user.RegistActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexActivity extends BaseActivity {
    @BindView(R.id.loginBtn) TextView loginBtn;
    @BindView(R.id.registBtn) TextView registBtn;
    @BindView(R.id.unLoginBtn) TextView unLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loginBtn, R.id.registBtn, R.id.unLoginBtn})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                Intent loginIntent=new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                finish();
                break;
            case R.id.registBtn:
                Intent regIntent=new Intent(this, RegistActivity.class);
                startActivity(regIntent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                finish();
                break;
            case R.id.unLoginBtn:
                Intent unLoginIntent=new Intent(this, MainActivity.class);
                startActivity(unLoginIntent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
                finish();
                break;
        }
    }
}
