package com.zt.hackman.model;

import android.app.Activity;
import android.net.sip.SipAudioCall;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.bean.ImageItem;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.hackman.R;
import com.zt.hackman.adapter.ChoosePhotoAdapter;
import com.zt.hackman.base.BaseActivity;
import com.zt.hackman.constant.Constant;
import com.zt.hackman.request.HackRequest;
import com.zt.hackman.response.HackmanResponse;
import com.zt.hackman.response.QuestionResponse;
import com.zt.hackman.response.Response;
import com.zt.hackman.utils.GsonUtils;
import com.zt.hackman.utils.LoginUtils;
import com.zt.hackman.utils.PreferencesUtils;
import com.zt.hackman.utils.StringUtils;
import com.zt.hackman.utils.ToastUtils;
import com.zt.hackman.view.ChooseCauseWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class QuestionModel implements View.OnClickListener{
    public GridView gridView;
    public RelativeLayout chooseQuestionLayout,questionNav;
    public EditText questionDes;
    public TextView commitQuestionBtn;
    private int currentState;
    private BaseActivity ac;
    private LayoutInflater inflater ;
    private LinearLayout container;
    private ArrayList<ImageItem> items;
    private ChoosePhotoAdapter adapter;
    private TextView question_des_text;
    private List<QuestionResponse.Cause> causes;
    private EditText startAddressText;
    private EditText paiArriveText,paiMoneyText;
    private int currentType;
    private int orderStatus;
    private String orderId;
    private String start_addr;
    private String end_addr;

    public void findViewByIds(BaseActivity activity ,
         int currentState,String orderId,
         int orderStatus,String start_addr,String end_addr){

        this.orderId = orderId;
        this.start_addr = start_addr;
        this.end_addr = end_addr;
        this.ac = activity;
        this.orderStatus = orderStatus;
        inflater = LayoutInflater.from(activity);
        this.currentState = currentState;
        chooseQuestionLayout = (RelativeLayout)activity.findViewById(R.id.choose_question_layout);
        container = (LinearLayout)activity.findViewById(R.id.question_container);

        questionNav = (RelativeLayout)activity.findViewById(R.id.question_nav_layout);
        questionNav.setBackgroundResource(R.color.colorWhite);
        commitQuestionBtn = (TextView) activity.findViewById(R.id.commit_question_btn);
        question_des_text = (TextView)activity.findViewById(R.id.question_des_text);

        commitQuestionBtn.setVisibility(View.GONE);
        chooseQuestionLayout.setOnClickListener(this);

    }

    public void initQuestion(int position){
        QuestionResponse.Cause cause =  causes.get(position);
        currentType = Integer.parseInt(cause.type);
        switch (currentType){
            case 1:
                question_des_text.setText("异常问题");
                initBadView();
                break;
            case 2:
                question_des_text.setText("二次改派");
                initPaiView();
                break;
        }
    }

    public  void refreshImg(ArrayList<ImageItem> imageItems){
        this.items = imageItems;
        if(imageItems.size()<=3){
            imageItems.add(new ImageItem());
        }
        adapter = new ChoosePhotoAdapter(ac);
        gridView.setAdapter(adapter);
        adapter.refreshData(imageItems);
    }

    /**
     * 初始化物品损坏界面
     */
    private void initBadView(){
        View badView = inflater.inflate(R.layout.layout_add_des,container,false);
        container.removeAllViews();
        container.addView(badView);
        gridView = (GridView)badView.findViewById(R.id.question_photo_grid_view);
        questionDes = (EditText)badView.findViewById(R.id.question_des_edit);

        items = new ArrayList<ImageItem>();
        initImages(items);
        commitQuestionBtn.setVisibility(View.VISIBLE);
        commitQuestionBtn.setOnClickListener(this);
    }

    /**
     * 初始化图片
     * @param items
     */
    public void initImages(ArrayList<ImageItem> items){
        this.items = items;
        items.add(new ImageItem());
        if(adapter==null) {
            adapter = new ChoosePhotoAdapter(ac);
            gridView.setAdapter(adapter);
        }
        adapter.refreshData(items);
    }

    /**
     * 初始化二次改派界面
     */
    private void initPaiView(){
        View paiView = inflater.inflate(R.layout.layout_second_pai,container,false);
        container.removeAllViews();
        container.addView(paiView);
        questionDes = (EditText)paiView.findViewById(R.id.question_des_edit);
        paiArriveText = (EditText) paiView.findViewById(R.id.pai_arrive_address_text);
        paiMoneyText = (EditText)paiView.findViewById(R.id.pai_money_text);
        startAddressText = (EditText) paiView.findViewById(R.id.pai_start_address_text);
        startAddressText.setText(start_addr);
        paiArriveText.setText(end_addr);
        commitQuestionBtn.setVisibility(View.VISIBLE);
        commitQuestionBtn.setOnClickListener(this);
    }

    /**
     * 返回的原因列表
     */
    StringCallback causeCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(ac,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            String content = null;
            if(res.code>0){
                try {
                    JSONObject jsonObject = new JSONObject(res.data);
                    content = jsonObject.getString("reason");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                causes = (List<QuestionResponse.Cause>) GsonUtils
                        .jsonToList(content,new TypeToken<List<QuestionResponse.Cause>>(){}.getType());
            }else{
                if(res.code==-10){
                    LoginUtils.showDialog(ac,"登录提示","会话已过期，请重新登录");
                }else{
                    Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void initData(){
        //{"command":"exceptionReason","sid":"uajndv9kead8lf0u45e2pbnrg7","type":"1"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command", "exceptionReason");
            String sid = PreferencesUtils.getSID(ac);
            if (sid == null) {
                LoginUtils.showDialog(ac, "登录提示", "你尚未登录，请先登录");
            }
            jsonObject.put("sid", sid);
            if (orderStatus == 3) {
                jsonObject.put("type", "1");
            }else if(orderStatus==4){
                jsonObject.put("type","2");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HackRequest().request(jsonObject.toString(),causeCallBack,Constant.TEST_HOST);
    }

    public void init(){
        initData();
    }
    StringCallback paiCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(ac,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                ToastUtils.showSuccess(ac,res.msg);
                ac.finish();
            }else{
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 二次改派请求接口
     */
    private void requestPai(){
        String money = paiMoneyText.getText().toString();
        String addr = paiArriveText.getText().toString();
        String startAddress = startAddressText.getText().toString();
        JSONObject jsonObject = new JSONObject();
        String describe = questionDes.getText().toString();
        if(addr==null|| StringUtils.isEmpty(addr)){
            Toast.makeText(ac,"收货地址不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(money == null||StringUtils.isEmpty(money)){
            Toast.makeText(ac,"改派金额不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(currentState==1) {
            if (startAddress == null || StringUtils.isEmpty(addr)) {
                Toast.makeText(ac, "取货地址不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            jsonObject.put("command","reSendOrder");
            String sid = PreferencesUtils.getSID(ac);
            if(sid==null){
                LoginUtils.showDialog(ac,"登录提示","您尚未登录,请先登录");
            }
            jsonObject.put("sid",sid);
            jsonObject.put("orderID",orderId);
            jsonObject.put("newPrice",money);
            if(currentState==1){
                jsonObject.put("newDeliverAddr",startAddress);
            }
            jsonObject.put("exceptionReasonId",""+currentType);
            jsonObject.put("newReceiptAddr",addr);
            jsonObject.put("describe",describe);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
        }
        new HackRequest().request(jsonObject.toString(),paiCallBack, Constant.TEST_HOST);
    }

    /**
     * 遇到异常问题回调
     */
    StringCallback badCallBack = new StringCallback() {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(ac,"网络请求异常",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            Response res = new Response(response,ac);
            if(res.code>0){
                ToastUtils.showSuccess(ac,"问题提交成功");
            }else{
                Toast.makeText(ac,res.msg,Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 物品损坏请求接口
     */
    private void requestBad(){
        //{"command":"pickupProblem","sid":"daefhgherr45456","type":"3","describe":"12sadad12"}
        String describe = questionDes.getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("command","pickupProblem");
            jsonObject.put("sid", PreferencesUtils.getSID(ac));
            jsonObject.put("type","1");
            if(describe!=null||!StringUtils.isEmpty(describe)) {
                jsonObject.put("describe", describe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ac,"JSON数据转换异常",Toast.LENGTH_SHORT).show();
        }
        ArrayList<HackmanResponse.FileParams> fileParamses =
                new ArrayList<HackmanResponse.FileParams>();
        for (ImageItem item : items){
            HackmanResponse.FileParams fileParams = new HackmanResponse.FileParams();
            fileParams.name="exceptionImg[]";
            fileParams.file = new File(item.path);
            fileParamses.add(fileParams);
        }
        new HackRequest().requestFileAndParams(jsonObject.toString(),fileParamses,Constant.TEST_HOST,badCallBack);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.choose_question_layout:
                if(currentState==1) {
                    ChooseCauseWindow window = new ChooseCauseWindow(ac, causes);
                    window.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                }else if(currentState==2) {
                    ChooseCauseWindow window = new ChooseCauseWindow(ac, causes);
                    window.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.commit_question_btn:
                switch (currentType){
                    case 1:
                        requestBad();
                        break;
                    case 2:
                        requestPai();
                        break;
                }
                break;
        }
    }

}
