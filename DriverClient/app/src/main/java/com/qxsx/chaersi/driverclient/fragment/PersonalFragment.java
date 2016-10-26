package com.qxsx.chaersi.driverclient.fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.adapter.PersonalItemAdapter;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.BaseFragment;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.PersonalInfoReq;
import com.qxsx.chaersi.driverclient.entry.result.PersonalInfoResult;
import com.qxsx.chaersi.driverclient.widget.ListViewShowView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chaersi on 16/10/23.
 */
public class PersonalFragment extends BaseFragment {
    private String TAG="PersonalFragment";

    @BindView(R.id.itemListView) ListViewShowView itemListView;
    @BindView(R.id.orderInfo) TextView orderInfo;
    @BindView(R.id.nameView) TextView nameView;
    @BindView(R.id.reviewFlag) ImageView reviewFlag;
    @BindView(R.id.driverid) TextView driverid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_personal, null);
        ButterKnife.bind(this, view);
        itemListView.setAdapter(new PersonalItemAdapter(getActivity()));


        showProgressDialog("读取中...");
        BaseApplication.getInstance().addRequestQueue(personalReq);
        return view;
    }

    @Override
    public void onClickListener(View v) {

    }

    StringRequest personalReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.i(TAG,"个人信息:"+s);
                    PersonalInfoResult result=BaseApplication.gson.fromJson(s,PersonalInfoResult.class);
                    if("106".equals(result.getCode())){
                        orderInfo.setText("累计"+result.getData().getOrderCount()+"单    好评率"+result.getData().getFavorableRate());
                        nameView.setText(result.getData().getDName());
                        if("1".equals(result.getData().getReviewStatus())){
                            reviewFlag.setImageResource(R.mipmap.state_already_approve);
                        }else if("0".equals(result.getData().getReviewStatus())){
                            reviewFlag.setImageResource(R.mipmap.state_no_approve);
                        }

                        driverid.setText(result.getData().getDriverId());
                    }else{
                        showTips(result.getMsg());
                    }
                    hideProgressDialog();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i(TAG,"网络不畅有稍后再试");
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            PersonalInfoReq req=new PersonalInfoReq();
            req.setCommand("driverPersonCenter");
            req.setSid(BaseApplication.getInstance().loginsid);
            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(req));
            return params;
        }
    };

}
