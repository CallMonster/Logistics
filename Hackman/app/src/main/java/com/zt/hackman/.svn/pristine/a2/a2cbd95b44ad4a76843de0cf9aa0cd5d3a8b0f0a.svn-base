package com.zt.hackman.fragment;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zt.hackman.R;
import com.zt.hackman.base.BaseFragment;
import com.zt.hackman.event.SwitchEvent;
import com.zt.hackman.model.CodeModel;
import com.zt.hackman.model.NavModel;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.ToastUtils;

import org.simple.eventbus.EventBus;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * Created by Administrator on 2016/10/8.
 */
public class CodeFragment extends BaseFragment implements QRCodeView.Delegate{
    CodeModel model;
    NavModel navModel;
    private View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_code,container,false);
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    protected void initNavBar() {
        navModel = new NavModel(view);
        navModel.setTitle("订单任务");
    }

    @Override
    protected void findViewByIds() {
        model = new CodeModel();
        model.findViewByIds(getActivity(),view);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        if(hidden){
            if(model!=null&&model.mQRCodeView!=null){
                model.mQRCodeView.stopSpot();
            }
        }else{
            if(model!=null&&model.mQRCodeView!=null){
                model.mQRCodeView.startSpot();
            }
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void initView() {
        model.mQRCodeView.setDelegate(this);
        model.mQRCodeView.startSpot();
        //model.mQRCodeView.startCamera();
    }

    @Override
    public void onStart() {

        model.mQRCodeView.startCamera();
        super.onStart();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }


    @Override
    public void onStop() {
        model.mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        model.mQRCodeView.onDestroy();
        model.clear();
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }



    @Override
    public void onScanQRCodeSuccess(String result) {
        System.out.print(result);
        //PreferencesUtils.put
        model.validCode(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(getActivity(),"请确认相机能被正常打开",Toast.LENGTH_SHORT).show();
    }
}
