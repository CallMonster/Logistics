package com.zt.hackman.model;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.PostStringRequest;
import com.zt.hackman.R;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.event.SwitchEvent;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.ToastUtils;


import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by Administrator on 2016/10/8.
 */
public class CodeModel {
    public QRCodeView mQRCodeView;
    public RelativeLayout navLayout;
    HackRequest request ;
    private Activity ac;

    StringCallback codeCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(ac,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                ToastUtils.showSuccess(ac,"扫码成功");
                Vibrator vibrator = (Vibrator) ac.getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                EventBus.getDefault().post(new SwitchEvent(SwitchEvent.TYPE_SUCCESS));
            }else{
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }

        }
    };

    public void findViewByIds(Activity ac,View view){
        this.ac = ac;
        mQRCodeView = (ZXingView)view.findViewById(R.id.zxingview);
        navLayout = (RelativeLayout)view.findViewById(R.id.fragment_code_layout);

        navLayout.setBackgroundResource(R.color.colorWhite);
    }

    /**
     * 判断二维码
     * @param code
     */
    public void validCode(String code){
        request = new HackRequest();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("command","changeQRCode");
            jsonObject.put("vehicleId","1");
            String sid = PreferencesUtils.getString(ac, Constant.PREFERENCE_KEY.KEY_SID,null);
            if(sid!=null){
                jsonObject.put("sid",sid);
            }
            jsonObject.put("page","1");
            jsonObject.put("limit",""+Constant.order_pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        request.request(jsonObject.toString(),codeCallBack,Constant.TEST_HOST);
//        {"command":"changeQRCode","vehicleId":"3","sid":"oabpuph1sob7jbepafktn23q53","page":"1","limit":"3"}

    }

    public void clear(){
        mQRCodeView = null;
        navLayout = null;
    }
}
