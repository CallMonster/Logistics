package com.qxsx.chaersi.driverclient.index;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseActivity;
import com.qxsx.chaersi.driverclient.utils.PreferenceUtils;

import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                PreferenceUtils preference = new PreferenceUtils(WelcomeActivity.this);
                String sid = preference.getUserId();
                if (TextUtils.isEmpty(sid)) {
                    Intent intent = new Intent(WelcomeActivity.this, IndexActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                    finish();
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                    finish();
                }
            }

        }, 3 * 1000);
    }

    @Override
    public void onClickListener(View v) {

    }


}
