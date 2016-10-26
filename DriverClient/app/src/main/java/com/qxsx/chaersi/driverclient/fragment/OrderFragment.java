package com.qxsx.chaersi.driverclient.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qxsx.chaersi.driverclient.R;
import com.qxsx.chaersi.driverclient.base.BaseApplication;
import com.qxsx.chaersi.driverclient.base.BaseFragment;
import com.qxsx.chaersi.driverclient.base.InitDesign;
import com.qxsx.chaersi.driverclient.entry.request.OrderCodeReq;
import com.qxsx.chaersi.driverclient.entry.result.OrderCodeResult;
import com.qxsx.chaersi.driverclient.index.MainActivity;
import com.qxsx.chaersi.driverclient.user.CertifyStep1_Activity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by Chaersi on 16/10/23.
 */
public class OrderFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,QRCodeView.Delegate {
    private String TAG="OrderFragment";
    @BindView(R.id.rgpBot) RadioGroup rgp;
    @BindView(R.id.rbtn01) RadioButton rbtn01;
    @BindView(R.id.rbtn02) RadioButton rbtn02;
    @BindView(R.id.rbtn03) RadioButton rbtn03;

    @BindView(R.id.leftView) ImageView leftView;
    @BindView(R.id.leftBtn) View leftBtn;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.rightBtn_img) ImageView rightBtnImg;
    @BindView(R.id.rightBtn) View rightBtn;

    @BindView(R.id.offLineLayout) LinearLayout offLineLayout;
    @BindView(R.id.onLineLayout) FrameLayout onLineLayout;

    @BindView(R.id.cutState) ImageView cutState;

    /*二维码扫描*/
    public QRCodeView mQRCodeView;

    /*----*/

    private Order_PickFragment frag_r_One;
    private Order_ArrivalFragment frag_r_Two;
    private Order_SettleFragment frag_r_Three;

    private FragmentTransaction transaction;
    boolean onLineState=false;
    boolean isShowQRCode=false;//是否显示二维码
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frag_order, null);
        ButterKnife.bind(this, view);

        title.setText("订单任务");
        leftView.setImageResource(R.mipmap.navi_message_have);
        rightBtnImg.setImageResource(R.mipmap.navi_call_normal);
        rightBtnImg.setVisibility(View.INVISIBLE);
        rightBtn.setVisibility(View.INVISIBLE);
        leftView.setVisibility(View.INVISIBLE);

        frag_r_One = new Order_PickFragment();
        frag_r_Two = new Order_ArrivalFragment();
        frag_r_Three = new Order_SettleFragment();

        mContext = frag_r_One;
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contain, frag_r_One);
        transaction.commit();

        mQRCodeView= (ZXingView)view.findViewById(R.id.zxingview);

        return view;
    }

    public void RunReq(){
        if(isShowQRCode){

        }else{
            if(frag_r_One!=null){
                frag_r_One.runRequest();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rgp.setOnCheckedChangeListener(this);
    }

    private Fragment mContext;

    /**
     * 切换Fragment
     *
     * @param to
     */
    private void switchContent(Fragment to) {
        if (mContext != to) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            if (to.isAdded()) {
                transaction.hide(mContext).show(to).commit();
            } else {
                transaction.hide(mContext).add(R.id.contain, to).commit();
            }
            mContext = to;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (checkedId) {
            case R.id.rbtn01:
                if (null == frag_r_One) {
                    frag_r_One = new Order_PickFragment();
                }
                Drawable bottomDrawable = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomDrawable = getResources().getDrawable(R.mipmap.ind_selected, null);
                } else {
                    bottomDrawable = getResources().getDrawable(R.mipmap.ind_selected);
                }
                bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
                rbtn01.setCompoundDrawables(null, null, null, bottomDrawable);
                rbtn02.setCompoundDrawables(null, null, null, null);
                rbtn03.setCompoundDrawables(null, null, null, null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    rbtn01.setTextColor(getResources().getColor(R.color.bottom_bg_color, null));
                    rbtn02.setTextColor(getResources().getColor(R.color.text_grey, null));
                    rbtn03.setTextColor(getResources().getColor(R.color.text_grey, null));
                } else {
                    rbtn01.setTextColor(getResources().getColor(R.color.bottom_bg_color));
                    rbtn02.setTextColor(getResources().getColor(R.color.text_grey));
                    rbtn03.setTextColor(getResources().getColor(R.color.text_grey));
                }

                switchContent(frag_r_One);
                break;
            case R.id.rbtn02:
                if (null == frag_r_Two) {
                    frag_r_Two = new Order_ArrivalFragment();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomDrawable = getResources().getDrawable(R.mipmap.ind_selected, null);
                } else {
                    bottomDrawable = getResources().getDrawable(R.mipmap.ind_selected);
                }
                bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
                rbtn01.setCompoundDrawables(null, null, null, null);
                rbtn02.setCompoundDrawables(null, null, null, bottomDrawable);
                rbtn03.setCompoundDrawables(null, null, null, null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    rbtn02.setTextColor(getResources().getColor(R.color.bottom_bg_color, null));
                    rbtn01.setTextColor(getResources().getColor(R.color.text_grey, null));
                    rbtn03.setTextColor(getResources().getColor(R.color.text_grey, null));
                } else {
                    rbtn02.setTextColor(getResources().getColor(R.color.bottom_bg_color));
                    rbtn01.setTextColor(getResources().getColor(R.color.text_grey));
                    rbtn03.setTextColor(getResources().getColor(R.color.text_grey));
                }

                switchContent(frag_r_Two);
                break;
            case R.id.rbtn03:
                if (null == frag_r_Three) {
                    frag_r_Three = new Order_SettleFragment();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bottomDrawable = getResources().getDrawable(R.mipmap.ind_selected, null);
                } else {
                    bottomDrawable = getResources().getDrawable(R.mipmap.ind_selected);
                }
                bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
                rbtn01.setCompoundDrawables(null, null, null, null);
                rbtn02.setCompoundDrawables(null, null, null, null);
                rbtn03.setCompoundDrawables(null, null, null, bottomDrawable);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    rbtn03.setTextColor(getResources().getColor(R.color.bottom_bg_color, null));
                    rbtn01.setTextColor(getResources().getColor(R.color.text_grey, null));
                    rbtn02.setTextColor(getResources().getColor(R.color.text_grey, null));
                } else {
                    rbtn03.setTextColor(getResources().getColor(R.color.bottom_bg_color));
                    rbtn01.setTextColor(getResources().getColor(R.color.text_grey));
                    rbtn02.setTextColor(getResources().getColor(R.color.text_grey));
                }

                switchContent(frag_r_Three);
                break;
        }
    }

    @OnClick({R.id.leftBtn, R.id.rightBtn,R.id.cutState})
    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:

                break;
            case R.id.rightBtn:

                break;
            case R.id.cutState:
                if(onLineState){
                    onLineState=false;
                    cutState.setImageResource(R.mipmap.btn_online);
                }else{
                    isShowQRCode=true;
                    offLineLayout.setVisibility(View.VISIBLE);
                    onLineLayout.setVisibility(View.GONE);
                    rightBtnImg.setVisibility(View.GONE);
                    rightBtn.setVisibility(View.GONE);
                    leftView.setVisibility(View.GONE);

                    startQRCode();
                }
                break;
        }
    }

    private void startQRCode(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.VIBRATE)!= PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.FLASHLIGHT)!= PackageManager.PERMISSION_GRANTED
                    ){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS, Manifest.permission.CAMERA,
                        Manifest.permission.VIBRATE, Manifest.permission.FLASHLIGHT}, 1);
            }
        }else{
            mQRCodeView.startCamera();
            mQRCodeView.setDelegate(this);
            mQRCodeView.startSpot();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        if(requestCode==1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mQRCodeView.startCamera();
                mQRCodeView.setDelegate(this);
                mQRCodeView.startSpot();
            } else {
                mQRCodeView.stopCamera();
                mQRCodeView.stopSpot();
                showAlert();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mQRCodeView!=null){
            mQRCodeView.startCamera();
            mQRCodeView.setDelegate(this);
            mQRCodeView.startSpot();
        }
    }

    private String QRCodeStr="";
    @Override
    public void onScanQRCodeSuccess(String result) {
        if(isShowQRCode){
            QRCodeStr=result;
            showProgressDialog("加载中..");
            BaseApplication.getInstance().addRequestQueue(onlineReq);
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        showTips("请开启摄像头后再试");
    }

    /**
     * 警告提示
     */
    private void showAlert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("警告");
        builder.setMessage("由于您没有允许相关权限，部分功能无法使用，请允许权限后再试");
        //监听下方button点击事件
        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        //设置对话框是可取消的
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    StringRequest onlineReq=new StringRequest(Request.Method.POST, InitDesign.BASE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,"online:"+response);
                    OrderCodeResult result=BaseApplication.gson.fromJson(response,OrderCodeResult.class);
                    if("108".equals(result.getCode())){
                        offLineLayout.setVisibility(View.GONE);
//                        offLineLayout.removeView(mQRCodeView);
//                        mQRCodeView.stopCamera();
                        mQRCodeView.stopSpot();
                        onLineLayout.setVisibility(View.VISIBLE);
                        rightBtnImg.setVisibility(View.VISIBLE);
                        rightBtn.setVisibility(View.VISIBLE);
                        leftView.setVisibility(View.VISIBLE);

                        BaseApplication.getInstance().ONLINE_STATE=true;
                        frag_r_One.runRequest();
                    }else{
                        showTips(result.getMsg());
                        mQRCodeView.startSpot();
                    }
                    hideProgressDialog();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG,"err:"+error);
                    showTips("网络不畅请稍后再试");
                    hideProgressDialog();
                    mQRCodeView.startSpot();
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            OrderCodeReq orderCode=new OrderCodeReq();
            orderCode.setCommand("changeQRCode");
            orderCode.setSid(BaseApplication.getInstance().loginsid);
            orderCode.setPage("1");
            orderCode.setVehicleId(QRCodeStr);
            orderCode.setLimit("5");
            HashMap<String, String> params = new HashMap<>();
            params.put("data", BaseApplication.gson.toJson(orderCode));
            return params;
        }
    };


}
