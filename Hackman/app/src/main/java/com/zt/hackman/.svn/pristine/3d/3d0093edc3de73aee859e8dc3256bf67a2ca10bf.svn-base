package com.zt.hackman.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zt.hackman.R;
import com.zt.hackman.adapter.CauseAdapter;
import com.zt.hackman.event.CauseEvent;
import com.zt.hackman.model.CauseWindowModel;
import com.zt.hackman.response.QuestionResponse;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ChooseCauseWindow extends PopupWindow implements View.OnClickListener{
    private View view;
    private List<QuestionResponse.Cause> causes;
    private Context context;
    private int index = -1;
    private CauseWindowModel model;
    private CauseAdapter adapter;


    public ChooseCauseWindow(Context context, List<QuestionResponse.Cause> causes){
        super(context);
        this.context = context;
        this.causes = causes;
        init();
    }

    private void init(){
        // Android获得屏幕信息
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        setAnimationStyle(R.style.AnimationWindow);
        view = LayoutInflater.from(context).inflate(R.layout.window_choose_cause_layout, null);
        initView();
        setContentView(view);


        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // window.setHeight(height/2);
        // setHeight(height);
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);// 控制popupwindow屏幕

        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                AnimationSet animationSet = new AnimationSet(true);
                // 参数1～2：x轴的开始位置
                // 参数3～4：y轴的开始位置
                // 参数5～6：x轴的结束位置
                // 参数7～8：y轴的结束位置
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, -0.5f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                translateAnimation.setDuration(500);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillAfter(true);
                // test_pop_layout.startAnimation(animationSet);
            }
        });
        // 设置PopupWindow外部区域是否可触摸
        setFocusable(true); // 设置PopupWindow可获得焦点
        setTouchable(true); // 设置PopupWindow可触摸
        setOutsideTouchable(false); // 设置非PopupWindow区域可触摸
    }

    private void initView(){
        model = new CauseWindowModel();
        model.findViewByIds(view,this);
        adapter = new CauseAdapter(context);
        model.causeListView.setAdapter(adapter);

        adapter.refreshData(causes,index);

        model.causeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                index = position;
                adapter.refreshData(causes,index);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.window_cancel_btn:
                dismiss();
                break;
            case R.id.window_confirm_btn:
                if(index == -1){
                    Toast.makeText(context,"请选择一个原因",Toast.LENGTH_SHORT).show();
                }else{
                    EventBus.getDefault().post(new CauseEvent(CauseEvent.TYPE_CAUSE_POSITION,index));
                    dismiss();
                }
                break;
            case R.id.window_root_view:
                dismiss();
                break;
        }
    }
}
