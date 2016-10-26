package com.zt.hackman.model;

import android.view.View;
import android.widget.TextView;

import com.zt.hackman.R;
import com.zt.hackman.view.WheelView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class AreaWindowModel {

    public WheelView provinceWheel,areaWheel,cityWheel;
    public TextView cancelBtn,confirmBtn;

    public void findViewByIds(View view, View.OnClickListener listener){
        provinceWheel = (WheelView) view.findViewById(R.id.province_wheel);
        cityWheel = (WheelView)view.findViewById(R.id.city_wheel);
        areaWheel = (WheelView)view.findViewById(R.id.area_wheel);

        cancelBtn  = (TextView)view.findViewById(R.id.window_area_cancel_btn);
        confirmBtn = (TextView)view.findViewById(R.id.window_area_confirm_btn);

        cancelBtn.setOnClickListener(listener);
        confirmBtn.setOnClickListener(listener);
    }

    public void initView(ArrayList<String> provinces,
                         ArrayList<String> citys, ArrayList<String> areas){
        provinceWheel.setData(provinces);
        provinceWheel.setSelected(true);

        cityWheel.setData(citys);
        cityWheel.setSelected(true);

        areaWheel.setData(areas);
        areaWheel.setSelected(true);
    }



    public String getText(){
        String province = provinceWheel.getSelectedText();
        String city = cityWheel.getSelectedText();
        String area = areaWheel.getSelectedText();

        return province+city+area;
    }
}
